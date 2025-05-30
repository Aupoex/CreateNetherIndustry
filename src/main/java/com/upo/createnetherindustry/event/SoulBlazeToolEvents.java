package com.upo.createnetherindustry.event;

import com.upo.createnetherindustry.registry.CNIItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;


@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
public class SoulBlazeToolEvents {

    @SubscribeEvent
    public static void onPlayerBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        if (player == null) {
            return;
        }
        ItemStack mainHandStack = player.getMainHandItem();
        Item heldItem = mainHandStack.getItem();
        if (heldItem == CNIItems.SOUL_BLAZE_PICKAXE.get() || heldItem == CNIItems.SOUL_BLAZE_AXE.get()) {
            if (player.level().dimension().equals(Level.NETHER)) {
                float currentSpeed = event.getNewSpeed();
                if (currentSpeed > 0) {
                    event.setNewSpeed(currentSpeed * 2.0f);
                }
            }
        }
    }
    @SubscribeEvent
    public static void onLivingDamageModify(LivingDamageEvent.Pre event) {
        DamageSource damageSource = event.getSource();
        Entity attacker = damageSource.getEntity();
        if (attacker instanceof Player playerAttacker) {
            ItemStack heldItemStack = playerAttacker.getMainHandItem();
            if (heldItemStack.getItem() == CNIItems.SOUL_BLAZE_AXE.get()) {
                if (playerAttacker.level().dimension().equals(Level.NETHER)) {
                    float currentDamage = event.getNewDamage();
                    event.setNewDamage(currentDamage * 2.0f);
                }
            }
        }
    }
}

