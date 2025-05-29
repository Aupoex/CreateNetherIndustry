package com.upo.createnetherindustry.content.blocks.corp;

import com.upo.createnetherindustry.content.blocks.nylium_farmland.CrimsonNyliumFarmlandBlock;
import com.upo.createnetherindustry.content.blocks.nylium_farmland.WarpedNyliumFarmlandBlock;
import com.upo.createnetherindustry.registry.CNITags;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GoldenCarrotCropBlock extends CropBlock {
    public static final int MAX_AGE = 7;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    };

    public GoldenCarrotCropBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
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
    protected ItemLike getBaseSeedId() {
        return Items.GOLDEN_CARROT;
    }

    @Override
    protected boolean mayPlaceOn(BlockState groundState, BlockGetter level, BlockPos pos) {
        return groundState.is(CNITags.NYLIUM_FARMLAND);

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
            } else if (farmlandState.hasProperty(CrimsonNyliumFarmlandBlock.MOISTURE) &&
                    farmlandState.getValue(CrimsonNyliumFarmlandBlock.MOISTURE) == CrimsonNyliumFarmlandBlock.MAX_MOISTURE) {
                growthChance = 0.10f;
            }

            if (random.nextFloat() < growthChance) {
                level.setBlock(pos, this.getStateForAge(currentAge + 1), Block.UPDATE_CLIENTS);
            }
        }

    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
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

