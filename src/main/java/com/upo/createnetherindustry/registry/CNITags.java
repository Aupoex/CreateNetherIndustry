package com.upo.createnetherindustry.registry;

import com.upo.createnetherindustry.CreateNetherIndustry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class CNITags {
    //自定义Tag
    public static final TagKey<Block> FAN_SOUL_STRIPPING_CATALYSTS =
            create("fan_soul_stripping_catalysts");
    public static final TagKey<Block> NYLIUM_FARMLAND =
            create("nylium_farmland");

    //引用Tag
    public static final TagKey<Block> CREATE_FAN_TRANSPARENT =
            create(ResourceLocation.fromNamespaceAndPath("create", "fan_transparent"));
    public static final TagKey<Block> PICKAXE_MINEABLE =
            create(ResourceLocation.fromNamespaceAndPath("minecraft", "mineable/pickaxe"));


    private static TagKey<Block> create(String name) {
        return TagKey.create(Registries.BLOCK, CreateNetherIndustry.asResource(name));
    }

    private static TagKey<Block> create(ResourceLocation name) {
        return TagKey.create(Registries.BLOCK, name);
    }

    public static void register() {}
}