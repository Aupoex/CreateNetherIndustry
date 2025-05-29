package com.upo.createnetherindustry.content.recipes;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.createmod.catnip.codecs.stream.CatnipStreamCodecBuilders;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CNIRecipeParams extends ProcessingRecipeParams {

    protected static final ResourceLocation UNKNOWN_ID = ResourceLocation.withDefaultNamespace("unknown");

    public CNIRecipeParams(ResourceLocation id) {
        super(id);
    }

    protected static <P extends CNIRecipeParams> MapCodec<P> createCodec(Function<ResourceLocation, P> factory) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                        Codec.either(FluidIngredient.CODEC, Ingredient.CODEC).listOf().fieldOf("ingredients")
                                .forGetter(CNIRecipeParams::getIngredientsForCodec),
                        Codec.either(FluidStack.CODEC, ProcessingOutput.CODEC).listOf().fieldOf("results")
                                .forGetter(CNIRecipeParams::getResultsForCodec),
                        Codec.INT.optionalFieldOf("processingTime", 0)
                                .forGetter(p -> p.processingDuration),
                        HeatCondition.CODEC.optionalFieldOf("heatRequirement", HeatCondition.NONE)
                                .forGetter(p -> p.requiredHeat))
                .apply(instance, (ingredients, results, processingDuration, requiredHeat) -> {
                    P params = factory.apply(UNKNOWN_ID);
                    ingredients.forEach(either -> either
                            .ifRight(params.ingredients::add)
                            .ifLeft(params.fluidIngredients::add));
                    results.forEach(either -> either
                            .ifRight(params.results::add)
                            .ifLeft(params.fluidResults::add));
                    params.processingDuration = processingDuration;
                    params.requiredHeat = requiredHeat;
                    return params;
                }));
    }


    protected static <P extends CNIRecipeParams> StreamCodec<RegistryFriendlyByteBuf, P> createStreamCodec(Function<ResourceLocation, P> constructor) {
        return StreamCodec.of(
                (buffer, params) -> params.writeToBuffer(buffer),
                (buffer) -> {
                    P params = constructor.apply(UNKNOWN_ID);
                    params.readFromBuffer(buffer);
                    return params;
                }
        );
    }

    protected List<Either<FluidIngredient, Ingredient>> getIngredientsForCodec() {
        List<Either<FluidIngredient, Ingredient>> combinedIngredients = new ArrayList<>();
        this.fluidIngredients.forEach(fi -> combinedIngredients.add(Either.left(fi)));
        this.ingredients.forEach(i -> combinedIngredients.add(Either.right(i)));
        return combinedIngredients;
    }

    protected List<Either<FluidStack, ProcessingOutput>> getResultsForCodec() {
        List<Either<FluidStack, ProcessingOutput>> combinedResults = new ArrayList<>();
        this.fluidResults.forEach(fs -> combinedResults.add(Either.left(fs)));
        this.results.forEach(po -> combinedResults.add(Either.right(po)));
        return combinedResults;
    }

    protected void writeToBuffer(RegistryFriendlyByteBuf buffer) {
        CatnipStreamCodecBuilders.nonNullList(Ingredient.CONTENTS_STREAM_CODEC).encode(buffer, ingredients);
        CatnipStreamCodecBuilders.nonNullList(ProcessingOutput.STREAM_CODEC).encode(buffer, results);
        CatnipStreamCodecBuilders.nonNullList(FluidIngredient.STREAM_CODEC).encode(buffer, fluidIngredients);
        CatnipStreamCodecBuilders.nonNullList(FluidStack.STREAM_CODEC).encode(buffer, fluidResults);
        ByteBufCodecs.VAR_INT.encode(buffer, processingDuration);
        HeatCondition.STREAM_CODEC.encode(buffer, requiredHeat);
    }

    protected void readFromBuffer(RegistryFriendlyByteBuf buffer) {
        ingredients = CatnipStreamCodecBuilders.nonNullList(Ingredient.CONTENTS_STREAM_CODEC).decode(buffer);
        results = CatnipStreamCodecBuilders.nonNullList(ProcessingOutput.STREAM_CODEC).decode(buffer);
        fluidIngredients = CatnipStreamCodecBuilders.nonNullList(FluidIngredient.STREAM_CODEC).decode(buffer);
        fluidResults = CatnipStreamCodecBuilders.nonNullList(FluidStack.STREAM_CODEC).decode(buffer);
        processingDuration = ByteBufCodecs.VAR_INT.decode(buffer);
        requiredHeat = HeatCondition.STREAM_CODEC.decode(buffer);
    }
}

