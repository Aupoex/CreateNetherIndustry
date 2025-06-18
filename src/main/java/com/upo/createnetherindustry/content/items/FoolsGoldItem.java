package com.upo.createnetherindustry.content.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FoolsGoldItem extends Item {
    public FoolsGoldItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isPiglinCurrency(ItemStack stack) {
        return true;
    }
}
