package com.upo.createnetherindustry.content.blocks.nylium_farmland;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;


public class WarpedNyliumFarmlandBlock extends FarmBlock {
    public static final IntegerProperty MOISTURE = BlockStateProperties.MOISTURE;
    public static final int MAX_MOISTURE = 7;

    public WarpedNyliumFarmlandBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(MOISTURE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MOISTURE);
    }

    public static void turnToNetherrack(@Nullable Entity entity, BlockState state, Level level, BlockPos pos) {
        BlockState netherrackState = Blocks.NETHERRACK.defaultBlockState();
        level.setBlockAndUpdate(pos, pushEntitiesUp(state, netherrackState, level, pos));
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, netherrackState));
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!level.isClientSide && entity.canTrample(state, pos, fallDistance)) {
            turnToNetherrack(entity, state, level, pos);
        }
        if (!level.isClientSide) {
            if (entity.canTrample(state, pos, fallDistance)) {
                turnToNetherrack(entity, state, level, pos);
            }
            entity.causeFallDamage(fallDistance, 1.0F, level.damageSources().fall());
        }
    }



    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            turnToNetherrack(null, state, level, pos);
            return;
        }

        int currentMoisture = state.getValue(MOISTURE);

        if (isNearLava(level, pos)) {
            if (currentMoisture < MAX_MOISTURE) {
                level.setBlock(pos, state.setValue(MOISTURE, MAX_MOISTURE), 2);
            }
        }else {
            if (isNearWaterInsteadOfLava(level, pos)) {
                if (currentMoisture > 0) {
                    level.setBlock(pos, state.setValue(MOISTURE, currentMoisture - 1), 2);
                } else if (!hasCrop(level, pos)) {
                    turnToNetherrack(null, state, level, pos);
                }
            }
        }
    }

    private static boolean isNearWaterInsteadOfLava(LevelReader levelReader, BlockPos pos) {
        for (BlockPos checkPos : BlockPos.betweenClosed(pos.getX() - 4, pos.getY() - 1, pos.getZ() - 4,
                pos.getX() + 4, pos.getY()    , pos.getZ() + 4)) {
            FluidState fluidState = levelReader.getFluidState(checkPos);
            if (fluidState.is(Fluids.WATER) || fluidState.is(Fluids.FLOWING_WATER)) {
                return true;
            }
        }
        return false;
    }


    private static boolean hasCrop(BlockGetter level, BlockPos pos) {
        BlockState plant = level.getBlockState(pos.above());

        return !plant.isAir();
    }


    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState blockStateAbove = level.getBlockState(pos.above());
        return !blockStateAbove.isSolid() || blockStateAbove.getBlock() instanceof FenceGateBlock;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            turnToNetherrack(null, state, level, pos);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!state.canSurvive(level, pos)) {
            level.scheduleTick(pos, this, 1);
        }
    }

    private static boolean isNearLava(LevelReader levelReader, BlockPos pos) {
        for (BlockPos checkPos : BlockPos.betweenClosed(pos.getX() - 4, pos.getY() -1 , pos.getZ() - 4,
                pos.getX() + 4, pos.getY()    , pos.getZ() + 4)) {
            FluidState fluidState = levelReader.getFluidState(checkPos);
            if (fluidState.is(Fluids.LAVA) || fluidState.is(Fluids.FLOWING_LAVA)) {
                return true;
            }
        }
        return false;
    }

}

