package com.talhanation.smallships.entities;

import com.talhanation.smallships.config.SmallShipsConfig;
import com.talhanation.smallships.init.ModEntityTypes;
import com.talhanation.smallships.init.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class GalleyEntity extends AbstractBasicShip {

    private static final Vector3d[] PASSENGER_OFFSETS = new Vector3d[]{
            new Vector3d(-1.3D, 0.0D, 0.4D),
            new Vector3d(1.3D, 0.0D, 0.4D),
            new Vector3d(-0.9D, 0.0D, -0.5D),
            new Vector3d(0.9D, 0.0D, -0.5D),
            new Vector3d(-0.4D, 0.0D, -1.3D),
            new Vector3d(0.4D, 0.0D, -1.3D)
    };

    public GalleyEntity(EntityType<? extends GalleyEntity> type, World world) {
        super(type, world);
        this.setSailState(4);
    }

    public GalleyEntity(World world, double x, double y, double z) {
        this(ModEntityTypes.GALLEY.get(), world);
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
        return 1;
    }

    @Override
    public double getWidth() {
        return 3.0D;
    }

    @Override
    public double getHeight() {
        return 1.2D;
    }

    @Override
    public double getShipDefense() {
        return 16D;
    }

    @Override
    public int getInventorySize() {
        return 36;
    }

    @Override
    public float getMaxSpeed() {
        return (float) (6.5F * SmallShipsConfig.GalleySpeedFactor.get());
    }

    @Override
    public float getMaxReverseSpeed() {
        return 0.12F;
    }

    @Override
    public float getAcceleration() {
        return (float) (0.024F * SmallShipsConfig.GalleySpeedFactor.get());
    }

    @Override
    public float getMaxRotationSpeed() {
        return (float) (4.0F * SmallShipsConfig.GalleyTurnFactor.get());
    }

    @Override
    public float getRotationAcceleration() {
        return (float) (0.3F * SmallShipsConfig.GalleyTurnFactor.get());
    }

    @Override
    public float getVelocityResistance() {
        return 0.008F;
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
    public Item getItemBoat() {
        switch (this.getWoodType()) {
            case SPRUCE:
                return ModItems.SPRUCE_GALLEY_ITEM.get();
            case BIRCH:
                return ModItems.BIRCH_GALLEY_ITEM.get();
            case JUNGLE:
                return ModItems.JUNGLE_GALLEY_ITEM.get();
            case ACACIA:
                return ModItems.ACACIA_GALLEY_ITEM.get();
            case DARK_OAK:
                return ModItems.DARK_OAK_GALLEY_ITEM.get();
            case OAK:
            default:
                return ModItems.OAK_GALLEY_ITEM.get();
        }
    }

    @Override
    protected Item getBrokenHullItem() {
        return Items.AIR;
    }
}
