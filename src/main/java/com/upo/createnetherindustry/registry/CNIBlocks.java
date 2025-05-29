package com.upo.createnetherindustry.registry;

import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.upo.createnetherindustry.CreateNetherIndustry;
import com.upo.createnetherindustry.content.blocks.SoulCondenserBlock;
import com.upo.createnetherindustry.content.blocks.corp.BlazeCropBlock;
import com.upo.createnetherindustry.content.blocks.nylium_farmland.CrimsonNyliumFarmlandBlock;
import com.upo.createnetherindustry.content.blocks.corp.GoldenCarrotCropBlock;
import com.upo.createnetherindustry.content.blocks.SoulStrippingBlock;
import com.upo.createnetherindustry.content.blocks.nylium_farmland.WarpedNyliumFarmlandBlock;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import com.upo.createnetherindustry.content.blockentities.SoulCondenserBlockEntity;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;


import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static com.upo.createnetherindustry.CreateNetherIndustry.REGISTRATE;

public class CNIBlocks {
    //祛魂栅
    public static final BlockEntry<SoulStrippingBlock> SOUL_STRIPPING_MEDIUM = REGISTRATE
            .block("soul_stripping_block", SoulStrippingBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p
                    .mapColor(MapColor.COLOR_CYAN)
                    .sound(SoundType.NETHER_GOLD_ORE)
                    .strength(2.0f, 6.0f)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()
            )
            .transform(pickaxeOnly())
            .blockstate((context, provider) -> {
                var existingModel = provider.models().getExistingFile(
                        CreateNetherIndustry.asResource("block/" + context.getName())
                );
                provider.simpleBlock(context.get(), existingModel);
            })
            .simpleItem()
            .register();

    //魂灵凝集器
    public static final BlockEntry<SoulCondenserBlock> SOUL_CONDENSER = REGISTRATE
            .block("soul_condenser", SoulCondenserBlock::new)
            .initialProperties(() -> Blocks.NETHERITE_BLOCK)
            .properties(props -> props
                    .mapColor(MapColor.COLOR_BLACK)
                    .sound(SoundType.NETHERITE_BLOCK)
                    .strength(4.0f, 10.0f)
                    .noOcclusion()
            )
            .transform(pickaxeOnly())
            .blockstate((ctx, prov) -> {
                ModelFile existingModel = prov.models().getExistingFile(prov.modLoc("block/" + ctx.getName()));
                prov.horizontalBlock(ctx.getEntry(), existingModel);
            })
            .lang("魂灵凝集器")
            .blockEntity(SoulCondenserBlockEntity::new)
            // .renderer(() -> SoulCondenserRenderer::new)
            .build()
            .item()
            .build()
            .register();



