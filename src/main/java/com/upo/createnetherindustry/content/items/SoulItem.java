package com.upo.createnetherindustry.content.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.Nullable;
import java.util.List;

public class SoulItem extends Item {

    public SoulItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.literal("蕴含着下界游魂的稀薄精华。"));
    }

    //持续发光
    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }

}
