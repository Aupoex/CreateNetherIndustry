package com.upo.createnetherindustry.content.items;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import javax.annotation.Nullable;
import java.util.function.Consumer;


public class SoulBlazePickaxe extends PickaxeItem {

    private final ItemAttributeModifiers customDefaultModifiers;

    private static final ResourceLocation PICKAXE_ATTACK_DAMAGE_MODIFIER_ID = ResourceLocation.withDefaultNamespace("base_attack_damage");
    private static final ResourceLocation PICKAXE_ATTACK_SPEED_MODIFIER_ID = ResourceLocation.withDefaultNamespace("base_attack_speed");


    public SoulBlazePickaxe(Tier tier, Properties properties) {
        super(tier, properties);

        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        float attackDamage = tier.getAttackDamageBonus() + 1.0F;
        float attackSpeed = -2.8F;

        builder.add(
                Attributes.ATTACK_DAMAGE,
                new AttributeModifier(PICKAXE_ATTACK_DAMAGE_MODIFIER_ID, attackDamage, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.MAINHAND
        );
        builder.add(
                Attributes.ATTACK_SPEED,
                new AttributeModifier(PICKAXE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.MAINHAND
        );

        this.customDefaultModifiers = builder.build();
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        return this.customDefaultModifiers;
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<Item> onBroken) {

        if (entity != null && entity.level().dimension().equals(Level.NETHER)) {
            return 0;
        }
        return super.damageItem(stack, amount, entity, onBroken);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F) {
            EquipmentSlot slot = entityLiving.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
            stack.hurtAndBreak(1, entityLiving, slot);
        }
        return true;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level().isClientSide) {
            EquipmentSlot slot = attacker.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
            stack.hurtAndBreak(2, attacker, slot);
        }
        return true;
    }
}
