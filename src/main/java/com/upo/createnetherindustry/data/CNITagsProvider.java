package com.upo.createnetherindustry.data;

import com.upo.createnetherindustry.CreateNetherIndustry;
import com.upo.createnetherindustry.registry.CNIBlocks;
import com.upo.createnetherindustry.registry.CNITags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class CNITagsProvider extends BlockTagsProvider {

    public CNITagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                           @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CreateNetherIndustry.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        //自定义标签添加
        this.tag(CNITags.FAN_SOUL_STRIPPING_CATALYSTS)
                .add(CNIBlocks.SOUL_STRIPPING_MEDIUM.get());
        this.tag(CNITags.NYLIUM_FARMLAND)
                .add(CNIBlocks.CRIMSON_NYLIUM_FARMLAND.get(),
                     CNIBlocks.WARPED_NYLIUM_FARMLAND.get()
                );

        //其他标签添加
        this.tag(CNITags.CREATE_FAN_TRANSPARENT)
                .add(CNIBlocks.SOUL_STRIPPING_MEDIUM.get());
        this.tag(CNITags.PICKAXE_MINEABLE)
                .add(CNIBlocks.CRIMSON_NYLIUM_FARMLAND.get(),
                     CNIBlocks.WARPED_NYLIUM_FARMLAND.get(),
                     CNIBlocks.SOUL_STRIPPING_MEDIUM.get()
                );

    }

    @Override
    public String getName() {
        return "Create Nether Industry Block Tags";
    }

}

