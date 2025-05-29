package com.upo.createnetherindustry.registry;

import com.simibubi.create.api.stress.BlockStressValues;
import net.minecraft.world.level.block.Block;

public class CNIStress {

    public static void registerAllStressValues() {

        Block soulCondenserInstance = CNIBlocks.SOUL_CONDENSER.get();
        double stressImpact = 8.0;
        BlockStressValues.IMPACTS.register(soulCondenserInstance, () -> stressImpact);

    }
}
