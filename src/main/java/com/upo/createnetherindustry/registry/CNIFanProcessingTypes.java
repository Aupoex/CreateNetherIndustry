package com.upo.createnetherindustry.registry;

import com.simibubi.create.api.registry.CreateRegistries;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.upo.createnetherindustry.CreateNetherIndustry;
import com.upo.createnetherindustry.content.kinetics.fan.soul_stripping.SoulStrippingFanProcessingType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CNIFanProcessingTypes {

    private static final DeferredRegister<FanProcessingType> TYPES =
            DeferredRegister.create(CreateRegistries.FAN_PROCESSING_TYPE, CreateNetherIndustry.MODID);

    public static final DeferredHolder<FanProcessingType, SoulStrippingFanProcessingType> SOUL_STRIPPING =
            TYPES.register("soul_stripping", SoulStrippingFanProcessingType::new);

    public static void register(IEventBus modBus) {
        TYPES.register(modBus);
    }

}

