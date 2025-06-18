package com.upo.createnetherindustry.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.loading.DatagenModLoader;
import java.util.concurrent.CompletableFuture;
import static com.upo.createnetherindustry.CreateNetherIndustry.MODID;

@Mod(MODID)
public class CNIData {

    public CNIData(IEventBus modBus) {
        if (!DatagenModLoader.isRunningDataGen()) {
            return;
        }
        modBus.register(this);
    }

    @SubscribeEvent
    public void generate(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();


        CNIBlockTagsProvider blockTagProvider = new CNIBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagProvider);
        generator.addProvider(event.includeServer(), new CNIItemTagsProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new CNIRecipeProvider(packOutput, lookupProvider));
    }
}