    //菌岩耕地
    public static final BlockEntry<WarpedNyliumFarmlandBlock> WARPED_NYLIUM_FARMLAND = REGISTRATE
            .block("warped_nylium_farmland", WarpedNyliumFarmlandBlock::new)
            .initialProperties(() -> Blocks.WARPED_NYLIUM)
            .properties(p -> p.mapColor(MapColor.WARPED_NYLIUM).strength(0.6F).sound(SoundType.NYLIUM).noOcclusion().requiresCorrectToolForDrops())
            .blockstate((ctx, prov) -> {
                ModelFile dryModel = prov.models().getExistingFile(prov.modLoc("block/nylium_farmland/" + ctx.getName() + "_dry"));
                ModelFile moistModel = prov.models().getExistingFile(prov.modLoc("block/nylium_farmland/" + ctx.getName() + "_moist"));
                prov.getVariantBuilder(ctx.getEntry())
                        .forAllStates(state -> {
                            ModelFile modelToUse = state.getValue(FarmBlock.MOISTURE) == FarmBlock.MAX_MOISTURE ? moistModel : dryModel;
                            return ConfiguredModel.builder().modelFile(modelToUse).build();
                        });
            })
            .loot((lootTables, block) -> {
                LootTable.Builder lootTableBuilder = LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Blocks.NETHERRACK)
                                        )
                        );
                lootTables.add(block, lootTableBuilder);
            })
            .item()
            .model((ctx, prov) -> prov.withExistingParent(ctx.getName(),
                    prov.modLoc("block/nylium_farmland/" + ctx.getName() + "_dry")))
            .build()
            .register();
    public static final BlockEntry<CrimsonNyliumFarmlandBlock> CRIMSON_NYLIUM_FARMLAND = REGISTRATE
             .block("crimson_nylium_farmland", CrimsonNyliumFarmlandBlock::new)
             .initialProperties(() -> Blocks.CRIMSON_NYLIUM)
             .properties(p -> p.mapColor(MapColor.CRIMSON_NYLIUM).strength(0.6F).sound(SoundType.NYLIUM).noOcclusion().requiresCorrectToolForDrops())
             .blockstate((ctx, prov) -> {
                ModelFile dryModel = prov.models().getExistingFile(prov.modLoc("block/nylium_farmland/" + ctx.getName() + "_dry"));
                ModelFile moistModel = prov.models().getExistingFile(prov.modLoc("block/nylium_farmland/" + ctx.getName() + "_moist"));
                prov.getVariantBuilder(ctx.getEntry())
                        .forAllStates(state -> {
                            ModelFile modelToUse = state.getValue(FarmBlock.MOISTURE) == FarmBlock.MAX_MOISTURE ? moistModel : dryModel;
                            return ConfiguredModel.builder().modelFile(modelToUse).build();
                        });
             })
            .loot((lootTables, block) -> {
                LootTable.Builder lootTableBuilder = LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Blocks.NETHERRACK)
                                )
                        );
                lootTables.add(block, lootTableBuilder);
            })
             .item()
             .model((ctx, prov) -> prov.withExistingParent(ctx.getName(),
                     prov.modLoc("block/nylium_farmland/" + ctx.getName() + "_dry")))
             .build()
             .register();

    //作物金胡萝卜
    public static final BlockEntry<GoldenCarrotCropBlock> GOLDEN_CARROT_CROP = REGISTRATE
            .block("golden_carrot_crop", GoldenCarrotCropBlock::new)
            .initialProperties(() -> Blocks.CARROTS)
            .properties(p -> p.noCollission().randomTicks().instabreak().sound(SoundType.CROP))
            .blockstate((ctx, prov) -> {
                prov.getVariantBuilder(ctx.getEntry())
                        .forAllStates(state -> {
                            int age = state.getValue(GoldenCarrotCropBlock.AGE);
                            String modelPath = "block/golden_carrot_crop/golden_carrot_crop_stage" + age;
                            ModelFile modelFile = prov.models().getExistingFile(prov.modLoc(modelPath));
                            return ConfiguredModel.builder().modelFile(modelFile).build();
                        });
            })
            .loot((registrateLootTables, block) -> {
                LootTable.Builder tableBuilder = LootTable.lootTable();
                StatePropertiesPredicate.Builder matureStatePredicate = StatePropertiesPredicate.Builder.properties()
                        .hasProperty(GoldenCarrotCropBlock.AGE, GoldenCarrotCropBlock.MAX_AGE);
                LootItemCondition.Builder matureCondition = LootItemBlockStatePropertyCondition
                        .hasBlockStateProperties(block)
                        .setProperties(matureStatePredicate);
                tableBuilder.withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.GOLDEN_CARROT)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                        )
                        .when(matureCondition)
                );
                for (int age = 0; age < GoldenCarrotCropBlock.MAX_AGE; age++) {
                    StatePropertiesPredicate.Builder immatureStateSpecificAgePredicate = StatePropertiesPredicate.Builder.properties()
                            .hasProperty(GoldenCarrotCropBlock.AGE, age);
                    LootItemCondition.Builder immatureSpecificAgeCondition = LootItemBlockStatePropertyCondition
                            .hasBlockStateProperties(block)
                            .setProperties(immatureStateSpecificAgePredicate);

                    tableBuilder.withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Items.GOLDEN_CARROT)
                                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                            )
                            .when(immatureSpecificAgeCondition)
                    );
                }
                registrateLootTables.add(block, tableBuilder);
            })
            .register();

    //作物烈焰枝条
    public static final BlockEntry<BlazeCropBlock> BLAZE_TWIG_CROP = REGISTRATE
            .block("blaze_twig_crop", BlazeCropBlock::new)
            .initialProperties(() -> Blocks.WHEAT)
            .properties(p -> p.randomTicks().instabreak().sound(SoundType.CROP)
                    .lightLevel(state -> {
                        if (state.hasProperty(BlazeCropBlock.AGE)) {
                            int age = state.getValue(BlazeCropBlock.AGE);
                            if (age == BlazeCropBlock.MAX_AGE) return 14;
                            if (age == BlazeCropBlock.MAX_AGE - 1) return 12;
                            if (age == BlazeCropBlock.MAX_AGE - 2) return 10;
                            if (age == BlazeCropBlock.MAX_AGE - 3) return 8;
                            if (age == BlazeCropBlock.MAX_AGE - 4) return 6;
                            return 0;
                        }
                        return 0;
                    }))
            .blockstate((ctx, prov) -> {
                prov.getVariantBuilder(ctx.getEntry())
                        .forAllStates(state -> {
                            int age = state.getValue(BlazeCropBlock.AGE);
                            String modelPath = "block/blaze_twig_crop/blaze_twig_crop_stage" + age;
                            ModelFile modelFile = prov.models().getExistingFile(prov.modLoc(modelPath));
                            return ConfiguredModel.builder().modelFile(modelFile).build();
                        });
            })
            .loot((registrateLootTables, block) -> {
                LootTable.Builder tableBuilder = LootTable.lootTable();
                StatePropertiesPredicate.Builder matureStatePredicate = StatePropertiesPredicate.Builder.properties()
                        .hasProperty(BlazeCropBlock.AGE, BlazeCropBlock.MAX_AGE);
                LootItemCondition.Builder matureCondition = LootItemBlockStatePropertyCondition
                        .hasBlockStateProperties(block)
                        .setProperties(matureStatePredicate);
                tableBuilder.withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.BLAZE_POWDER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                        )
                        .when(matureCondition)
                );
                for (int age = 0; age < BlazeCropBlock.MAX_AGE; age++) {
                    StatePropertiesPredicate.Builder immatureStateSpecificAgePredicate = StatePropertiesPredicate.Builder.properties()
                            .hasProperty(BlazeCropBlock.AGE, age);
                    LootItemCondition.Builder immatureSpecificAgeCondition = LootItemBlockStatePropertyCondition
                            .hasBlockStateProperties(block)
                            .setProperties(immatureStateSpecificAgePredicate);

                    tableBuilder.withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(CNIItems.BLAZE_TWIG)
                                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                            )
                            .when(immatureSpecificAgeCondition)
                    );
                }
                registrateLootTables.add(block, tableBuilder);
            })
            .register();


    public static void register() {}
}
