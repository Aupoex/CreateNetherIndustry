package com.upo.createnetherindustry.data;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.fluids.transfer.EmptyingRecipe;
import com.simibubi.create.content.kinetics.millstone.MillingRecipe;
import com.simibubi.create.content.kinetics.mixer.MixingRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.content.kinetics.fan.processing.SplashingRecipe;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.upo.createnetherindustry.CreateNetherIndustry;
import com.upo.createnetherindustry.content.recipes.soulstrip.SoulStrippingRecipe;
import com.upo.createnetherindustry.content.recipes.condenser.CondensingRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.core.HolderLookup;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import static com.simibubi.create.AllBlocks.*;
import static com.simibubi.create.AllItems.*;
import static com.upo.createnetherindustry.registry.CNIBlocks.SOUL_CONDENSER;
import static com.upo.createnetherindustry.registry.CNIBlocks.SOUL_STRIPPING_MEDIUM;
import static com.upo.createnetherindustry.registry.CNIFluids.THICK_SOUL_SYRUP;
import static com.upo.createnetherindustry.registry.CNIFluids.THIN_SOUL_FLUID;
import static com.upo.createnetherindustry.registry.CNIItems.*;
import static net.minecraft.world.item.Items.*;
import java.util.concurrent.CompletableFuture;

public class CNIRecipeProvider extends RecipeProvider {

    public CNIRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        //————————祛魂————————
        //灵魂沙
        new StandardProcessingRecipe.Builder<>(SoulStrippingRecipe::new, CreateNetherIndustry.asResource("soul_sand_stripping"))
                .require(SOUL_SAND)
                .output(SAND)
                .output(0.5f, SOUL_ITEM.get(), 1)
                .build(recipeOutput);
        //灵魂土
        new StandardProcessingRecipe.Builder<>(SoulStrippingRecipe::new, CreateNetherIndustry.asResource("soul_soil_stripping"))
                .require(SOUL_SOIL)
                .output(DIRT)
                .output(0.5f, SOUL_ITEM.get(), 1)
                .build(recipeOutput);
        //灵魂火把
        new StandardProcessingRecipe.Builder<>(SoulStrippingRecipe::new, CreateNetherIndustry.asResource("soul_torch_stripping"))
                .require(SOUL_TORCH)
                .output(TORCH)
                .output(1, SOUL_ITEM.get(), 1)
                .build(recipeOutput);
        //灵魂灯笼
        new StandardProcessingRecipe.Builder<>(SoulStrippingRecipe::new, CreateNetherIndustry.asResource("soul_lantern_stripping"))
                .require(SOUL_LANTERN)
                .output(LANTERN)
                .output(1, SOUL_ITEM.get(), 1)
                .build(recipeOutput);
        //灵魂营火
        new StandardProcessingRecipe.Builder<>(SoulStrippingRecipe::new, CreateNetherIndustry.asResource("soul_campfire_stripping"))
                .require(SOUL_CAMPFIRE)
                .output(CAMPFIRE)
                .output(1, SOUL_ITEM.get(), 1)
                .build(recipeOutput);
        //黑石
        new StandardProcessingRecipe.Builder<>(SoulStrippingRecipe::new, CreateNetherIndustry.asResource("black_stone_stripping"))
                .require(BLACKSTONE)
                .output(COBBLESTONE)
                .output(0.5f, SOUL_ITEM.get(), 1)
                .build(recipeOutput);
        //哭泣黑曜石
        new StandardProcessingRecipe.Builder<>(SoulStrippingRecipe::new, CreateNetherIndustry.asResource("crying_obsidian_stripping"))
                .require(CRYING_OBSIDIAN)
                .output(OBSIDIAN)
                .output(0.5f, SOUL_ITEM.get(), 1)
                .build(recipeOutput);
        //腐肉
        new StandardProcessingRecipe.Builder<>(SoulStrippingRecipe::new, CreateNetherIndustry.asResource("rotten_flesh_stripping"))
                .require(ROTTEN_FLESH)
                .output(LEATHER)
                .output(0.2f, SOUL_ITEM.get(), 1)
                .build(recipeOutput);
        //地狱疣
        new StandardProcessingRecipe.Builder<>(SoulStrippingRecipe::new, CreateNetherIndustry.asResource("nether_wart_stripping"))
                .require(NETHER_WART)
                .output(WHEAT_SEEDS)
                .output(0.1f, SOUL_ITEM.get(), 1)
                .build(recipeOutput);

