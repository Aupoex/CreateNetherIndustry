package com.upo.createnetherindustry.ponder;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.upo.createnetherindustry.CreateNetherIndustry;
import com.upo.createnetherindustry.registry.CNIBlocks;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class CNIPonderTags {

    public static final ResourceLocation NETHER_INDUSTRY_TAG_ID =
            ResourceLocation.fromNamespaceAndPath(CreateNetherIndustry.MODID, "nether_industry");

    public static void register(PonderTagRegistrationHelper<ResourceLocation> helper) {

        helper.registerTag(NETHER_INDUSTRY_TAG_ID)
                .addToIndex()
                .item(CNIBlocks.SOUL_CONDENSER.get())
                .title("下界工业")
                .description("在下界建造你的产线")
                .register();

        PonderTagRegistrationHelper<RegistryEntry<?, ?>> ENTRY_HELPER = helper.withKeyFunction(RegistryEntry::getId);
        ENTRY_HELPER.addToTag(NETHER_INDUSTRY_TAG_ID)
                .add(CNIBlocks.SOUL_CONDENSER);

    }

}
