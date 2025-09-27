package com.talhanation.smallships.entities;

import com.talhanation.smallships.InventoryEvents;
import com.talhanation.smallships.config.SmallShipsConfig;
import com.talhanation.smallships.init.ModEntityTypes;
import com.talhanation.smallships.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

public class CogEntity extends AbstractCannonShip{

    private static final Vector3d[] LEFT_CANNON_OFFSETS = new Vector3d[]{
        new Vector3d(-1.4D, 0.01D, -0.5D), // front
        new Vector3d(1.4D, 0.01D, -0.7D) // back
    };

    private static final Vector3d[] RIGHT_CANNON_OFFSETS = new Vector3d[]{
        new Vector3d(-1.4D, 0.01D, 0.5D), // front
        new Vector3d(1.4D, 0.01D, 0.7D) // back
    };

    private static final Vector3d[] PASSENGER_OFFSETS = new Vector3d[]{
        new Vector3d(-1.75D, 0.0D, 0.0D),
        new Vector3d(1.25D, 0.0D, -0.90D),
        new Vector3d(1.25D, 0.0D, 0.90D),
        new Vector3d(1.25D, 0.0D, 0.0D),
        new Vector3d(0.45D, 0.0D, -0.90D)
    };

    private static final Vector3d[] PASSENGER_LAYOUT_FOUR = new Vector3d[]{
        PASSENGER_OFFSETS[0],
        PASSENGER_OFFSETS[1],
        PASSENGER_OFFSETS[2],
        PASSENGER_OFFSETS[3]
    };

    private static final Vector3d[] PASSENGER_LAYOUT_THREE = new Vector3d[]{
        PASSENGER_OFFSETS[0],
        PASSENGER_OFFSETS[1],
        PASSENGER_OFFSETS[2]
    };

    private static final Vector3d[] PASSENGER_LAYOUT_TWO = new Vector3d[]{
        PASSENGER_OFFSETS[0],
        PASSENGER_OFFSETS[1]
    };

    private static final Vector3d[] PASSENGER_LAYOUT_ONE = new Vector3d[]{
        PASSENGER_OFFSETS[0]
    };


    public CogEntity(EntityType<? extends CogEntity> type, World world) {
        super(type, world);
    }

