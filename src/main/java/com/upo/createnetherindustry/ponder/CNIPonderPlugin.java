package com.upo.createnetherindustry.ponder;

import com.upo.createnetherindustry.CreateNetherIndustry;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CNIPonderPlugin implements PonderPlugin{
    @Override
    public @NotNull String getModId() { return CreateNetherIndustry.MODID; }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        CNIPonderScenes.register(helper);
    }
    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        CNIPonderTags.register(helper);
    }

}
