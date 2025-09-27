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

public class BriggEntity extends AbstractCannonShip{

    private static final Vector3d[] LEFT_CANNON_OFFSETS = new Vector3d[]{
            new Vector3d(-1.8D, -0.5D, 1.4D),
            new Vector3d(-1.8D, -0.5D, 0.0D),
            new Vector3d(-1.8D, -0.5D, -1.4D)
    };
    private static final Vector3d[] RIGHT_CANNON_OFFSETS = new Vector3d[]{
            new Vector3d(1.8D, -0.5D, 1.4D),
            new Vector3d(1.8D, -0.5D, 0.0D),
            new Vector3d(1.8D, -0.5D, -1.4D)
    };
    private static final Vector3d DRIVER_PORT = new Vector3d(-2.0D, 0.0D, 0.75D);
    private static final Vector3d DRIVER_STARBOARD = new Vector3d(-2.0D, 0.0D, -0.75D);
    private static final Vector3d MID_STARBOARD = new Vector3d(-1.0D, 0.0D, -0.75D);
    private static final Vector3d MID_PORT = new Vector3d(-1.0D, 0.0D, 0.75D);
    private static final Vector3d CENTER_STARBOARD = new Vector3d(0.0D, 0.0D, -0.75D);
    private static final Vector3d CENTER_PORT = new Vector3d(0.0D, 0.0D, 0.75D);
    private static final Vector3d AFT_STARBOARD = new Vector3d(1.0D, 0.0D, -0.75D);
    private static final Vector3d AFT_PORT = new Vector3d(1.0D, 0.0D, 0.75D);
    private static final Vector3d BOW_PORT = new Vector3d(2.25D, 0.0D, 0.5D);
    private static final Vector3d BOW_STARBOARD = new Vector3d(2.25D, 0.0D, -0.5D);
    private static final Vector3d BOW_CENTER = new Vector3d(2.25D, 0.0D, 0.0D);
    private static final Vector3d[] PASSENGER_OFFSETS = new Vector3d[]{
            DRIVER_PORT,
            DRIVER_STARBOARD,
            MID_STARBOARD,
            MID_PORT,
            CENTER_STARBOARD,
            CENTER_PORT,
            AFT_STARBOARD,
            AFT_PORT,
            BOW_PORT,
            BOW_STARBOARD,
            BOW_CENTER
    };
    private static final Vector3d[] PASSENGER_LAYOUT_TEN = new Vector3d[]{
            DRIVER_PORT,
            DRIVER_STARBOARD,
            MID_STARBOARD,
            MID_PORT,
            CENTER_STARBOARD,
            CENTER_PORT,
            AFT_STARBOARD,
            AFT_PORT,
            BOW_PORT,
            BOW_STARBOARD
    };
    private static final Vector3d[] PASSENGER_LAYOUT_NINE = new Vector3d[]{
            DRIVER_PORT,
            DRIVER_STARBOARD,
            MID_STARBOARD,
            MID_PORT,
            CENTER_STARBOARD,
            CENTER_PORT,
            AFT_STARBOARD,
            AFT_PORT,
            BOW_CENTER
    };
    private static final Vector3d[] PASSENGER_LAYOUT_EIGHT = new Vector3d[]{
            DRIVER_PORT,
            DRIVER_STARBOARD,
            MID_STARBOARD,
            MID_PORT,
            CENTER_STARBOARD,
            CENTER_PORT,
            AFT_STARBOARD,
            AFT_PORT
    };
    private static final Vector3d[] PASSENGER_LAYOUT_SEVEN = new Vector3d[]{
            DRIVER_PORT,
            DRIVER_STARBOARD,
            MID_STARBOARD,
            MID_PORT,
            CENTER_STARBOARD,
            CENTER_PORT,
            AFT_STARBOARD
    };
    private static final Vector3d[] PASSENGER_LAYOUT_SIX = new Vector3d[]{
            DRIVER_PORT,
            DRIVER_STARBOARD,
            MID_STARBOARD,
            MID_PORT,
            CENTER_STARBOARD,
            CENTER_PORT
    };
    private static final Vector3d[] PASSENGER_LAYOUT_FIVE = new Vector3d[]{
            DRIVER_PORT,
            DRIVER_STARBOARD,
            MID_STARBOARD,
            MID_PORT,
            CENTER_STARBOARD
    };
    private static final Vector3d[] PASSENGER_LAYOUT_FOUR = new Vector3d[]{
            DRIVER_PORT,
            DRIVER_STARBOARD,
            MID_STARBOARD,
            MID_PORT
    };
    private static final Vector3d[] PASSENGER_LAYOUT_THREE = new Vector3d[]{
            DRIVER_PORT,
            DRIVER_STARBOARD,
            MID_STARBOARD
    };
    private static final Vector3d[] PASSENGER_LAYOUT_TWO = new Vector3d[]{
            DRIVER_PORT,
            DRIVER_STARBOARD
    };
    private static final Vector3d[] PASSENGER_LAYOUT_ONE = new Vector3d[]{
            DRIVER_PORT
    };

    public BriggEntity(EntityType<? extends BriggEntity> type, World world) {
        super(type, world);
    }

