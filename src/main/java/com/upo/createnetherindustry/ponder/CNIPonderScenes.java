package com.upo.createnetherindustry.ponder;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.upo.createnetherindustry.registry.CNIBlocks;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import com.upo.createnetherindustry.ponder.scenes.SoulCondenserScenes;

public class CNIPonderScenes {

    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<RegistryEntry<?, ?>> ENTRY_HELPER = helper.withKeyFunction(RegistryEntry::getId);
        ENTRY_HELPER.forComponents(CNIBlocks.SOUL_CONDENSER)
                .addStoryBoard("condenser", SoulCondenserScenes::processing,CNIPonderTags.NETHER_INDUSTRY_TAG_ID);

    }

}
