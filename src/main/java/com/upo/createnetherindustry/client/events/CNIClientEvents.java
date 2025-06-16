package com.upo.createnetherindustry.client.events;

import com.upo.createnetherindustry.CreateNetherIndustry;
import com.upo.createnetherindustry.client.model.LavaSeaSpeedboatModel;
import com.upo.createnetherindustry.ponder.CNIPonderPlugin;
import com.upo.createnetherindustry.registry.CNIBlockEntities;
import com.upo.createnetherindustry.registry.CNIBlocks;
import com.upo.createnetherindustry.registry.CNIEntities;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import com.upo.createnetherindustry.client.render.SoulCondenserRenderer;
import com.upo.createnetherindustry.client.render.LavaSpeedboatRenderer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = CreateNetherIndustry.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CNIClientEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(CNIBlocks.SOUL_STRIPPING_MEDIUM.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(CNIBlocks.BLAZE_TWIG_CROP.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(CNIBlocks.WITHER_BUSH_CROP.get(), RenderType.cutoutMipped());
            ItemBlockRenderTypes.setRenderLayer(CNIBlocks.SOUL_CONDENSER.get(), RenderType.cutoutMipped());
            PonderIndex.addPlugin(new CNIPonderPlugin());
        });
    }


    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(CNIBlockEntities.SOUL_CONDENSER.get(), SoulCondenserRenderer::new);

        event.registerEntityRenderer(CNIEntities.LAVA_SPEEDBOAT.get(), LavaSpeedboatRenderer::new);
    }


    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(LavaSeaSpeedboatModel.LAYER_LOCATION, LavaSeaSpeedboatModel::createBodyLayer);
    }
}
