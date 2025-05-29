package com.upo.createnetherindustry.registry;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.FluidEntry;
import com.upo.createnetherindustry.CreateNetherIndustry;
import com.upo.createnetherindustry.content.fluid.ThickSoulSyrupType;
import com.upo.createnetherindustry.content.fluid.ThinSoulFluidType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;


public class CNIFluids {
    private static final CreateRegistrate REGISTRATE = CreateNetherIndustry.REGISTRATE;

    public static final FluidEntry<BaseFlowingFluid.Flowing> THIN_SOUL_FLUID = REGISTRATE
            .standardFluid("thin_soul_fluid", ThinSoulFluidType.create())
            .lang("稀薄魂灵液")
            .properties(p -> p
                    .density(500).viscosity(3000).temperature(150).canSwim(true).canDrown(true).lightLevel(7).supportsBoating(false)
                    .pathType(net.minecraft.world.level.pathfinder.PathType.STICKY_HONEY).canConvertToSource(false)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            )
            .fluidProperties(fp -> fp
                    .levelDecreasePerBlock(2).tickRate(5).slopeFindDistance(6).explosionResistance(10f)
            )
            .source(BaseFlowingFluid.Source::new)
            .block()
            .properties(bp -> bp.mapColor(MapColor.WATER).strength(100f).noOcclusion())
            .build()
            .bucket()
            .build()
            .register();

    public static final FluidEntry<BaseFlowingFluid.Flowing> THICK_SOUL_SYRUP = REGISTRATE
            .standardFluid("thick_soul_syrup", ThickSoulSyrupType.create())
            .lang("浓稠魂灵浆")
            .properties(p -> p
                    .density(5000).viscosity(3000).temperature(150).canSwim(false).canDrown(true).lightLevel(7).supportsBoating(false)
                    .pathType(net.minecraft.world.level.pathfinder.PathType.STICKY_HONEY).canConvertToSource(false)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            )
            .fluidProperties(fp -> fp
                    .levelDecreasePerBlock(2).tickRate(8).slopeFindDistance(2).explosionResistance(10f)
            )
            .source(BaseFlowingFluid.Source::new)
            .block()
            .properties(bp -> bp.mapColor(MapColor.WATER).strength(100f).noOcclusion())
            .build()
            .bucket()
            .build()
            .register();





    public static void register() {}
    public static void registerFluidInteractions() {}


}

