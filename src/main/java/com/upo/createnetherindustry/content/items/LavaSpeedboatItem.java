package com.upo.createnetherindustry.content.items;

import com.upo.createnetherindustry.entity.LavaSpeedboatEntity; // 引入你的实体
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class LavaSpeedboatItem extends Item {

    private static final Predicate<Entity> ENTITY_PREDICATE =
            ((Predicate<Entity>) (entity -> !entity.isSpectator())).and(Entity::isPickable);

    public LavaSpeedboatItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        // 使用 rayTrace 来确定玩家正在看什么地方
        HitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);

        // 如果玩家没有看向任何东西，则失败
        if (hitResult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemStack);
        }

        Vec3 lookAngle = player.getViewVector(1.0F);
        // 检查玩家附近是否有其他实体，防止在实体堆里放船
        List<Entity> list = level.getEntities(
                player,
                player.getBoundingBox().expandTowards(lookAngle.scale(5.0)).inflate(1.0),
                ENTITY_PREDICATE
        );
        if (!list.isEmpty()) {
            Vec3 eyePosition = player.getEyePosition();
            for (Entity entity : list) {
                AABB aabb = entity.getBoundingBox().inflate(entity.getPickRadius());
                if (aabb.contains(eyePosition)) {
                    return InteractionResultHolder.pass(itemStack);
                }
            }
        }

        // 如果玩家看向了一个方块
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            // 创建我们的岩浆船实体
            LavaSpeedboatEntity speedboat = new LavaSpeedboatEntity(level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
            speedboat.setYRot(player.getYRot()); // 让船的朝向和玩家一致

            if (!level.noCollision(speedboat, speedboat.getBoundingBox())) {
                return InteractionResultHolder.fail(itemStack); // 如果生成位置有碰撞，则失败
            }

            if (!level.isClientSide) {
                level.addFreshEntity(speedboat); // 在服务器端添加实体
                level.gameEvent(player, GameEvent.ENTITY_PLACE, hitResult.getLocation());
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1); // 如果不是创造模式，消耗一个物品
                }
            }

            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
        }

        return InteractionResultHolder.pass(itemStack);
    }
}
