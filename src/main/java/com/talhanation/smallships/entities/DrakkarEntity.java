package com.talhanation.smallships.entities;

import com.talhanation.smallships.config.SmallShipsConfig;
import com.talhanation.smallships.init.ModEntityTypes;
import com.talhanation.smallships.init.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class DrakkarEntity extends AbstractBasicShip {

    private static final Vector3d[] PASSENGER_OFFSETS = new Vector3d[]{
            new Vector3d(-1.2D, 0.0D, 0.3D),
            new Vector3d(1.2D, 0.0D, 0.3D),
            new Vector3d(-0.8D, 0.0D, -0.5D),
            new Vector3d(0.8D, 0.0D, -0.5D),
            new Vector3d(0.0D, 0.0D, -1.2D)
    };

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
    protected Vector3d[] getPassengerOffsets() {
        return PASSENGER_OFFSETS;
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
        return 18;
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
        return 0F;
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
}
