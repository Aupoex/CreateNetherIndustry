package com.upo.createnetherindustry.content.kinetics.fan.soul_stripping;

import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import com.upo.createnetherindustry.registry.CNIRecipes;
import com.upo.createnetherindustry.registry.CNITags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulStrippingFanProcessingType implements FanProcessingType {

    @Override
    public boolean isValidAt(Level level, BlockPos pos) {

        return level.getBlockState(pos).is(CNITags.FAN_SOUL_STRIPPING_CATALYSTS);
    }

    @Override
    public int getPriority() {

        return 450;
    }

    @Override
    public boolean canProcess(ItemStack stack, Level level) {
        if (stack.isEmpty()) {
            return false;
        }

        return level.getRecipeManager()
                .getRecipeFor(CNIRecipes.SOUL_STRIPPING_TYPE_INFO.getType(), new SingleRecipeInput(stack), level)
                .isPresent();
    }

    @Override
    @Nullable
    public List<ItemStack> process(ItemStack stack, Level level) {
        if (stack.isEmpty()) {
            return null;
        }

        return level.getRecipeManager()
                .getRecipeFor(CNIRecipes.SOUL_STRIPPING_TYPE_INFO.getType(), new SingleRecipeInput(stack), level)
                .map(recipeHolder -> RecipeApplier.applyRecipeOn(level, stack, recipeHolder.value()))
                .orElse(null);
    }

    @Override
    public void spawnProcessingParticles(Level level, Vec3 pos) {

        if (level.random.nextInt(5) == 0) {
            level.addParticle(ParticleTypes.SOUL,
                    pos.x + (level.random.nextFloat() - 0.5f) * 0.8f,
                    pos.y + 0.2f + level.random.nextFloat() * 0.5f,
                    pos.z + (level.random.nextFloat() - 0.5f) * 0.8f,
                    (level.random.nextFloat() - 0.5f) * 0.01f,
                    level.random.nextFloat() * 0.05f,
                    (level.random.nextFloat() - 0.5f) * 0.01f
            );
        }
    }

    @Override
    public void morphAirFlow(AirFlowParticleAccess particleAccess, RandomSource random) {

        if (random.nextBoolean()) {
            particleAccess.setColor(0xADD8E6);
        } else {
            particleAccess.setColor(0xAFEEEE);
        }
        particleAccess.setAlpha(0.8f + random.nextFloat() * 0.2f);
        if (random.nextInt(16) == 0) {
            particleAccess.spawnExtraParticle(ParticleTypes.SOUL_FIRE_FLAME, 0.05f);
        }
    }

    @Override
    public void affectEntity(Entity entity, Level level) {
        if (level.isClientSide) {
            return;
        }
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.getType().is(EntityTypeTags.UNDEAD)) {
                if (livingEntity.tickCount % 20 == 0) {
                    livingEntity.heal(1.0F);
                }
            } else {
                DamageSources damageSources = level.damageSources();
                if (livingEntity.tickCount % 20 == 0) {
                    livingEntity.hurt(damageSources.magic(), 1.0F);
                }
            }
        }
    }
}