    //Constructor for ShipItem
    public BriggEntity(World world, double x, double y, double z) {
        this(ModEntityTypes.BRIGG.get(), world);
        setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    ////////////////////////////////////GET////////////////////////////////////

    @Override
    public int getBiomesModifierType() {
        return 0;// 0 = cold; 1 = neutral; 2 = warm;
    }

    @Override
    public double getWidth() {
        return 4.6D;
    }

    @Override
    public double getHeight() {
        return 1.9D;
    }

    @Override
    public double getShipDefense() { //in %
        return 18;
    }

    @Override
    public int getInventorySize() {
        return SmallShipsConfig.BriggInventorySize.get();
    }

    @Override
    public int getConfiguredInventoryPages() {
        return SmallShipsConfig.BriggInventoryPages.get();
    }

    @Override
    public float getMaxSpeed() {
        return 7F;
    }

    @Override
    public float getMaxReverseSpeed() {
        return 0.1F;
    }

    @Override
    public float getAcceleration() {
        return 0.0135F; //sensible;
    }

    @Override
    public float getMaxRotationSpeed() {
        return 4F;
    }

    @Override
    public float getRotationAcceleration() {
        return 0.3F;
    }//not to change

    @Override
    public float getVelocityResistance() {
        return 0.009F;
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
        return this.getPassengerSize() * 0.01F;
    }

    @Override
    public ResourceLocation getLootTable() {
        return null;
    }

    @Override
    public int getPassengerSize() {
        switch (getTotalCannonCount()) {
            default:
            case 0:
                return 10;
            case 1:
            case 2:
                return 9;
            case 3:
            case 4:
                return 8;
            case 5:
            case 6:
                return 7;
        }
    }

    @Override
    public int getMaxCannons() {
        return SmallShipsConfig.BriggMaxCannons.get();
    }

    @Override
    public Item getItemBoat() {
        switch (this.getWoodType()) {
            case SPRUCE:
                return ModItems.SPRUCE_BRIGG_ITEM.get();
            case BIRCH:
                return ModItems.BIRCH_BRIGG_ITEM.get();
            case JUNGLE:
                return ModItems.JUNGLE_BRIGG_ITEM.get();
            case ACACIA:
                return ModItems.ACACIA_BRIGG_ITEM.get();
            case DARK_OAK:
                return ModItems.DARK_OAK_BRIGG_ITEM.get();
            case OAK:
            default:
                return ModItems.OAK_BRIGG_ITEM.get();
        }
    }

    @Override
    protected Item getBrokenHullItem() {
        return ModItems.BROKEN_BRIGG_HULL.get();
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
        float f0 = MathHelper.cos(this.yRot * ((float)Math.PI / 180F)) * 1.2F;
        float f1 = MathHelper.sin(this.yRot * ((float)Math.PI / 180F)) * 1.2F;
        float f2 =  4F - this.random.nextFloat() * 0.7F; // höhe
        float f2_ =  -2.3F - this.random.nextFloat() * 0.7F;
        float x = 0; //verschiebung nach rechts/links
        float y = 4;
        for (int i = 0; i < 2; ++i) {                                                                                                                             //höhe
            this.level.addParticle(ParticleTypes.DOLPHIN, this.getX() - vector3d.x * (double) f2 + (double) f0, this.getY() - vector3d.y + 0.5D, this.getZ() - vector3d.z * (double) f2 + (double) f1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.DOLPHIN, this.getX() - vector3d.x * (double) f2 - (double) f0, this.getY() - vector3d.y + 0.5D, this.getZ() - vector3d.z * (double) f2 - (double) f1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.DOLPHIN, this.getX() - vector3d.x * (double) f2 + (double) f0, this.getY() - vector3d.y + 0.5D, this.getZ() - vector3d.z * (double) f2 + (double) f1 * 5.1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.DOLPHIN, this.getX() - vector3d.x * (double) f2 - (double) f0, this.getY() - vector3d.y + 0.5D, this.getZ() - vector3d.z * (double) f2 - (double) f1 * 5.1, 0.0D, 0.0D, 0.0D);

            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2 + (double) f0, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) f2 + (double) f1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2 - (double) f0, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) f2 - (double) f1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2 + (double) f0, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) f2 + (double) f1 * 1.1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2 - (double) f0, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) f2 - (double) f1 * 1.1, 0.0D, 0.0D, 0.0D);

            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2_ + (double) f0, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) (f2_ - x) + (double) f1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2_ - (double) f0, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) (f2_ - x) - (double) f1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2_ + (double) f0, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) (f2_ - x) + (double) f1 * 1.1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.SPLASH, this.getX() - vector3d.x * (double) f2_ - (double) f0, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) (f2_ - x) - (double) f1 * 1.1, 0.0D, 0.0D, 0.0D);

            this.level.addParticle(ParticleTypes.BUBBLE, this.getX() - vector3d.x * (double) f2_ + (double) f0, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) (f2_ - x) + (double) f1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.BUBBLE, this.getX() - vector3d.x * (double) f2_ - (double) f0, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) (f2_ - x) - (double) f1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.BUBBLE, this.getX() - vector3d.x * (double) f2_ + (double) f0, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) (f2_ - x) + (double) f1 * 1.1, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.BUBBLE, this.getX() - vector3d.x * (double) f2_ - (double) f0, this.getY() - vector3d.y + 0.8D, this.getZ() - vector3d.z * (double) (f2_ - x) - (double) f1 * 1.1, 0.0D, 0.0D, 0.0D);
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
        if (seatCount >= PASSENGER_LAYOUT_TEN.length) {
            return PASSENGER_LAYOUT_TEN;
        } else if (seatCount == 9) {
            return PASSENGER_LAYOUT_NINE;
        } else if (seatCount == 8) {
            return PASSENGER_LAYOUT_EIGHT;
        } else if (seatCount == 7) {
            return PASSENGER_LAYOUT_SEVEN;
        } else if (seatCount == 6) {
            return PASSENGER_LAYOUT_SIX;
        } else if (seatCount == 5) {
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
    protected Vector3d[] getLeftCannonOffsets() {
        return LEFT_CANNON_OFFSETS;
    }

    @Override
    protected Vector3d[] getRightCannonOffsets() {
        return RIGHT_CANNON_OFFSETS;
    }
}
