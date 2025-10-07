package com.talhanation.smallships.entities;

import com.google.common.collect.ImmutableSet;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.talhanation.smallships.Main;
import com.talhanation.smallships.client.model.ModelSail;
import com.talhanation.smallships.client.render.RenderSailColor;
import com.talhanation.smallships.config.SmallShipsConfig;
import com.talhanation.smallships.init.SoundInit;
import com.talhanation.smallships.network.MessageControlShip;
import com.talhanation.smallships.network.MessageSailState;
import de.maxhenkel.corelib.math.MathUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Optional;

public abstract class AbstractSailShip extends AbstractWaterVehicle {

    private float wheelRotation;
    private final float[] paddlePositions = new float[2];
    private float waveAngle;
    private float prevWaveAngle;
    private boolean collidedLastTick;

    private static final DataParameter<Float> SPEED = EntityDataManager.defineId(AbstractSailShip.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> ROT_SPEED = EntityDataManager.defineId(AbstractSailShip.class, DataSerializers.FLOAT);
    private static final DataParameter<Boolean> FORWARD = EntityDataManager.defineId(AbstractSailShip.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> BACKWARD = EntityDataManager.defineId(AbstractSailShip.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> LEFT = EntityDataManager.defineId(AbstractSailShip.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> RIGHT = EntityDataManager.defineId(AbstractSailShip.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> SAIL_STATE = EntityDataManager.defineId(AbstractSailShip.class, DataSerializers.INT);
    private static final DataParameter<String>  SAIL_COLOR = EntityDataManager.defineId(AbstractSailShip.class, DataSerializers.STRING);

    private static final DataParameter<Integer> TYPE = EntityDataManager.defineId(AbstractSailShip.class, DataSerializers.INT);
    private static final DataParameter<Boolean> LEFT_PADDLE = EntityDataManager.defineId(AbstractSailShip.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> RIGHT_PADDLE = EntityDataManager.defineId(AbstractSailShip.class, DataSerializers.BOOLEAN);

    public static final String BROKEN_TAG = "SmallShipsBroken";

    public AbstractSailShip(EntityType<? extends AbstractWaterVehicle> type, World world) {
        super(type, world);
        this.maxUpStep = 0.2F;
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(SPEED, 0F);
        entityData.define(ROT_SPEED, 0F);
        entityData.define(FORWARD, false);
        entityData.define(BACKWARD, false);
        entityData.define(LEFT, false);
        entityData.define(RIGHT, false);
        entityData.define(SAIL_STATE, 0);
        entityData.define(SAIL_COLOR, "white");
        entityData.define(TYPE, AbstractSailShip.Type.OAK.ordinal());
        entityData.define(LEFT_PADDLE, false);
        entityData.define(RIGHT_PADDLE, false);
    }

    public abstract Double getMaxSpeed();
    public abstract Double getMaxReverseSpeed();
    public abstract Double getAcceleration();
    public abstract Double getMaxRotationSpeed();
    public abstract Double getRotationAcceleration();
    public abstract float getCargoModifier();
    public abstract float getCannonModifier();
    public abstract float getPassengerModifier();
    public abstract boolean getHasBanner();
    public abstract void  WaterSplash();
    public abstract void onInteractionWithShears(PlayerEntity player);
    public abstract void onCannonKeyPressed();
    public abstract boolean onInteractionWithBanner(ItemStack itemStack,PlayerEntity player);
    public abstract void damageShip(double amount);
    public abstract int getBiomesModifierType(); // 0 = cold; 1 = neutral; 2 = warm;



    ////////////////////////////////////TICK////////////////////////////////////

    @Override
    public void tick() {
        super.tick();

        //if(getDriver()!=null)this.getDriver().sendMessage(new StringTextComponent("" + this.getSpeed()), getDriver().getUUID());


        if ((getSpeed() > 0.085F || getSpeed() < -0.085F)) {
            this.knockBack(this.level.getEntities(this, this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D).move(0.0D, -2.0D, 0.0D), EntityPredicates.NO_CREATIVE_OR_SPECTATOR));
            this.knockBack(this.level.getEntities(this, this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D).move(0.0D, -2.0D, 0.0D), EntityPredicates.NO_CREATIVE_OR_SPECTATOR));

            if (this.getStatus() == Status.IN_WATER) {

                WaterSplash();

                if (SmallShipsConfig.PlaySwimmSound.get()) {
                    this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_SWIM, this.getSoundSource(), 0.05F, 0.8F + 0.4F * this.random.nextFloat());
                }
            }

        }

        if ((this.getControllingPassenger() == null ||!(this.getControllingPassenger() instanceof PlayerEntity) )) {
            setSailState(0);
        }

        if (this.getStatus() == Status.IN_WATER) updateWaveAngle();
        updateGravity();
        controlShip();
        updatePaddles();
        checkPush();
        move(MoverType.SELF, getDeltaMovement());
        //updateWheelRotation();
        updateWaterMobs();
        breakLily();

         /*
        if(this.getDriver() != null) {
            BlockPos pos = new BlockPos(getX(), getY() - 0.1D, getZ());
            double x = this.level.getBiome(pos).getTemperature(pos);
            double y = this.level.getBiome(pos).getWaterColor();
            //getDriver().sendMessage(new TextComponent("Temp: " + x + " Cannon: " + this.getCannonModifier() + " Passenger: " + this.getPassengerModifier() + " Cargo: " + this.getCargoModifier()), getDriver().getUUID());
            getDriver().sendMessage(new TextComponent("kmh: " + getKilometerPerHour()), getDriver().getUUID());
            //getDriver().sendMessage(new TextComponent("OnPos: " + getOnPos().getY()), getDriver().getUUID());
            getDriver().sendMessage(new TextComponent("ShipType: " + getBiomesModifierType()), getDriver().getUUID());
            getDriver().sendMessage(new TextComponent("ShipModifier: " + getBiomesModifier()), getDriver().getUUID());
        }
        */
    }
    ////////////////////////////////////SAVE DATA////////////////////////////////////

    @Override
    public void addAdditionalSaveData(CompoundNBT nbt) {

        nbt.putString("SailColor", getSailColor());
        nbt.putString("Type", getWoodType().getName());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbt) {;
        this.setSailColor(nbt.getString("SailColor"));
        if (nbt.contains("Type", 8)) {
            this.setWoodType(AbstractSailShip.Type.getTypeFromString(nbt.getString("Type")));
        }
        //this.setOwnerUUID(nbt.getUUID("OwnerUUID"));
    }


    ////////////////////////////////////GET////////////////////////////////////

    public float getVelocityResistance() {
        return 0.008F;
    }

    public String getSailColor() {
        return this.entityData.get(SAIL_COLOR);
    }

    public boolean getSteerState(int side) {
        return this.entityData.<Boolean>get(side == 0 ? LEFT : RIGHT) && this.getControllingPassenger() != null;
    }

    public Integer getSailState() {
        return entityData.get(SAIL_STATE);
    }

    public boolean getPaddleState(int side) {
        return this.entityData.<Boolean>get(side == 0 ? LEFT_PADDLE : RIGHT_PADDLE) && this.getControllingPassenger() != null;
    }

    public float getKilometerPerHour() {
        return (getSpeed() * 20 * 60 * 60) / 1000;
    }

    public static final ImmutableSet<RegistryKey<Biome>> COLD_BIOMES = ImmutableSet.of(
            Biomes.COLD_OCEAN,
            Biomes.DEEP_COLD_OCEAN,
            Biomes.FROZEN_OCEAN,
            Biomes.DEEP_FROZEN_OCEAN,
            Biomes.FROZEN_RIVER
    );

    public static final ImmutableSet<RegistryKey<Biome>> WARM_BIOMES = ImmutableSet.of(
            Biomes.WARM_OCEAN,
            Biomes.DEEP_WARM_OCEAN,
            Biomes.LUKEWARM_OCEAN,
            Biomes.DEEP_LUKEWARM_OCEAN
    );

    public static final ImmutableSet<RegistryKey<Biome>> NEUTRAL_BIOMES = ImmutableSet.of(
            Biomes.OCEAN,
            Biomes.DEEP_OCEAN,
            Biomes.RIVER
    );

    public float getBiomesModifier() {
        int biomeType = this.getBiomesModifierType(); // 0 = cold; 1 = neutral; 2 = warm;
        BlockPos pos = new BlockPos(getX(), getY() - 0.1D, getZ());
        Optional<RegistryKey<Biome>> biome = this.level.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getResourceKey(level.getBiome(pos));;


        if(biome.isPresent()) {
            boolean coldBiomes = COLD_BIOMES.contains(biome.get());;
            boolean neutralBiomes = NEUTRAL_BIOMES.contains(biome.get());
            boolean warmBiomes = WARM_BIOMES.contains(biome.get());

            boolean coldType = biomeType == 0;
            boolean neutralType = biomeType == 1;
            boolean warmType = biomeType == 2;

            if (coldBiomes && coldType || warmBiomes && warmType || neutralBiomes && neutralType) {
                return -0.1F;
            } else if (
                    (coldBiomes && warmType || warmBiomes && coldType) ||
                            ((coldBiomes || warmBiomes) && neutralType)
            ) {
                return 0.1F;
            } else if (neutralBiomes && warmType || neutralBiomes && coldType) {
                return 0.05F;
            } else
                return 0;
        }
        return 0;
    }

    public float getWheelRotationAmount() {
        return 120F * getSpeed();
    }

    public float getSpeed() {
        return this.entityData.get(SPEED);
    }

    public float getRotSpeed() {
        return this.entityData.get(ROT_SPEED);
    }

    public AbstractSailShip.Type getWoodType() {
        return AbstractSailShip.Type.byId(this.entityData.get(TYPE));
    }


    public float getWaveFactor() {

        return level.isRaining() ? 3F : 1.25F;
    }

    public float getWaveSpeed() {
        return level.isRaining() ? 0.12F : 0.03F;
    }

    public float getWaveAngle(float partialTicks) {
        return MathHelper.lerp(partialTicks, this.prevWaveAngle, this.waveAngle);
    }

    public boolean isForward() {
        if (getDriver() == null) {
            return false;
        }
        return entityData.get(FORWARD);
    }

    public boolean isBackward() {
        if (getDriver() == null) {
            return false;
        }
        return entityData.get(BACKWARD);
    }

    public boolean isLeft() {
        return entityData.get(LEFT);
    }

    public boolean isRight() {
        return entityData.get(RIGHT);
    }

    public boolean isAccelerating() {
        boolean b = (isForward() || isBackward()) && !horizontalCollision;
        return b;
    }

    @OnlyIn(Dist.CLIENT)
    public float getRowingTime(int side, float limbSwing) {
        return this.getPaddleState(side) ? (float) MathHelper.clampedLerp((double) this.paddlePositions[side] - (double) ((float) Math.PI / 8F), (double) this.paddlePositions[side], (double) limbSwing) : 0.0F;
    }

    ////////////////////////////////////SET////////////////////////////////////

    public void setSailColor(String color) {
        entityData.set(SAIL_COLOR, color);
    }

    public void setSailState(int state) {
        if (state != getSailState()) {
            playSailSound(state);
            entityData.set(SAIL_STATE, state);
        }
    }

    public void setPaddleState(boolean left, boolean right) {
        this.entityData.set(LEFT_PADDLE, left);
        this.entityData.set(RIGHT_PADDLE, right);
    }

    public void setForward(boolean forward) {
        entityData.set(FORWARD, forward);
    }

    public void setBackward(boolean backward) {
        entityData.set(BACKWARD, backward);
    }

    public void setLeft(boolean left) {
        entityData.set(LEFT, left);
    }

    public void setRight(boolean right) {
        entityData.set(RIGHT, right);
    }

    public void setSpeed(float speed) {
        this.entityData.set(SPEED, speed);
    }

    public void setRotSpeed(float rotSpeed) {
        this.entityData.set(ROT_SPEED, rotSpeed);
    }

    public void setWoodType(AbstractSailShip.Type type) {
        this.entityData.set(TYPE, type.ordinal());
    }

    ////////////////////////////////////SERVER////////////////////////////////////

    public void sendSailStateToServer(int state) {
        if (level.isClientSide) {
            Main.SIMPLE_CHANNEL.sendToServer(new MessageSailState(state));
        }
    }

    public void sendSteerStateToServer(){
        //Main.SIMPLE_CHANNEL.sendToServer(new MessageSteerState(this.getSteerState(0), this.getSteerState(1)));
    }

    public void checkPush() {
        List<PlayerEntity> list = level.getEntitiesOfClass(PlayerEntity.class, getBoundingBox().expandTowards(0.2, 0, 0.2).expandTowards(-0.2, 0, -0.2));

        for (PlayerEntity player : list) {
            if (!player.hasPassenger(this) && player.isShiftKeyDown()) {
                double motX = calculateMotionX(0.05F, player.yRot);
                double motZ = calculateMotionZ(0.05F, player.yRot);
                move(MoverType.PLAYER, new Vector3d(motX, 0, motZ));
                return;
            }
        }
    }

    private void controlShip() {
        if (!isVehicle()) {
            setForward(false);
            setBackward(false);
            setLeft(false);
            setRight(false);
        }
        int sailstate = getSailState();
        float modifier = Math.max(0F, 1 - (getPassengerModifier() + getCargoModifier()));


        float blockedmodf = 1;

        float maxSp = (float) ((getMaxSpeed() / (12F * 1.15F)) * modifier);
        float maxBackSp = (float) ((getMaxReverseSpeed() / (12F * 1.15F)) * modifier);
        float maxRotSp = (float) (((getMaxRotationSpeed() * 0.1F) + 1.8F) * modifier);

        float speed = MathUtils.subtractToZero(getSpeed(), getVelocityResistance());

        if (sailstate != 0) {
            switch (sailstate) {
                case 1:
                    maxSp *= 4/16F;
                    if (speed <= maxSp)
                        speed = (float) Math.min(speed + getAcceleration() * 9F / 16, maxSp);
                    break;
                case 2:
                    maxSp *= 8/16F;
                    if (speed <= maxSp)
                        speed = (float) Math.min(speed + getAcceleration() * 11F / 16, maxSp);
                    break;

                case 3:
                    maxSp *= 12/16F;
                    if (speed <= maxSp)
                        speed = (float) Math.min(speed + getAcceleration() * 13 / 16, maxSp);
                    break;
                case 4:
                    maxSp *= 1F;
                    if (speed <= maxSp) {
                        speed = (float) Math.min(speed + getAcceleration(), maxSp);
                    }
                    break;
            }
        }

        if (isForward()) {
            if (speed <= maxSp) {
                speed = (float) Math.min(speed + getAcceleration() * 1 / 8, maxSp);
            }
        }

        if (isBackward()) {
            if (speed >= -maxBackSp) {
                speed = (float) Math.max(speed - getAcceleration() * 1 / 8, -maxBackSp);
            }
        }

        if (isLeft() || isRight()) {
            speed = speed * (1.0F - (Math.abs(getRotSpeed()) * 0.02F));
        }

        setSpeed(speed * blockedmodf);

        deltaRotation = 0;
        float rotationSpeed = MathUtils.subtractToZero(getRotSpeed(), getVelocityResistance() * 3);
        if (isRight()) {
            if (rotationSpeed <= maxRotSp) {
                rotationSpeed = (float) Math.min(rotationSpeed + getRotationAcceleration() * 1 / 8, maxRotSp);
            }
        }

        if (isLeft()) {
            if (rotationSpeed >= -maxRotSp) {
                rotationSpeed = (float) Math.max(rotationSpeed - getRotationAcceleration() * 1 / 8, -maxRotSp);
            }
        }

        setRotSpeed(rotationSpeed);

        deltaRotation = rotationSpeed;

        yRot += deltaRotation;

        setDeltaMovement(calculateMotionX(getSpeed(), yRot), getDeltaMovement().y, calculateMotionZ(getSpeed(), yRot));
    }

    ////////////////////////////////////ON FUNCTIONS////////////////////////////////////

    public void onKeyPressed() {
        int state = getSailState();

        if (state != 4){
            state = 4;

        }else{
            state = 0;
        }

        sendSailStateToServer(state);
    }

    public void onKeyLowerPressed() {
        int state = getSailState();

        if (state != 4){
            state++;
        }

        sendSailStateToServer(state);
    }

    public void onKeyHigherPressed() {
        int state = getSailState();

        if (state != 0){
            state--;

        }
        sendSailStateToServer(state);
    }


    public void onInteractionWithDye(PlayerEntity player, DyeColor dyeColor, ItemStack itemStack) {
        String color = dyeColor.getName();
        setSailColor(color);
        if (!player.isCreative()) {
            itemStack.shrink(1);
        }
    }

    public void onCollision(float speed) {
        if (level.isClientSide) {
            //Main.SIMPLE_CHANNEL.sendToServer(new MessageCrash(speed, this));
        }
        setSpeed(0.00F);
        setDeltaMovement(0D, getDeltaMovement().y, 0D);
    }

    ////////////////////////////////////UPDATE FUNCTIONS////////////////////////////////////

    public void updateWaveAngle(){
        this.prevWaveAngle = this.waveAngle;
        this.waveAngle = (float) Math.sin(getWaveSpeed() * (float) tickCount) * getWaveFactor();
    }

    public void updateWaterMobs() {
        if (SmallShipsConfig.WaterMobFlee.get()) {
            double radius = 15.0D;
            List<WaterMobEntity> list1 = this.level.getEntitiesOfClass(WaterMobEntity.class, new AxisAlignedBB(getX() - radius, getY() - radius, getZ() - radius, getX() + radius, getY() + radius, getZ() + radius));
            for (WaterMobEntity ent : list1)
                fleeEntity(ent);
        }
    }
    public void updateWheelRotation() {
        wheelRotation += getWheelRotationAmount();
    }

    private void updateGravity() {
        double d1 = this.isNoGravity() ? 0.0D : (double) -0.04F;
        double d2 = 0.0D;
        float momentum = 0.05F;
        if (this.previousStatus == AbstractWaterVehicle.Status.IN_AIR && this.status != AbstractWaterVehicle.Status.IN_AIR && this.status != AbstractWaterVehicle.Status.ON_LAND) {
            this.waterLevel = this.getY(1.0D);
            this.setPos(this.getX(), (double) (this.getWaterLevelAbove() - this.getBbHeight()) + 0.101D, this.getZ());
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D));
            this.lastYd = 0.0D;
            this.status = AbstractWaterVehicle.Status.IN_WATER;
        } else {
            if (this.status == AbstractWaterVehicle.Status.IN_WATER) {
                d2 = (this.waterLevel - this.getY()) / (double) this.getBbHeight();
                momentum = 0.9F;
            }

            Vector3d vector3d = this.getDeltaMovement();
            this.setDeltaMovement(vector3d.x * (double) momentum, vector3d.y + d1, vector3d.z * (double) momentum);
            this.deltaRotation *= momentum;

            double multFactor = 0.75D;
            if (this instanceof RowBoatEntity)
                multFactor = 0.5D;

            if (d2 > 0.0D) {
                Vector3d vector3d1 = this.getDeltaMovement();
                this.setDeltaMovement(vector3d1.x, (vector3d1.y + d2 * 0.06153846016296973D) * multFactor, vector3d1.z);
            }
        }

    }

    public void updateControls(boolean forward, boolean backward, boolean left, boolean right, PlayerEntity player) {
        boolean needsUpdate = false;

        if (isForward() != forward) {
            setForward(forward);
            needsUpdate = true;
        }

        if (isBackward() != backward) {
            setBackward(backward);
            needsUpdate = true;
        }

        if (isLeft() != left) {
            setLeft(left);
            needsUpdate = true;
        }

        if (isRight() != right) {
            setRight(right);
            needsUpdate = true;
        }
        if (this.level.isClientSide && needsUpdate) {
            Main.SIMPLE_CHANNEL.sendToServer(new MessageControlShip(forward, backward, left, right, player));
        }
    }

    protected void updatePaddles() {
        boolean hasDriver = this.getControllingPassenger() instanceof PlayerEntity;
        if (!hasDriver) {
            setPaddleState(false, false);
        } else {
            boolean forward = isForward();
            boolean backward = isBackward();
            boolean left = isLeft();
            boolean right = isRight();
            boolean leftPaddleActive = forward || backward || left;
            boolean rightPaddleActive = forward || backward || right;
            setPaddleState(leftPaddleActive, rightPaddleActive);
        }

        for (int i = 0; i < this.paddlePositions.length; ++i) {
            if (this.getPaddleState(i)) {
                this.paddlePositions[i] = (float) ((double) this.paddlePositions[i] + 0.39269908169872414D);
                if (this.paddlePositions[i] > (float) (Math.PI * 2D)) {
                    this.paddlePositions[i] -= (float) (Math.PI * 2D);
                }
            } else {
                this.paddlePositions[i] = 0.0F;
            }
        }
    }

    ////////////////////////////////////OTHER FUNCTIONS////////////////////////////////////

    public boolean canPlayerEnterShip(PlayerEntity player) {
        return true;
    }

    //////////////////////////////////////SOUND////////////////////////////////////////////

    public void playSailSound(int state) {
        if (state != 0) {
            this.level.playSound(null, this.getX(), this.getY() + 4 , this.getZ(), SoundInit.SHIP_SAIL_0.get(), this.getSoundSource(), 15.0F, 0.8F + 0.4F * this.random.nextFloat());
        } else {
            this.level.playSound(null, this.getX(), this.getY() + 4, this.getZ(), SoundInit.SHIP_SAIL_1.get(), this.getSoundSource(), 10.0F, 0.8F + 0.4F * this.random.nextFloat());
        }
    }

    ////////////////////////////////////OTHER FUNCTIONS////////////////////////////////////

    public void renderSailColor(MatrixStack matrixStack, IRenderTypeBuffer buffer , int packedLight, float partialTicks, ModelSail sailModel) {
        RenderSailColor.renderSailColor(this, partialTicks, matrixStack, getSailColor(), buffer,  packedLight, sailModel);
    }

    public void destroyShip(DamageSource dmg) {
        remove();
    }

    public void fleeEntity(MobEntity entity) {
        double fleeDistance = 10.0D;
        Vector3d vecBoat = new Vector3d(getX(), getY(), getZ());
        Vector3d vecEntity = new Vector3d(entity.getX(), entity.getY(), entity.getZ());
        Vector3d fleeDir = vecEntity.subtract(vecBoat);
        fleeDir = fleeDir.normalize();
        Vector3d fleePos = new Vector3d(vecEntity.x + fleeDir.x * fleeDistance, vecEntity.y + fleeDir.y * fleeDistance, vecEntity.z + fleeDir.z * fleeDistance);
        entity.getNavigation().moveTo(fleePos.x, fleePos.y, fleePos.z, 1.5D);
    }

    private void knockBack(List<Entity> entities) {
        double d0 = (this.getBoundingBox().minX + this.getBoundingBox().maxX) / 2.5D;
        double d1 = (this.getBoundingBox().minZ + this.getBoundingBox().maxZ) / 2.5D;

        for(Entity entity : entities) {
            if (entity instanceof LivingEntity) {
                double d2 = entity.getX() - d0;
                double d3 = entity.getZ() - d1;
                double d4 = Math.max(d2 * d2 + d3 * d3, 0.1D);
                entity.push(d2 / d4 * 0.4D, (double)0.0F, d3 / d4 * 0.4D);
                this.canCollideWith(entity);
            }
        }
    }

    private void breakLily() {
        AxisAlignedBB boundingBox = getBoundingBox();
        double offset = 0.75D;
        BlockPos start = new BlockPos(boundingBox.minX - offset, boundingBox.minY - offset, boundingBox.minZ - offset);
        BlockPos end = new BlockPos(boundingBox.maxX + offset, boundingBox.maxY + offset, boundingBox.maxZ + offset);
        BlockPos.Mutable pos = new BlockPos.Mutable();
        boolean hasBroken = false;
        if (level.hasChunksAt(start, end)) {
            for (int i = start.getX(); i <= end.getX(); ++i) {
                for (int j = start.getY(); j <= end.getY(); ++j) {
                    for (int k = start.getZ(); k <= end.getZ(); ++k) {
                        pos.set(i, j, k);
                        BlockState blockstate = level.getBlockState(pos);
                        if (blockstate.getBlock() instanceof LilyPadBlock) {
                            level.destroyBlock(pos, true);
                            hasBroken = true;
                        }
                    }
                }
            }
        }
        if (hasBroken) {
            level.playSound(null, getX(), getY(), getZ(), SoundEvents.CROP_BREAK, SoundCategory.BLOCKS, 1F, 0.9F + 0.2F * random.nextFloat());
        }
    }

    public Item getItemBoat() {
        switch (this.getWoodType()) {
            case OAK:
            default:
                return Items.OAK_BOAT;
            case SPRUCE:
                return Items.SPRUCE_BOAT;
            case BIRCH:
                return Items.BIRCH_BOAT;
            case JUNGLE:
                return Items.JUNGLE_BOAT;
            case ACACIA:
                return Items.ACACIA_BOAT;
            case DARK_OAK:
                return Items.DARK_OAK_BOAT;
        }
    }

    protected ItemStack createShipItemStack(boolean broken) {
        ItemStack stack = new ItemStack(this.getItemBoat());
        if (broken) {
            stack.getOrCreateTag().putBoolean(BROKEN_TAG, true);
        }
        stack.getOrCreateTag().putString("SailColor", getSailColor());
        return stack;
    }

    public void applyItemData(ItemStack stack) {
        CompoundNBT tag = stack.getTag();
        if (tag != null && tag.contains("SailColor", 8)) {
            this.setSailColor(tag.getString("SailColor"));
        }
    }

    public enum Type {
        OAK("oak"),
        SPRUCE("spruce"),
        BIRCH("birch"),
        JUNGLE("jungle"),
        ACACIA("acacia"),
        DARK_OAK("dark_oak");
        /*
        //BOP
        BOP_CHERRY("bop_cherry"),
        BOP_DEAD("bop_cherry"),
        BOP_FIR("bop_fir"),
        BOP_HELLBARK("bop_hellbark"),
        BOP_JACARANDA("bop_jacaranda"),
        BOP_MAGIC("bop_magic"),
        BOP_MAHOGANY("bop_mahogany"),
        BOP_PALM("bop_palm"),
        BOP_REDWOOD("bop_redwood"),
        BOP_UMBRAN("bop_umbran"),
        BOP_WILLOW("bop_willow"),
        //LOTR
        LOTR_APPLE("lotr_apple"),
        LOTR_ASPEN("lotr_aspen"),
        LOTR_BEECH("lotr_beech"),
        LOTR_CEDAR("lotr_cedar"),
        LOTR_CHARRED("lotr_charred"),
        LOTR_CHERRY("lotr_cherry"),
        LOTR_CYPRESS("lotr_cypress"),
        LOTR_FIR("lotr_fir"),
        LOTR_GREEN_OAK("lotr_green_oak"),
        LOTR_HOLLY("lotr_holly"),
        LOTR_LAIRELOSSE("lotr_lairelosse"),
        LOTR_LARCH("lotr_larch"),
        LOTR_LEBETHRON("lotr_lebethron"),
        LOTR_MALLORN("lotr_mallorn"),
        LOTR_MAPLE("lotr_maple"),
        LOTR_MIRK_OAK("lotr_mirk_oak"),
        LOTR_PEAR("lotr_pear"),
        LOTR_PINE("lotr_pine"),
        LOTR_ROTTEN("lotr_rotten"),
        //ENVI
        ENVI_CHERRY("envi_cherry"),
        ENVI_WISTERIA("envi_wisteria"),
        ENVI_WILLOW("envi_willow");

         */

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }


        public String toString() {
            return this.name;
        }

        /**
         * Get a boat type by it's enum ordinal
         */
        public static AbstractSailShip.Type byId(int id) {
            AbstractSailShip.Type[] aboatentity$type = values();
            if (id < 0 || id >= aboatentity$type.length) {
                id = 0;
            }

            return aboatentity$type[id];
        }

        public static AbstractSailShip.Type getTypeFromString(String nameIn) {
            AbstractSailShip.Type[] aboatentity$type = values();

            for (int i = 0; i < aboatentity$type.length; ++i) {
                if (aboatentity$type[i].getName().equals(nameIn)) {
                    return aboatentity$type[i];
                }
            }

            return aboatentity$type[0];
        }
    }
}