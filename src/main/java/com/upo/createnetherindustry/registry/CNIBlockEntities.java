package com.upo.createnetherindustry.registry;


import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.upo.createnetherindustry.CreateNetherIndustry;
import com.upo.createnetherindustry.content.blockentities.SoulCondenserBlockEntity;

public class CNIBlockEntities {

     public static final BlockEntityEntry<SoulCondenserBlockEntity> SOUL_CONDENSER = CreateNetherIndustry.REGISTRATE
         .blockEntity("soul_condenser", SoulCondenserBlockEntity::new)
         .validBlocks(CNIBlocks.SOUL_CONDENSER)
         .register();
}
