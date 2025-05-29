package com.upo.createnetherindustry.registry;

import com.simibubi.create.api.stress.BlockStressValues;
import net.minecraft.world.level.block.Block;

public class CNIStress {

    public static void registerAllStressValues() { // 方法名可以自定义

        Block soulCondenserInstance = CNIBlocks.SOUL_CONDENSER.get(); // 示例路径
        double stressImpact = 8.0;
        BlockStressValues.IMPACTS.register(soulCondenserInstance, () -> stressImpact);

    }
}
