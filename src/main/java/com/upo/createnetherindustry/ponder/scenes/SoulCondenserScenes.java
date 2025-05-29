package com.upo.createnetherindustry.ponder.scenes;

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

public class SoulCondenserScenes {
    public static void processing(SceneBuilder builder, SceneBuildingUtil util){
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("condenser", "液体转化");
        scene.configureBasePlate(0, 0, 4);
        scene.showBasePlate();
        scene.idle(20);

        BlockPos condenser = util.grid().at(2, 2, 1);
        scene.world().showSection(util.select().fromTo(2, 2, 1, 2, 2, 1), Direction.DOWN);
        scene.idle(8);
        scene.overlay().showText(60)
                .text("凝集器是一种可以转化液体的机械元件")
                .pointAt(Vec3.atCenterOf(condenser))
                .placeNearTarget();
        scene.idle(60);

        scene.world().showSection(util.select().fromTo(2, 1, 2, 2, 2, 2), Direction.DOWN);
        scene.idle(8);
        scene.world().setKineticSpeed(util.select().everywhere(), -64);
        scene.overlay().showText(55)
                .text("从侧面接入应力……")
                .pointAt(Vec3.atCenterOf(condenser))
                .placeNearTarget();
        scene.idle(55);

        scene.world().showSection(util.select().fromTo(2, 1, 0, 2, 1, 1), Direction.UP);
        scene.world().showSection(util.select().fromTo(2, 3, 1, 1, 3, 2), Direction.DOWN);
        scene.world().showSection(util.select().fromTo(0, 1, 2, 0, 3, 2), Direction.DOWN);
        scene.idle(10);
        scene.overlay().showText(80)
                .text("原料从下方通入，成品从上方产出")
                .pointAt(Vec3.atCenterOf(condenser))
                .placeNearTarget();
        scene.idle(90);

        scene.overlay().showText(800)
                .text("你也可以通过数据包自定义流体转化的配方")
                .independent();

        scene.markAsFinished();
    }
}
