package com.upo.createnetherindustry.registry;

import com.tterrag.registrate.util.entry.EntityEntry;
import com.upo.createnetherindustry.CreateNetherIndustry;
import com.upo.createnetherindustry.entity.LavaSpeedboatEntity;
import net.minecraft.world.entity.MobCategory;

public class CNIEntities {

    public static final EntityEntry<LavaSpeedboatEntity> LAVA_SPEEDBOAT =
            CreateNetherIndustry.REGISTRATE
                    .entity("lava_speedboat", LavaSpeedboatEntity::create, MobCategory.MISC)
                    .properties(properties -> properties
                            .sized(1.375F, 0.5625F)
                            .fireImmune()
                            .clientTrackingRange(10)
                    )
                    .register();


    public static void register() {}
}