    //Constructor for ShipItem
    public CogEntity(World world, double x, double y, double z) {
        this(ModEntityTypes.COG.get(), world);
        setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    ////////////////////////////////////GET////////////////////////////////////

    @Override
    public int getBiomesModifierType() {
        return 0;// 0 = cold; 1 = neutral; 2 = warm; 3 = extreme cold; 4 = extreme warm;
    }

    @Override
    public double getWidth() {
        return 3.4D;
    }

    @Override
    public double getHeight() {
        return 1.0D;
    }

    public double getShipDefense() { //in %
        return 12;
    }

    @Override
    public int getInventorySize() {
        return SmallShipsConfig.CogInventorySize.get();
    }

    @Override
    public int getConfiguredInventoryPages() {
        return SmallShipsConfig.CogInventoryPages.get();
    }

    @Override
    public Double getMaxSpeed() {
        return SmallShipsConfig.CogSpeedFactor.get();
    }

    @Override
    public Double getMaxReverseSpeed() {
        return getMaxSpeed() / 5;
    }

    @Override
    public Double getAcceleration() {
        return 0.015D; //sensible
    }

    @Override
    public Double getMaxRotationSpeed() {
        return SmallShipsConfig.CogTurnFactor.get();
    }

    @Override
    public Double getRotationAcceleration() {
        return 0.3D;
    }

    @Override
    public float getVelocityResistance() {
        return 0.009F;
    }

    public float getCargoModifier() {
        return this.getCargo() * 0.02F;
    }

    @Override
    public float getCannonModifier() {
        return this.getTotalCannonCount() * 0.02F;
    }

    @Override
    protected float getLeftCannonRotation() {
        return 0F;
    }

    @Override
    protected float getRightCannonRotation() {
        return 180F;
    }

    @Override
    public float getPassengerModifier() {
        return this.getPassengerSize() * 0.01F;
    }

    @Override
    public ResourceLocation getLootTable() {
        return null;
    }

    @Override
    public int getPassengerSize() {
        return PASSENGER_OFFSETS.length;
    }

    @Override
    public Item getItemBoat() {
        switch (this.getWoodType()) {
            case SPRUCE:
                return ModItems.SPRUCE_COG_ITEM.get();
            case BIRCH:
                return ModItems.BIRCH_COG_ITEM.get();
            case JUNGLE:
                return ModItems.JUNGLE_COG_ITEM.get();
            case ACACIA:
                return ModItems.ACACIA_COG_ITEM.get();
            case DARK_OAK:
                return ModItems.DARK_OAK_COG_ITEM.get();
            case OAK:
            default:
                return ModItems.OAK_COG_ITEM.get();
        }
    }

    @Override
    protected Item getBrokenHullItem() {
        return ModItems.BROKEN_COG_HULL.get();
    }

    public int getMaxCannons(){//max cannons
        return SmallShipsConfig.CogMaxCannons.get();
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
            } else {
                if (!(getControllingPassenger() instanceof PlayerEntity)) {
                    InventoryEvents.openShipGUI(player, this,0);
                }
                return ActionResultType.sidedSuccess(this.level.isClientSide);
            }

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
                } else return ActionResultType.FAIL;
            } else if (itemInHand.getItem() instanceof ShearsItem) {
                if (this.getHasBanner()) {
                    this.onInteractionWithShears(player);
                    return ActionResultType.SUCCESS;
                }
                return ActionResultType.PASS;
            }
            if (!player.isSecondaryUseActive()) {

                if (!this.level.isClientSide) {
                    return player.startRiding(this) ? ActionResultType.CONSUME : ActionResultType.PASS;

                } else {
                    return ActionResultType.SUCCESS;
                }
            }
        }

       return ActionResultType.FAIL;
    }


    @Override
    public boolean doesEnterThirdPerson() {
        return true;
    }


    ////////////////////////////////////OTHER FUNCTIONS////////////////////////////////////
    @Override
    public void WaterSplash(){
        Vector3d vector3d = this.getViewVector(0.0F);
        float f0 = MathHelper.cos(this.yRot * ((float)Math.PI / 180F)) * 0.8F;
        float f1 = MathHelper.sin(this.yRot * ((float)Math.PI / 180F)) * 0.8F;
        float f0_1 = MathHelper.cos(this.yRot * ((float)Math.PI / 180F)) * 1.6F;
        float f1_1 = MathHelper.sin(this.yRot * ((float)Math.PI / 180F)) * 1.6F;
        float f2 =  2.5F - this.random.nextFloat() * 0.7F;
        float f2_ =  -1.3F - this.random.nextFloat() * 0.7F;
        float x = 0;
        for (int i = 0; i < 2; ++i) {
            this.level.addParticle(ParticleTypes.DOLPHIN, this.getX() - vector3d.x * (double) f2 + (double) f0, this.getY() - vector3d.y + 0.5D, this.getZ() - vector3d.z * (double) f2 + (double) f1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.DOLPHIN, this.getX() - vector3d.x * (double) f2 - (double) f0, this.getY() - vector3d.y + 0.5D, this.getZ() - vector3d.z * (double) f2 - (double) f1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.DOLPHIN, this.getX() - vector3d.x * (double) f2 + (double) f0, this.getY() - vector3d.y + 0.5D, this.getZ() - vector3d.z * (double) f2 + (double) f1 * 1.1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.DOLPHIN, this.getX() - vector3d.x * (double) f2 - (double) f0, this.getY() - vector3d.y + 0.5D, this.getZ() - vector3d.z * (double) f2 - (double) f1 * 1.1, 0.0D, 0.0D, 0.0D);

            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2 + (double) f0, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) f2 + (double) f1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2 - (double) f0, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) f2 - (double) f1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2 + (double) f0, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) f2 + (double) f1 * 1.1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2 - (double) f0, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) f2 - (double) f1 * 1.1, 0.0D, 0.0D, 0.0D);

            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2_ + (double) f0_1, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) (f2_ - x) + (double) f1_1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2_ - (double) f0_1, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) (f2_ - x) - (double) f1_1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2_ + (double) f0_1, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) (f2_ - x) + (double) f1_1 * 1.1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2_ - (double) f0_1, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) (f2_ - x) - (double) f1_1 * 1.1, 0.0D, 0.0D, 0.0D);

            this.level.addParticle(ParticleTypes.BUBBLE, this.getX() - vector3d.x * (double) f2_ + (double) f0_1, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) (f2_ - x) + (double) f1_1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.BUBBLE, this.getX() - vector3d.x * (double) f2_ - (double) f0_1, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) (f2_ - x) - (double) f1_1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.BUBBLE, this.getX() - vector3d.x * (double) f2_ + (double) f0_1, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) (f2_ - x) + (double) f1_1 * 1.1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.BUBBLE, this.getX() - vector3d.x * (double) f2_ - (double) f0_1, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) (f2_ - x) - (double) f1_1 * 1.1, 0.0D, 0.0D, 0.0D);

        }
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
        int seatCount = MathHelper.clamp(this.getPassengerSize(), 1, PASSENGER_OFFSETS.length);
        switch (seatCount) {
            case 1:
                return PASSENGER_LAYOUT_ONE;
            case 2:
                return PASSENGER_LAYOUT_TWO;
            case 3:
                return PASSENGER_LAYOUT_THREE;
            case 4:
                return PASSENGER_LAYOUT_FOUR;
            default:
                return PASSENGER_OFFSETS;
        }
    }

    @Override
    protected Vector3d[] getLeftCannonOffsets() {
        return LEFT_CANNON_OFFSETS;
    }

    @Override
    protected Vector3d[] getRightCannonOffsets() {
        return RIGHT_CANNON_OFFSETS;
    }
}
