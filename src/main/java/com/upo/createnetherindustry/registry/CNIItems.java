package com.upo.createnetherindustry.registry;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.upo.createnetherindustry.CreateNetherIndustry;
import com.upo.createnetherindustry.content.items.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class CNIItems {
    private static final CreateRegistrate REGISTRATE = CreateNetherIndustry.registrate();

    public static final ItemEntry<SoulItem> SOUL_ITEM = REGISTRATE
            .item("soul_item", SoulItem::new)
            .lang("魂灵颗粒")
            .properties(p -> p
                            .stacksTo(64)
                            .rarity(Rarity.COMMON)
            )
            .register();
    public static final ItemEntry<SoulBottleItem> SOUL_BOTTLE_ITEM = REGISTRATE
            .item("soul_bottle_item",SoulBottleItem::new)
            .lang("稀薄魂灵之瓶")
            .properties(p -> p
                            .stacksTo(16)
                            .rarity(Rarity.COMMON)
            )
            .register();
    public static final ItemEntry<ThickSoulBottleItem> THICK_SOUL_BOTTLE_ITEM = REGISTRATE
            .item("thick_soul_bottle_item", ThickSoulBottleItem::new)
            .lang("浓稠魂灵之瓶")
            .properties(p -> p
                    .stacksTo(16)
                    .rarity(Rarity.COMMON)
            )
            .register();
    public static final ItemEntry<MysteriousAncientMechanismItem> ANCIENT_MECHANISM = REGISTRATE
            .item("mysterious_ancient_mechanism",MysteriousAncientMechanismItem::new)
            .lang("神秘远古构件")
            .properties(p -> p
                    .stacksTo(64)
                    .rarity(Rarity.EPIC)
                    .fireResistant()
            )
            .register();
    public static final ItemEntry<BlazeTwigItem> BLAZE_TWIG = REGISTRATE
            .item("blaze_twig",BlazeTwigItem::new)
            .lang("烈焰枝条")
            .properties(p -> p
                    .stacksTo(64)
                    .rarity(Rarity.UNCOMMON)
                    .fireResistant()
            )
            .register();



    public static final ItemEntry<Item> SOUL_BLAZE_ROD = REGISTRATE
            .item("soul_blaze_rod", Item::new)
            .lang("萦魂烈焰棒")
            .properties(p -> p.stacksTo(64))
            .register();
    public static final ItemEntry<Item> INCOMPLETE_SKELETON_SKULL = REGISTRATE
            .item("incomplete_skeleton_skull", Item::new)
            .lang("未完成的骷髅头颅")
            .properties(p -> p.stacksTo(64))
            .register();
    public static final ItemEntry<Item> DEAD_BLAZE_ROD = REGISTRATE
            .item("dead_blaze_rod", Item::new)
            .lang("失活烈焰棒")
            .properties(p -> p.stacksTo(64))
            .register();
    public static final ItemEntry<Item> BLAZE_FRUIT = REGISTRATE
            .item("blaze_fruit", Item::new)
            .lang("烈焰果实")
            .properties(p -> p.stacksTo(64))
            .register();


    public static void register() {}
}
