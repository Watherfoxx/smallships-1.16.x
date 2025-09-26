package com.talhanation.smallships.entities;

import com.talhanation.smallships.InventoryEvents;
import com.talhanation.smallships.config.SmallShipsConfig;
import com.talhanation.smallships.init.ModEntityTypes;
import com.talhanation.smallships.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

public class RowBoatEntity extends AbstractCannonShip {

    private static final Vector3d[] PASSENGER_OFFSETS = new Vector3d[]{
        new Vector3d(-0.5D, 0.0D, 0.0D),
        new Vector3d(0.5D, 0.0D, -0.4D)
    };

    private static final Vector3d[] PASSENGER_LAYOUT_ONE = new Vector3d[]{
        PASSENGER_OFFSETS[0]
    };

    public RowBoatEntity(EntityType<? extends RowBoatEntity> type, World world) {
        super(type, world);
        this.setPaddleState(false, false);
    }

    public RowBoatEntity(World world, double x, double y, double z) {
        this(ModEntityTypes.ROWBOAT.get(), world);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    public int getBiomesModifierType() {
        return 1;
    }

    @Override
    public double getWidth() {
        return 1.6D;
    }

    @Override
    public double getHeight() {
        return 0.7D;
    }

    @Override
    public double getShipDefense() {
        return 5D;
    }

    @Override
    public int getInventorySize() {
        return SmallShipsConfig.RowBoatInventorySize.get();
    }

    @Override
    public int getConfiguredInventoryPages() {
        return SmallShipsConfig.RowBoatInventoryPages.get();
    }

    @Override
    public int getMaxCannons() {
        return SmallShipsConfig.RowBoatMaxCannons.get();
    }

    @Override
    public float getMaxSpeed() {
        return (float) (4.0F * SmallShipsConfig.RowBoatSpeedFactor.get());
    }

    @Override
    public float getMaxReverseSpeed() {
        return 0.05F;
    }

    @Override
    public float getAcceleration() {
        return (float) (0.03F * SmallShipsConfig.RowBoatSpeedFactor.get());
    }

    @Override
    public float getMaxRotationSpeed() {
        return (float) (6.0F * SmallShipsConfig.RowBoatTurnFactor.get());
    }

    @Override
    public float getRotationAcceleration() {
        return (float) (0.6F * SmallShipsConfig.RowBoatTurnFactor.get());
    }

    @Override
    public float getVelocityResistance() {
        return 0.009F;
    }

    @Override
    public float getCargoModifier() {
        return this.getCargo() * 0.01F;
    }

    @Override
    public float getCannonModifier() {
        return this.getTotalCannonCount() * 0.02F;
    }

    @Override
    public float getPassengerModifier() {
        return this.getPassengers().size() * 0.01F;
    }

    @Override
    public boolean doesEnterThirdPerson() {
        return true;
    }

    @Override
    public int getPassengerSize() {
        return PASSENGER_OFFSETS.length;
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
            if (itemInHand.getItem() == ModItems.CANNON_ITEM.get()) {
                this.onInteractionWithCannon(player, itemInHand);
                return ActionResultType.SUCCESS;
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
        Vector3d[] layout = getSeatLayout();
        if (layout.length == 0) {
            return;
        }
        int seatIndex = MathHelper.clamp(index, 0, layout.length - 1);
        Vector3d offset = layout[seatIndex];
        float ridingOffset = (float) ((this.removed ? 0.02D : this.getPassengersRidingOffset()) + passenger.getMyRidingOffset());
        Vector3d rotated = new Vector3d(offset.x, 0.0D, offset.z)
                .yRot(-this.yRot * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));
        passenger.setPos(this.getX() + rotated.x, this.getY() + offset.y + ridingOffset, this.getZ() + rotated.z);
        passenger.yRot += this.deltaRotation;
        passenger.setYHeadRot(passenger.getYHeadRot() + this.deltaRotation);
        applyYawToEntity(passenger);
    }

    private Vector3d[] getSeatLayout() {
        int seatCount = this.getPassengerSize();
        if (seatCount <= 1) {
            return PASSENGER_LAYOUT_ONE;
        }
        return PASSENGER_OFFSETS;
    }

    @Override
    public ResourceLocation getLootTable() {
        return null;
    }

    @Override
    public Item getItemBoat() {
        switch (this.getWoodType()) {
            case SPRUCE:
                return ModItems.SPRUCE_ROWBOAT_ITEM.get();
            case BIRCH:
                return ModItems.BIRCH_ROWBOAT_ITEM.get();
            case JUNGLE:
                return ModItems.JUNGLE_ROWBOAT_ITEM.get();
            case ACACIA:
                return ModItems.ACACIA_ROWBOAT_ITEM.get();
            case DARK_OAK:
                return ModItems.DARK_OAK_ROWBOAT_ITEM.get();
            case OAK:
            default:
                return ModItems.OAK_ROWBOAT_ITEM.get();
        }
    }

    @Override
    protected Item getBrokenHullItem() {
        return Items.AIR;
    }

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
    public boolean getHasBanner() {
        return false;
    }
}
