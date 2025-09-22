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

public class DrakkarEntity extends AbstractCannonShip {

    private static final Vector3d[] PASSENGER_OFFSETS = new Vector3d[]{
            new Vector3d(-1.2D, 0.0D, 0.3D),
            new Vector3d(1.2D, 0.0D, 0.3D),
            new Vector3d(-0.8D, 0.0D, -0.5D),
            new Vector3d(0.8D, 0.0D, -0.5D),
            new Vector3d(0.0D, 0.0D, -1.2D)
    };
    private static final Vector3d[] PASSENGER_LAYOUT_FIVE = new Vector3d[]{
            PASSENGER_OFFSETS[0],
            PASSENGER_OFFSETS[1],
            PASSENGER_OFFSETS[2],
            PASSENGER_OFFSETS[3],
            PASSENGER_OFFSETS[4]
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
    private static final int MIN_PASSENGERS = 2;

    public DrakkarEntity(EntityType<? extends DrakkarEntity> type, World world) {
        super(type, world);
        this.setSailState(3);
    }

    public DrakkarEntity(World world, double x, double y, double z) {
        this(ModEntityTypes.DRAKKAR.get(), world);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    public int getBiomesModifierType() {
        return 0;
    }

    @Override
    public double getWidth() {
        return 2.8D;
    }

    @Override
    public double getHeight() {
        return 1.15D;
    }

    @Override
    public double getShipDefense() {
        return 14D;
    }

    @Override
    public int getInventorySize() {
        return SmallShipsConfig.DrakkarInventorySize.get();
    }

    @Override
    public int getConfiguredInventoryPages() {
        return SmallShipsConfig.DrakkarInventoryPages.get();
    }

    @Override
    public int getMaxCannons() {
        return SmallShipsConfig.DrakkarMaxCannons.get();
    }

    @Override
    public float getMaxSpeed() {
        return (float) (5.5F * SmallShipsConfig.DrakkarSpeedFactor.get());
    }

    @Override
    public float getMaxReverseSpeed() {
        return 0.1F;
    }

    @Override
    public float getAcceleration() {
        return (float) (0.022F * SmallShipsConfig.DrakkarSpeedFactor.get());
    }

    @Override
    public float getMaxRotationSpeed() {
        return (float) (4.5F * SmallShipsConfig.DrakkarTurnFactor.get());
    }

    @Override
    public float getRotationAcceleration() {
        return (float) (0.32F * SmallShipsConfig.DrakkarTurnFactor.get());
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
        int seatCount = PASSENGER_OFFSETS.length - (this.getTotalCannonCount() + 1) / 2;
        return MathHelper.clamp(seatCount, MIN_PASSENGERS, PASSENGER_OFFSETS.length);
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
        passenger.setPos(this.getX() + rotated.x, this.getY() + ridingOffset, this.getZ() + rotated.z);
        passenger.yRot += this.deltaRotation;
        passenger.setYHeadRot(passenger.getYHeadRot() + this.deltaRotation);
        applyYawToEntity(passenger);
    }

    private Vector3d[] getSeatLayout() {
        int seatCount = MathHelper.clamp(this.getPassengerSize(), 1, PASSENGER_OFFSETS.length);
        if (seatCount >= PASSENGER_LAYOUT_FIVE.length) {
            return PASSENGER_LAYOUT_FIVE;
        } else if (seatCount == 4) {
            return PASSENGER_LAYOUT_FOUR;
        } else if (seatCount == 3) {
            return PASSENGER_LAYOUT_THREE;
        } else if (seatCount == 2) {
            return PASSENGER_LAYOUT_TWO;
        }
        return PASSENGER_LAYOUT_ONE;
    }

    @Override
    public ResourceLocation getLootTable() {
        return null;
    }

    @Override
    public Item getItemBoat() {
        switch (this.getWoodType()) {
            case SPRUCE:
                return ModItems.SPRUCE_DRAKKAR_ITEM.get();
            case BIRCH:
                return ModItems.BIRCH_DRAKKAR_ITEM.get();
            case JUNGLE:
                return ModItems.JUNGLE_DRAKKAR_ITEM.get();
            case ACACIA:
                return ModItems.ACACIA_DRAKKAR_ITEM.get();
            case DARK_OAK:
                return ModItems.DARK_OAK_DRAKKAR_ITEM.get();
            case OAK:
            default:
                return ModItems.OAK_DRAKKAR_ITEM.get();
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
