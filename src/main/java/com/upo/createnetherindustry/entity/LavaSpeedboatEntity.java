package com.upo.createnetherindustry.entity;

import com.upo.createnetherindustry.registry.CNIEntities;
import com.upo.createnetherindustry.registry.CNIItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Random;


public class LavaSpeedboatEntity extends Boat {

    private final Random random = new Random();

    public LavaSpeedboatEntity(EntityType<? extends Boat> entityType, Level level) {
        super(entityType, level);
        this.blocksBuilding = true;
    }

    public LavaSpeedboatEntity(Level level, double x, double y, double z) {
        this(CNIEntities.LAVA_SPEEDBOAT.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            if (this.getDeltaMovement().lengthSqr() > 0.01) {
                Vec3 forward = this.getLookAngle().scale(-0.7);
                double x = this.getX() + forward.x;
                double y = this.getY() + 0.4D;
                double z = this.getZ() + forward.z;

                for (int i = 0; i < 2; i++) {
                    double offsetX = (random.nextDouble() - 0.5D) * 0.3D;
                    double offsetY = (random.nextDouble() - 0.5D) * 0.3D;
                    double offsetZ = (random.nextDouble() - 0.5D) * 0.3D;

                    this.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                            x + offsetX, y + offsetY, z + offsetZ,
                            0.0, 0.05, 0.0);
                }
            }
        }
    }

    public static LavaSpeedboatEntity create(EntityType<? extends Boat> type, Level level) {
        return new LavaSpeedboatEntity(type, level);
    }


    @Override
    public boolean canBoatInFluid(@NotNull FluidState fluidState) {
        return fluidState.is(FluidTags.LAVA);
    }

    @Override
    public @NotNull Item getDropItem() {
        return CNIItems.LAVA_SPEEDBOAT.get();
    }


    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    protected boolean canAddPassenger(@NotNull Entity passenger) {
        return this.getPassengers().isEmpty();
    }

    @Override
    public int getMaxPassengers() {
        return 1;
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float partialTicks) {

        double x = 0.0D;
        double y = dimensions.height() * 1.5D;
        double z = 0.0D;

        return new Vec3(x, y, z);
    }
}
