package com.upo.createnetherindustry;

import com.tterrag.registrate.providers.RegistrateDataProvider;
import com.upo.createnetherindustry.content.recipes.condenser.CondenserRecipeSerializers;
import com.upo.createnetherindustry.content.recipes.condenser.CondenserRecipeType;
import com.upo.createnetherindustry.data.*;
import com.upo.createnetherindustry.ponder.CNIPonderPlugin;
import com.upo.createnetherindustry.registry.*;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import com.upo.createnetherindustry.client.render.SoulCondenserRenderer;
import java.util.concurrent.CompletableFuture;


@Mod(CreateNetherIndustry.MODID)
public class CreateNetherIndustry
{
    public static final String MODID = "createnetherindustry";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID).defaultCreativeTab(CNICreativeTabs.MAIN_TAB.getKey());


    public CreateNetherIndustry(IEventBus modEventBus, ModContainer modContainer)
    {
        REGISTRATE.registerEventListeners(modEventBus);
        CNIItems.register();
        CNIBlocks.register();
        CNIFluids.register();
        CNITags.register();
        CNIRecipes.register(modEventBus);
        CondenserRecipeType.register(modEventBus);
        CondenserRecipeSerializers.register(modEventBus);
        CNIFanProcessingTypes.register(modEventBus);
        CNICreativeTabs.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(CreateNetherIndustry::gatherData);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            BlockEntityRenderers.register(CNIBlockEntities.SOUL_CONDENSER.get(), SoulCondenserRenderer::new);
            /*
            BlockEntityType<SoulCondenserBlockEntity> beType = CNIBlockEntities.SOUL_CONDENSER.get();
            SimpleBlockEntityVisualizer.builder(beType).factory(SoulCondenserVisual::new).skipVanillaRender(blockEntity -> true).apply();
             */
            CNIStress.registerAllStressValues();
            CNIPartials.init();
            ItemBlockRenderTypes.setRenderLayer(CNIBlocks.SOUL_STRIPPING_MEDIUM.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(CNIBlocks.BLAZE_TWIG_CROP.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(CNIBlocks.SOUL_CONDENSER.get(), RenderType.cutoutMipped());
            PonderIndex.addPlugin(new CNIPonderPlugin());
            CNIFluids.registerFluidInteractions();
        });
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    { @SubscribeEvent public static void onClientSetup(FMLClientSetupEvent event) {}}

    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        RegistrateDataProvider registrateDataProvider = new RegistrateDataProvider(REGISTRATE, MODID, event);
        generator.addProvider(true, REGISTRATE.setDataProvider(registrateDataProvider));
        CNITagsProvider blockTagProvider = new CNITagsProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagProvider);
        generator.addProvider(event.includeServer(), new CNIRecipeProvider(packOutput, lookupProvider));


    }
    private void registerCapabilities(final RegisterCapabilitiesEvent event) {

        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                CNIBlockEntities.SOUL_CONDENSER.get(),
                (be, context) -> be.getFluidHandlerCapability(context)
        );


    }

}
