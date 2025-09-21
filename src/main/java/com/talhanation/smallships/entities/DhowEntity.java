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

public class DhowEntity extends AbstractBasicShip {

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
}
