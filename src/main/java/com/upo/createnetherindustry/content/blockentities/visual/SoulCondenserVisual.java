package com.upo.createnetherindustry.content.blockentities.visual;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.upo.createnetherindustry.content.blockentities.SoulCondenserBlockEntity;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

//弃用，全部移交BER处理
public class SoulCondenserVisual extends SingleAxisRotatingVisual<SoulCondenserBlockEntity> implements SimpleDynamicVisual {

    public SoulCondenserVisual(VisualizationContext context, SoulCondenserBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick, Direction.UP, Models.partial(AllPartialModels.SHAFTLESS_COGWHEEL));
        if (this.rotatingModel != null) {
            BlockPos visualPos = getVisualPosition();
            this.rotatingModel.setPosition(
                    visualPos.getX(),
                    visualPos.getY(),
                    visualPos.getZ()
            );
        }
    }
    @Override
    public void beginFrame(DynamicVisual.Context ctx) {
    }
}



