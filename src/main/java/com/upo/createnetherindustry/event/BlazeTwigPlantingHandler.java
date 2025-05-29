package com.upo.createnetherindustry.event;

import com.upo.createnetherindustry.registry.CNIBlocks;
import com.upo.createnetherindustry.registry.CNIItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
public class BlazeTwigPlantingHandler {
    @SubscribeEvent
    public static void onPlayerRightClickBlockPlantGoldenCarrot(PlayerInteractEvent.RightClickBlock event) {
        if (event.isCanceled()) {
            return;
        }

        Level level = event.getLevel();
        BlockPos clickedPos = event.getPos();
        Player player = event.getEntity();
        ItemStack heldStack = event.getItemStack();
        InteractionHand hand = event.getHand();

        if (heldStack.getItem() != CNIItems.BLAZE_TWIG.get()) {
            return;
        }

        BlockState clickedBlockState = level.getBlockState(clickedPos);
        if (!clickedBlockState.is(CNIBlocks.CRIMSON_NYLIUM_FARMLAND)) {
            return;
        }

        BlockPos plantPos = clickedPos.above();
        if (!level.getBlockState(plantPos).isAir()) {
            return;
        }


        BlockState cropToPlace = CNIBlocks.BLAZE_TWIG_CROP.get().defaultBlockState();
        if (!cropToPlace.canSurvive(level, plantPos)) {
            return;
        }

        if (!level.isClientSide) {
            level.setBlock(plantPos, cropToPlace, Block.UPDATE_ALL);
            BlockState placedCropState = level.getBlockState(plantPos);
            level.playSound(null, plantPos, placedCropState.getSoundType(level, plantPos, player).getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);

            if (!player.getAbilities().instabuild) {
                heldStack.shrink(1);
            }
        }
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
    }
}
