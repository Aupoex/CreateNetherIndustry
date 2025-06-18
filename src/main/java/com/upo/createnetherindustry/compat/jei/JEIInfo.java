package com.upo.createnetherindustry.compat.jei;

import com.upo.createnetherindustry.registry.CNIBlocks;
import com.upo.createnetherindustry.registry.CNIItems;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.List;

public class JEIInfo {
    public static void registerInfoPages(IRecipeRegistration registration) {

        addInfo(registration, CNIItems.SOUL_ITEM,
                "jei.createnetherindustry.info.soul_item_alt.line1",
                "jei.createnetherindustry.info.soul_item_alt.line2");

        addInfo(registration, CNIBlocks.SOUL_STRIPPING_MEDIUM,
                "jei.createnetherindustry.info.medium.line1",
                "jei.createnetherindustry.info.medium.line2");

        addInfo(registration, CNIItems.BLAZE_TWIG,
                "jei.createnetherindustry.info.blaze_twig.line1",
                "jei.createnetherindustry.info.blaze_twig.line2");

        addInfo(registration, CNIItems.ANCIENT_MECHANISM,
                "jei.createnetherindustry.info.ancient_mechanism.line1",
                "jei.createnetherindustry.info.ancient_mechanism.line2");

        addInfo(registration, CNIItems.SOUL_BLAZE_PICKAXE,
                "jei.createnetherindustry.info.soul_blaze_pickaxe.line1",
                "jei.createnetherindustry.info.soul_blaze_pickaxe.line2");

        addInfo(registration, CNIItems.SOUL_BLAZE_AXE,
                "jei.createnetherindustry.info.soul_blaze_axe.line1",
                "jei.createnetherindustry.info.soul_blaze_axe.line2");

        addInfo(registration, Items.GOLDEN_CARROT,
                "jei.createnetherindustry.info.golden_carrot.line1",
                "jei.createnetherindustry.info.golden_carrot.line2");

        addInfo(registration, Items.WITHER_ROSE,
                "jei.createnetherindustry.info.wither_rose.line1",
                "jei.createnetherindustry.info.wither_rose.line2");
        addInfo(registration, CNIItems.LAVA_SPEEDBOAT,
                "jei.createnetherindustry.info.lava_speedboat.line1");

        addInfo(registration, CNIItems.FOOLS_GOLD,
                "jei.createnetherindustry.info.fools_gold.line1");
    }


    private static void addInfo(IRecipeRegistration registration, ItemLike itemProvider, String... descriptionKeys) {
        ItemStack stack = new ItemStack(itemProvider.asItem());
        if (stack.isEmpty()) {
            return;
        }
        List<MutableComponent> components = Arrays.stream(descriptionKeys)
                .map(Component::translatable)
                .toList();
        registration.addItemStackInfo(stack, components.toArray(new Component[0]));
    }
}
