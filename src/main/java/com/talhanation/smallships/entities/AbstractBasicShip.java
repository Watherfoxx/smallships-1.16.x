package com.talhanation.smallships.entities;

import com.talhanation.smallships.InventoryEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

public abstract class AbstractBasicShip extends AbstractShipDamage {

    protected AbstractBasicShip(EntityType<? extends AbstractBasicShip> type, World world) {
        super(type, world);
    }

    @Override
    public ActionResultType interact(PlayerEntity player, Hand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        if (player.isSecondaryUseActive()) {
            if (this.getSunken()) {
                if (!this.level.isClientSide) {
                    ItemStack salvagedBoat = this.createShipItemStack(false);
                    if (!salvagedBoat.isEmpty()) {
                        if (salvagedBoat.isDamageableItem()) {
                            salvagedBoat.setDamageValue(salvagedBoat.getMaxDamage());
                        }
                        ItemStack toGive = salvagedBoat.copy();
                        if (!player.addItem(toGive)) {
                            this.spawnAtLocation(salvagedBoat);
                        }
                    }
                    setDropBrokenItemOnDestroy(false);
                    this.destroyShip(DamageSource.GENERIC);
                }
                return ActionResultType.sidedSuccess(this.level.isClientSide);
            }

            if (this.getPassengers().isEmpty()) {
                if (!this.level.isClientSide) {
                    setDropBrokenItemOnDestroy(false);
                    this.spawnAtLocation(this.createShipItemStack(false));
                    this.destroyShip(DamageSource.GENERIC);
                }
                return ActionResultType.sidedSuccess(this.level.isClientSide);
            }

            if (this.isVehicle() && !(getControllingPassenger() instanceof PlayerEntity)) {
                this.ejectPassengers();
            } else if (!(getControllingPassenger() instanceof PlayerEntity)) {
                InventoryEvents.openShipGUI(player, this, 0);
            }

            return ActionResultType.sidedSuccess(this.level.isClientSide);
        }

        if (!this.getSunken()) {
            if (itemInHand.getItem() instanceof BannerItem) {
                if (!this.getHasBanner()) {
                    this.onInteractionWithBanner(itemInHand, player);
                    return ActionResultType.sidedSuccess(this.level.isClientSide);
                }
            }

            if (itemInHand.getItem() == Items.SHEARS && this.getHasBanner()) {
                this.onInteractionWithShears(player);
                itemInHand.hurtAndBreak(1, player, living -> living.broadcastBreakEvent(hand));
                return ActionResultType.sidedSuccess(this.level.isClientSide);
            }

            if (!this.level.isClientSide) {
                player.startRiding(this);
            }
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        }

        return ActionResultType.PASS;
    }

    @Override
    public void positionRider(Entity passenger) {
        if (!hasPassenger(passenger)) {
            return;
        }

        List<Entity> passengers = this.getPassengers();
        int index = passengers.indexOf(passenger);
        Vector3d[] offsets = getPassengerOffsets();
        Vector3d offset = offsets.length > 0 ? offsets[Math.min(index, offsets.length - 1)] : Vector3d.ZERO;

        float ridingOffset = (float) ((this.removed ? 0.02D : this.getPassengersRidingOffset()) + passenger.getMyRidingOffset());
        Vector3d rotated = new Vector3d(offset.x, 0.0D, offset.z)
                .yRot(-this.yRot * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));
        passenger.setPos(this.getX() + rotated.x, this.getY() + ridingOffset, this.getZ() + rotated.z);
        passenger.setYRot(passenger.yRot + this.deltaRotation);
        passenger.setYHeadRot(passenger.getYHeadRot() + this.deltaRotation);
        applyYawToEntity(passenger);
    }

    protected abstract Vector3d[] getPassengerOffsets();

    @Override
    public void WaterSplash() {
        Vector3d forward = this.getViewVector(0.0F);
        float cos = MathHelper.cos(this.yRot * ((float) Math.PI / 180F)) * 0.6F;
        float sin = MathHelper.sin(this.yRot * ((float) Math.PI / 180F)) * 0.6F;
        for (int i = 0; i < 2; i++) {
            this.level.addParticle(net.minecraft.particles.ParticleTypes.SPLASH,
                    this.getX() - forward.x * 1.5D + cos,
                    this.getY() + 0.2D,
                    this.getZ() - forward.z * 1.5D + sin,
                    0.0D, 0.0D, 0.0D);
            this.level.addParticle(net.minecraft.particles.ParticleTypes.SPLASH,
                    this.getX() - forward.x * 1.5D - cos,
                    this.getY() + 0.2D,
                    this.getZ() - forward.z * 1.5D - sin,
                    0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void onCannonKeyPressed() {
        // no-op for non-cannon ships
    }

    @Override
    public boolean getHasBanner() {
        return false;
    }
}