package com.upo.createnetherindustry.event;

import com.upo.createnetherindustry.registry.CNIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
public class NyliumHoeingHandler {

    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getEntity();
        ItemStack heldStack = event.getItemStack();
        BlockState clickedBlockState = level.getBlockState(pos);
        Block clickedBlock = clickedBlockState.getBlock();

        if (!(heldStack.getItem() instanceof HoeItem)) {
            return;
        }

        Block targetFarmlandBlock = null;
        if (clickedBlock == Blocks.WARPED_NYLIUM) {
            targetFarmlandBlock = CNIBlocks.WARPED_NYLIUM_FARMLAND.get();
        } else if (clickedBlock == Blocks.CRIMSON_NYLIUM) {
            targetFarmlandBlock = CNIBlocks.CRIMSON_NYLIUM_FARMLAND.get();
        }

        if (targetFarmlandBlock != null) {
            if (level.getBlockState(pos.above()).isAir()) {

                level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);

                if (!level.isClientSide) {
                    level.setBlock(pos, targetFarmlandBlock.defaultBlockState(), 11);

                    if (!player.getAbilities().instabuild) {
                        heldStack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(heldStack));
                    }
                }
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            }
        }
    }
}

