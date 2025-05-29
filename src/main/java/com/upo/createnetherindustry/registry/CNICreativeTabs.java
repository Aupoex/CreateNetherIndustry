package com.upo.createnetherindustry.registry;

import com.upo.createnetherindustry.CreateNetherIndustry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CNICreativeTabs {

    public static final DeferredRegister<CreativeModeTab> TAB_REGISTER =
                DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateNetherIndustry.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = TAB_REGISTER.register("main_tab",
                () -> CreativeModeTab.builder()
                        .icon(() -> new ItemStack(CNIItems.SOUL_ITEM.get()))
                        .title(Component.translatable("creativetab.createeggproduction.main_tab"))
                        .build()
        );

    public static void register(IEventBus modEventBus) {
        TAB_REGISTER.register(modEventBus);
    }
}

