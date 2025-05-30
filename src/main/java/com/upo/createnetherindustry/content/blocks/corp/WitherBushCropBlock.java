package com.upo.createnetherindustry.content.blocks.corp;

import com.upo.createnetherindustry.content.blocks.nylium_farmland.WarpedNyliumFarmlandBlock;
import com.upo.createnetherindustry.registry.CNIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WitherBushCropBlock extends CropBlock {
    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, MAX_AGE);

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D),
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D),
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 9.0D, 14.0D),
    };
    public WitherBushCropBlock(Properties properties) {
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
        return Items.WITHER_ROSE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState groundState, BlockGetter worldIn, BlockPos pos) {
        return groundState.is(CNIBlocks.WARPED_NYLIUM_FARMLAND);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 1)) return;
        int currentAge = this.getAge(state);
        if (currentAge < this.getMaxAge()) {
            float growthChance = 0.05f;

            BlockState farmlandState = level.getBlockState(pos.below());
            if (farmlandState.hasProperty(WarpedNyliumFarmlandBlock.MOISTURE) &&
                    farmlandState.getValue(WarpedNyliumFarmlandBlock.MOISTURE) == WarpedNyliumFarmlandBlock.MAX_MOISTURE) {
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


}