        //——————液体转化————————
        Fluid milkFluidInstance = BuiltInRegistries.FLUID.get(ResourceLocation.withDefaultNamespace("milk"));
        Fluid thinSoulFluidInstance = BuiltInRegistries.FLUID.get(ResourceLocation.fromNamespaceAndPath("createnetherindustry", "thin_soul_fluid"));
        Fluid thickSoulSyrupInstance = BuiltInRegistries.FLUID.get(ResourceLocation.fromNamespaceAndPath("createnetherindustry", "thick_soul_syrup"));
        //水转奶
        CondensingRecipe.builder(CreateNetherIndustry.asResource("water_to_milk"))
                .require(FluidIngredient.fromFluidStack(new FluidStack(Fluids.WATER, 100)))
                .output(new FluidStack(milkFluidInstance, 100))
                .build(recipeOutput);
        //稀薄转浓稠
        CondensingRecipe.builder(CreateNetherIndustry.asResource("thin_to_thick"))
                .require(FluidIngredient.fromFluidStack(new FluidStack(thinSoulFluidInstance, 100)))
                .output(new FluidStack(thickSoulSyrupInstance, 100))
                .build(recipeOutput);

        //————————洗涤————————
        //烈焰果实
        new StandardProcessingRecipe.Builder<>(SplashingRecipe::new,
                ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "gunpowder_from_blaze_fruit"))
                .require(BLAZE_FRUIT.get())
                .output(GUNPOWDER)
                .build(recipeOutput);
        //岩浆膏
        new StandardProcessingRecipe.Builder<>(SplashingRecipe::new,
                ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "slime_ball_from_magma_cream"))
                .require(MAGMA_CREAM)
                .output(SLIME_BALL)
                .build(recipeOutput);

        //——————工作台合成——————
        //祛魂栅
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SOUL_STRIPPING_MEDIUM.get())
                .pattern("III")
                .pattern("ICI")
                .pattern("III")
                .define('I', IRON_BARS)
                .define('C', ANCIENT_MECHANISM)
                .unlockedBy("has_ancient_mechanism", has(ANCIENT_MECHANISM))
                .unlockedBy("has_iron_bars", has(IRON_BARS))
                .save(recipeOutput,ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "crafting/soul_stripping_medium"));
        //魂灵凝集器
        ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "glass_blocks"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SOUL_CONDENSER.get())
                .pattern(" P ")
                .pattern("GCG")
                .pattern("TTT")
                .define('P', PITCHER_PLANT)
                .define('G', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "glass_blocks")))
                .define('C', COGWHEEL)
                .define('T', COPPER_CASING)
                .unlockedBy("has_pitcher_plant", has(PITCHER_PLANT))
                .unlockedBy("has_copper_casing", has(COPPER_CASING))
                .save(recipeOutput,ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "crafting/soul_condenser"));
        //烈焰果实
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BLAZE_POWDER, 2)
                .requires(BLAZE_FRUIT)
                .unlockedBy("has_blaze_fruit", has(BLAZE_FRUIT))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "crafting/blaze_powder_from_fruit"));
        //烈焰枝条
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BLAZE_TWIG)
                .pattern("  T")
                .pattern(" B ")
                .pattern("G  ")
                .define('T', ItemTags.SAPLINGS)
                .define('B', BLAZE_ROD)
                .define('G', GOLD_BLOCK)
                .unlockedBy("has_blaze_rod", has(BLAZE_ROD))
                .save(recipeOutput,ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "crafting/blaze_twig"));
        //萦魂烈焰稿
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SOUL_BLAZE_PICKAXE)
                .pattern("GGZ")
                .pattern(" YG")
                .pattern("Y G")
                .define('Z', AMETHYST_SHARD)
                .define('G', GOLD_INGOT)
                .define('Y', SOUL_BLAZE_ROD)
                .unlockedBy("has_gold_ingot", has(GOLD_INGOT))
                .unlockedBy("has_amethyst_shard", has(AMETHYST_SHARD))
                .save(recipeOutput,ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "crafting/soul_blaze_pickaxe"));
        //萦魂烈焰斧
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SOUL_BLAZE_AXE)
                .pattern("GGZ")
                .pattern("GY ")
                .pattern("Y  ")
                .define('Z', AMETHYST_SHARD)
                .define('G', GOLD_INGOT)
                .define('Y', SOUL_BLAZE_ROD)
                .unlockedBy("has_gold_ingot", has(GOLD_INGOT))
                .unlockedBy("has_amethyst_shard", has(AMETHYST_SHARD))
                .save(recipeOutput,ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "crafting/soul_blaze_axe"));
        //岩浆快艇
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, LAVA_SPEEDBOAT)
                .pattern("J  ")
                .pattern("HKK")
                .pattern("HBB")
                .define('J', PRECISION_MECHANISM)
                .define('H', BRASS_BLOCK)
                .define('K', BRASS_CASING)
                .define('B', STURDY_SHEET)
                .unlockedBy("has_precision_mechanism", has(PRECISION_MECHANISM))
                .unlockedBy("has_sturdy_sheet", has(STURDY_SHEET))
                .unlockedBy("has_brass_block", has(BRASS_BLOCK))
                .unlockedBy("has_brass_casing", has(BRASS_CASING))
                .save(recipeOutput,ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "crafting/lava_speedboat"));

        //————————注液————————
        //稀薄魂灵瓶
        new StandardProcessingRecipe.Builder<>(FillingRecipe::new,
                ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "soul_bottle_from_thin_soul_fluid"))
                .require(GLASS_BOTTLE)
                .require(FluidIngredient.fromFluid(THIN_SOUL_FLUID.get(), 250))
                .output(SOUL_BOTTLE_ITEM.get())
                .build(recipeOutput);
        //浓稠魂灵瓶
        new StandardProcessingRecipe.Builder<>(FillingRecipe::new,
                ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "soul_bottle_from_thick_soul_syrup"))
                .require(GLASS_BOTTLE)
                .require(FluidIngredient.fromFluid(THICK_SOUL_SYRUP.get(), 250))
                .output(THICK_SOUL_BOTTLE_ITEM.get())
                .build(recipeOutput);

        //萦魂烈焰棒
        new StandardProcessingRecipe.Builder<>(FillingRecipe::new,
                ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "soul_blaze_rod_from_thin_soul_fluid"))
                .require(BLAZE_ROD)
                .require(FluidIngredient.fromFluid(THICK_SOUL_SYRUP.get(), 250))
                .output(SOUL_BLAZE_ROD.get())
                .build(recipeOutput);
        //哭泣黑曜石
        new StandardProcessingRecipe.Builder<>(FillingRecipe::new,
                ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "crying_obsidian_from_thin_soul_fluid"))
                .require(OBSIDIAN)
                .require(FluidIngredient.fromFluid(THICK_SOUL_SYRUP.get(), 250))
                .output(CRYING_OBSIDIAN)
                .build(recipeOutput);

        //————————分液————————
        //魂灵颗粒
        new StandardProcessingRecipe.Builder<>(EmptyingRecipe::new,
                ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "soul_item_to_thin_soul_fluid"))
                .require(SOUL_ITEM)
                .output(THIN_SOUL_FLUID.get(), 250)
                .build(recipeOutput);

        //————————搅拌————————
        //凋零骷髅头颅
        new StandardProcessingRecipe.Builder<>(MixingRecipe::new,
                ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "skeleton_to_wither_skeleton_skull"))
                .require(SKELETON_SKULL)
                .require(WITHER_ROSE).require(WITHER_ROSE).require(WITHER_ROSE).require(WITHER_ROSE).require(WITHER_ROSE).require(WITHER_ROSE).require(WITHER_ROSE).require(WITHER_ROSE)
                .output(WITHER_SKELETON_SKULL)
                .requiresHeat(HeatCondition.SUPERHEATED)
                .build(recipeOutput);
        //愚人金
        new StandardProcessingRecipe.Builder<>(MixingRecipe::new,
                ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "fools_gold"))
                .require(IRON_NUGGET).require(IRON_NUGGET)
                .require(CINDER_FLOUR)
                .output(FOOLS_GOLD)
                .requiresHeat(HeatCondition.HEATED)
                .build(recipeOutput);

        //——————研磨————————
        //烈焰果实
        new StandardProcessingRecipe.Builder<>(MillingRecipe::new,
                ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "blaze_fruit_to_blaze_powder"))
                .require(BLAZE_FRUIT)
                .output(BLAZE_POWDER,3)
                .output(0.2f,BLAZE_POWDER)
                .build(recipeOutput);

        //————————序列组装————————
        //骷髅头颅
        new SequencedAssemblyRecipeBuilder(
                ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "sequenced_wither_skull_from_bones"))
                .require(BONE)
                .transitionTo(INCOMPLETE_SKELETON_SKULL)
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(BONE_BLOCK))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(BONE_BLOCK))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(BONE_MEAL))
                .loops(3)
                .addOutput(SKELETON_SKULL, 0.18f)
                .addOutput(BONE, 0.40f)
                .addOutput(BONE_MEAL, 0.42f)
                .build(recipeOutput);
        //烈焰棒
        new SequencedAssemblyRecipeBuilder(
                ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "sequenced_blaze_rod"))
                .require(BONE)
                .transitionTo(DEAD_BLAZE_ROD)
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(BLAZE_POWDER))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(BLAZE_POWDER))
                .addStep(FillingRecipe::new, rb -> rb.require(Fluids.LAVA, 500))
                .loops(2)
                .addOutput(BLAZE_ROD, 0.8f)
                .addOutput(BLAZE_POWDER, 0.2f)
                .build(recipeOutput);
        //恶魂之泪
        new SequencedAssemblyRecipeBuilder(
                ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "sequenced_ghast_tear"))
                .require(SNOWBALL)
                .transitionTo(OBSESSION_SNOW)
                .addStep(FillingRecipe::new, rb -> rb.require(FluidIngredient.fromFluid(THICK_SOUL_SYRUP.get(), 250)))
                .addStep(FillingRecipe::new, rb -> rb.require(FluidIngredient.fromFluid(THICK_SOUL_SYRUP.get(), 250)))
                .addStep(PressingRecipe::new, rb -> rb)
                .loops(3)
                .addOutput(GHAST_TEAR, 0.75f)
                .addOutput(SNOW_BLOCK, 0.15f)
                .addOutput(SOUL_ITEM, 0.10f)
                .build(recipeOutput);
        //远古构件
        new SequencedAssemblyRecipeBuilder(
                ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "sequenced_ancient_mechanism"))
                .require(AllItems.PRECISION_MECHANISM)
                .transitionTo(INCOMPLETE_ANCIENT_MECHANISM)
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(NETHER_BRICK))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(STURDY_SHEET))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(AMETHYST_SHARD))
                .addStep(DeployerApplicationRecipe::new, rb -> rb.require(POLISHED_ROSE_QUARTZ))
                .loops(4)
                .addOutput(ANCIENT_MECHANISM, 0.50f)
                .addOutput(AMETHYST_SHARD, 0.10f)
                .addOutput(NETHER_BRICK, 0.10f)
                .addOutput(STURDY_SHEET, 0.10f)
                .addOutput(STURDY_SHEET, 0.10f)
                .addOutput(INCOMPLETE_PRECISION_MECHANISM, 0.10f)
                .build(recipeOutput);
    }

}

