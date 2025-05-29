package com.upo.createnetherindustry.content.blocks.corp;

import com.upo.createnetherindustry.content.blocks.nylium_farmland.CrimsonNyliumFarmlandBlock;
import com.upo.createnetherindustry.registry.CNIBlocks;
import com.upo.createnetherindustry.registry.CNIItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;


public class BlazeCropBlock extends CropBlock {
    public static final int MAX_AGE = 4;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, MAX_AGE);

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 4.0D, 13.0D),
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D),
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 12.0D, 13.0D),
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D),
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D)
    };

    public BlazeCropBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), 0));
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return CNIItems.BLAZE_TWIG.get();
    }

    @Override
    protected boolean mayPlaceOn(BlockState groundState, BlockGetter worldIn, BlockPos pos) {
        return groundState.is(CNIBlocks.CRIMSON_NYLIUM_FARMLAND);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 1)) return;
        int currentAge = this.getAge(state);
        if (currentAge < this.getMaxAge()) {
            float growthChance = 0.05f;

            BlockState farmlandState = level.getBlockState(pos.below());
            if (farmlandState.hasProperty(CrimsonNyliumFarmlandBlock.MOISTURE) &&
                    farmlandState.getValue(CrimsonNyliumFarmlandBlock.MOISTURE) == CrimsonNyliumFarmlandBlock.MAX_MOISTURE) {
                growthChance = 0.10f;
            }
            if (random.nextFloat() < growthChance) {
                level.setBlock(pos, this.getStateForAge(currentAge + 1), Block.UPDATE_CLIENTS);
            }
        }
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        int ageIncrease = java.util.concurrent.ThreadLocalRandom.current().nextInt(10) < 3 ? 1 : 0;;
        int newAge = this.getAge(state) + ageIncrease;
        int maxAge = this.getMaxAge();
        if (newAge > maxAge) {
            newAge = maxAge;
        }
        level.setBlock(pos, this.getStateForAge(newAge), Block.UPDATE_CLIENTS);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int currentAge = state.getValue(AGE);
        boolean isMature = currentAge == MAX_AGE;

        if (isMature) {
            if (!level.isClientSide) {
                int amountToDrop = 1 + level.random.nextInt(3);
                ItemStack fruitStack = new ItemStack(CNIItems.BLAZE_FRUIT.get(), amountToDrop);
                popResource(level, pos, fruitStack);

                level.setBlock(pos, state.setValue(AGE, MAX_AGE - 1), Block.UPDATE_CLIENTS);

                level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);

                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            }


            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

}
