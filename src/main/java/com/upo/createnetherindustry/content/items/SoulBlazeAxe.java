package com.upo.createnetherindustry.content.items;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public class SoulBlazeAxe extends AxeItem {

    private final ItemAttributeModifiers customDefaultModifiers;

    private static final ResourceLocation AXE_ATTACK_DAMAGE_MODIFIER_ID = ResourceLocation.withDefaultNamespace("base_attack_damage");
    private static final ResourceLocation AXE_ATTACK_SPEED_MODIFIER_ID = ResourceLocation.withDefaultNamespace("base_attack_speed");

    public SoulBlazeAxe(Tier tier, Properties properties) {
        super(tier, properties);

        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        float attackDamage = tier.getAttackDamageBonus() + 5.0F;
        float attackSpeed = -3.0F;

        builder.add(
                Attributes.ATTACK_DAMAGE,
                new AttributeModifier(AXE_ATTACK_DAMAGE_MODIFIER_ID, attackDamage, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.MAINHAND
        );
        builder.add(
                Attributes.ATTACK_SPEED,
                new AttributeModifier(AXE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE),
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


}
