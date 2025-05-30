package com.upo.createnetherindustry.content.items;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class CNITiers {
    public static final SimpleTier SOUL_BLAZE_TIER = new SimpleTier(
            BlockTags.NEEDS_DIAMOND_TOOL,
            1821,
            8.0F,
            3.0F,
            20,
            () -> Ingredient.of(Items.GOLD_INGOT)
    );

}
