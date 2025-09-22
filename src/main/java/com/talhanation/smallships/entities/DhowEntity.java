package com.talhanation.smallships.entities;

import com.talhanation.smallships.InventoryEvents;
import com.talhanation.smallships.config.SmallShipsConfig;
import com.talhanation.smallships.init.ModEntityTypes;
import com.talhanation.smallships.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BannerItem;
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

public class DhowEntity extends AbstractCannonShip {

    private static final Vector3d[] PASSENGER_OFFSETS = new Vector3d[]{
            new Vector3d(-1.0D, 0.0D, 0.2D),
            new Vector3d(1.0D, 0.0D, 0.2D),
            new Vector3d(-0.6D, 0.0D, -0.6D),
            new Vector3d(0.6D, 0.0D, -0.6D)
    };

    public DhowEntity(EntityType<? extends DhowEntity> type, World world) {
        super(type, world);
        this.setSailState(3);
    }

    public DhowEntity(World world, double x, double y, double z) {
        this(ModEntityTypes.DHOW.get(), world);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    protected Vector3d[] getPassengerOffsets() {
        return PASSENGER_OFFSETS;
    }

    @Override
    public int getBiomesModifierType() {
        return 2;
    }

    @Override
    public double getWidth() {
        return 2.6D;
    }

    @Override
    public double getHeight() {
        return 1.1D;
    }

    @Override
    public double getShipDefense() {
        return 12D;
    }

    @Override
    public int getInventorySize() {
        return 27;
    }

    @Override
    public float getMaxSpeed() {
        return (float) (6.0F * SmallShipsConfig.DhowSpeedFactor.get());
    }

    @Override
    public float getMaxReverseSpeed() {
        return 0.1F;
    }

    @Override
    public float getAcceleration() {
        return (float) (0.025F * SmallShipsConfig.DhowSpeedFactor.get());
    }

    @Override
    public float getMaxRotationSpeed() {
        return (float) (4.0F * SmallShipsConfig.DhowTurnFactor.get());
    }

    @Override
    public float getRotationAcceleration() {
        return (float) (0.35F * SmallShipsConfig.DhowTurnFactor.get());
    }

    @Override
    public float getVelocityResistance() {
        return 0.0085F;
    }

    @Override
    public float getCargoModifier() {
        return this.getCargo() * 0.02F;
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
    public int getMaxCannons() {
        return 4;
    }

    @Override
    public ResourceLocation getLootTable() {
        return null;
    }

    @Override
    public Item getItemBoat() {
        switch (this.getWoodType()) {
            case SPRUCE:
                return ModItems.SPRUCE_DHOW_ITEM.get();
            case BIRCH:
                return ModItems.BIRCH_DHOW_ITEM.get();
            case JUNGLE:
                return ModItems.JUNGLE_DHOW_ITEM.get();
            case ACACIA:
                return ModItems.ACACIA_DHOW_ITEM.get();
            case DARK_OAK:
                return ModItems.DARK_OAK_DHOW_ITEM.get();
            case OAK:
            default:
                return ModItems.OAK_DHOW_ITEM.get();
        }
    }

    @Override
    protected Item getBrokenHullItem() {
        return Items.AIR;
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
        Vector3d offset = PASSENGER_OFFSETS[Math.min(index, PASSENGER_OFFSETS.length - 1)];
        float ridingOffset = (float) ((this.removed ? 0.02D : this.getPassengersRidingOffset()) + passenger.getMyRidingOffset());
        Vector3d rotated = new Vector3d(offset.x, 0.0D, offset.z)
                .yRot(-this.yRot * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));
        passenger.setPos(this.getX() + rotated.x, this.getY() + ridingOffset, this.getZ() + rotated.z);
        passenger.yRot += this.deltaRotation;
        passenger.setYHeadRot(passenger.getYHeadRot() + this.deltaRotation);
        applyYawToEntity(passenger);
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
