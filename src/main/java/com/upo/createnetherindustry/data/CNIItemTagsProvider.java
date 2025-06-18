package com.upo.createnetherindustry.data;

import com.upo.createnetherindustry.CreateNetherIndustry;
import com.upo.createnetherindustry.registry.CNIItems;
import com.upo.createnetherindustry.registry.CNITags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;


public class CNIItemTagsProvider extends ItemTagsProvider{

    public CNIItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                               CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, CreateNetherIndustry.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(CNITags.PIGLIN_LOVED)
                .add(CNIItems.FOOLS_GOLD.get());

    }

    @Override
    public String getName() {
        return "Create Nether Industry Item Tags";
    }
}
