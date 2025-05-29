package com.upo.createnetherindustry.content.blocks;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.block.IBE;
import com.upo.createnetherindustry.content.blockentities.SoulCondenserBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import com.upo.createnetherindustry.registry.CNIBlockEntities;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SoulCondenserBlock extends HorizontalKineticBlock implements IBE<SoulCondenserBlockEntity>, ICogWheel {

    public SoulCondenserBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public Class<SoulCondenserBlockEntity> getBlockEntityClass() {
        return SoulCondenserBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SoulCondenserBlockEntity> getBlockEntityType() {
        return CNIBlockEntities.SOUL_CONDENSER.get();
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Block.box(0, 0, 0, 16, 16, 16);
    }
}
