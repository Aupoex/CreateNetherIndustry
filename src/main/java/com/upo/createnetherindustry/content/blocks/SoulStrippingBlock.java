package com.upo.createnetherindustry.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SoulStrippingBlock extends Block {

    public SoulStrippingBlock(Properties properties) {
        super(properties.randomTicks());
    }




    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return handleInteraction(level, pos, player, hand);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        ItemInteractionResult itemInteractionResult = handleInteraction(level, pos, player, player.getUsedItemHand());
        return itemInteractionResult.result();
    }

    private ItemInteractionResult handleInteraction(Level level, BlockPos pos, Player player, InteractionHand hand) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            boolean clearedEffect = false;
            List<Holder<MobEffect>> effectsToRemove = new ArrayList<>();

            for (MobEffectInstance effectInstance : serverPlayer.getActiveEffects()) {
                Holder<MobEffect> effectHolder = effectInstance.getEffect();
                if (effectHolder.isBound()) {
                    if (effectHolder.value().getCategory() == MobEffectCategory.HARMFUL) {
                        effectsToRemove.add(effectHolder);
                        clearedEffect = true;
                    }
                }
            }

            if (clearedEffect) {
                for (Holder<MobEffect> effectHolderToRemove : effectsToRemove) {
                    serverPlayer.removeEffect(effectHolderToRemove);
                }

                level.playSound(null, pos, SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
                ((ServerLevel) level).sendParticles(ParticleTypes.SOUL, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 10, 0.3, 0.3, 0.3, 0.05);

                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.literal("§7能够引导气流祛除灵魂束缚。§r"));
    }


    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);

        double attractionRadius = 16.0;
        AABB searchBox = new AABB(pos).inflate(attractionRadius);

        List<Mob> nearbyMobs = level.getEntitiesOfClass(Mob.class, searchBox, mob ->
                mob.getType().is(EntityTypeTags.UNDEAD) &&
                        mob.isAlive() &&
                        (mob.getTarget() == null || !mob.getTarget().isAlive() || mob.distanceToSqr(mob.getTarget()) > attractionRadius * attractionRadius / 4) &&
                        (mob.getNavigation().getTargetPos() == null || !mob.getNavigation().getTargetPos().closerThan(pos, 2.0))
        );

        if (nearbyMobs.isEmpty()) {
            return;
        }

        for (Mob mob : nearbyMobs) {
            if (mob.getNavigation().isDone() || random.nextInt(3) == 0) {
                BlockPos targetPos = pos.above();
                BlockState targetBlockState = level.getBlockState(targetPos);

                if (targetBlockState.isPathfindable(PathComputationType.LAND)) {
                    mob.getNavigation().moveTo(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, 1.0D);
                } else {
                    BlockState currentBlockState = level.getBlockState(pos);
                    if (currentBlockState.isPathfindable(PathComputationType.LAND)) {
                        mob.getNavigation().moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 1.0D);
                    }
                }
            }
        }
    }
}




