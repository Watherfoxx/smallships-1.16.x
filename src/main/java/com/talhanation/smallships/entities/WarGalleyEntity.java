package com.talhanation.smallships.entities;

import com.talhanation.smallships.InventoryEvents;
import com.talhanation.smallships.config.SmallShipsConfig;
import com.talhanation.smallships.init.ModEntityTypes;
import com.talhanation.smallships.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

public class WarGalleyEntity extends AbstractCannonShip {

    private static final Vector3d[] PASSENGER_OFFSETS = new Vector3d[]{
            new Vector3d(-1.4D, 0.0D, 0.5D),
            new Vector3d(1.4D, 0.0D, 0.5D),
            new Vector3d(-1.0D, 0.0D, -0.4D),
            new Vector3d(1.0D, 0.0D, -0.4D),
            new Vector3d(-0.6D, 0.0D, -1.3D),
            new Vector3d(0.6D, 0.0D, -1.3D)
    };

    public WarGalleyEntity(EntityType<? extends WarGalleyEntity> type, World world) {
        super(type, world);
        this.setSailState(4);
    }

    public WarGalleyEntity(World world, double x, double y, double z) {
        this(ModEntityTypes.WAR_GALLEY.get(), world);
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
        return 3.2D;
    }

    @Override
    public double getHeight() {
        return 1.25D;
    }

    @Override
    public double getShipDefense() {
        return 18D;
    }

    @Override
    public int getInventorySize() {
        return 36;
    }

    @Override
    public float getMaxSpeed() {
        return (float) (6.0F * SmallShipsConfig.WarGalleySpeedFactor.get());
    }

    @Override
    public float getMaxReverseSpeed() {
        return 0.12F;
    }

    @Override
    public float getAcceleration() {
        return (float) (0.022F * SmallShipsConfig.WarGalleySpeedFactor.get());
    }

    @Override
    public float getMaxRotationSpeed() {
        return (float) (3.5F * SmallShipsConfig.WarGalleyTurnFactor.get());
    }

    @Override
    public float getRotationAcceleration() {
        return (float) (0.28F * SmallShipsConfig.WarGalleyTurnFactor.get());
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
        return 6;
    }

    @Override
    public ResourceLocation getLootTable() {
        return null;
    }

    @Override
    public Item getItemBoat() {
        switch (this.getWoodType()) {
            case SPRUCE:
                return ModItems.SPRUCE_WAR_GALLEY_ITEM.get();
            case BIRCH:
                return ModItems.BIRCH_WAR_GALLEY_ITEM.get();
            case JUNGLE:
                return ModItems.JUNGLE_WAR_GALLEY_ITEM.get();
            case ACACIA:
                return ModItems.ACACIA_WAR_GALLEY_ITEM.get();
            case DARK_OAK:
                return ModItems.DARK_OAK_WAR_GALLEY_ITEM.get();
            case OAK:
            default:
                return ModItems.OAK_WAR_GALLEY_ITEM.get();
        }
    }

    @Override
    protected Item getBrokenHullItem() {
        return Items.AIR;
    }

    ////////////////////////////////////INTERACTIONS///////////////////////////////

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

            if (itemInHand.getItem() instanceof DyeItem) {
                this.onInteractionWithDye(player, ((DyeItem) itemInHand.getItem()).getDyeColor(), itemInHand);
                return ActionResultType.SUCCESS;
            }

            if (itemInHand.getItem() instanceof BannerItem) {
                this.onInteractionWithBanner(itemInHand, player);
                return ActionResultType.SUCCESS;
            }

            if (itemInHand.getItem() instanceof AxeItem) {
                if (hasPlanks(player.inventory) && hasIronNugget(player.inventory) && getShipDamage() > 16.0D) {
                    this.onInteractionWitAxe(player);
                    return ActionResultType.SUCCESS;
                } else {
                    return ActionResultType.FAIL;
                }
            } else if (itemInHand.getItem() instanceof ShearsItem) {
                if (this.getHasBanner()) {
                    this.onInteractionWithShears(player);
                    return ActionResultType.SUCCESS;
                }
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
        passenger.setYRot(passenger.yRot + this.deltaRotation);
        passenger.setYHeadRot(passenger.getYHeadRot() + this.deltaRotation);
        applyYawToEntity(passenger);
    }

    @Override
    public void WaterSplash() {
        Vector3d forward = this.getViewVector(0.0F);
        float cos = MathHelper.cos(this.yRot * ((float) Math.PI / 180F)) * 0.7F;
        float sin = MathHelper.sin(this.yRot * ((float) Math.PI / 180F)) * 0.7F;
        for (int i = 0; i < 2; i++) {
            this.level.addParticle(net.minecraft.particles.ParticleTypes.SPLASH,
                    this.getX() - forward.x * 1.8D + cos,
                    this.getY() + 0.25D,
                    this.getZ() - forward.z * 1.8D + sin,
                    0.0D, 0.0D, 0.0D);
            this.level.addParticle(net.minecraft.particles.ParticleTypes.SPLASH,
                    this.getX() - forward.x * 1.8D - cos,
                    this.getY() + 0.25D,
                    this.getZ() - forward.z * 1.8D - sin,
                    0.0D, 0.0D, 0.0D);
        }
    }
}
