package com.talhanation.smallships.items;

import com.talhanation.smallships.entities.AbstractSailShip;
import com.talhanation.smallships.entities.AbstractWaterVehicle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class ShipItem<T extends AbstractSailShip> extends Item {

    private static final Predicate<Entity> SPAWN_BLOCKERS = EntityPredicates.NO_SPECTATORS.and(Entity::canBeCollidedWith);

    protected final AbstractSailShip.Type type;
    private final ShipFactory<T> factory;

    public ShipItem(AbstractSailShip.Type type, Properties properties, ShipFactory<T> factory) {
        super(properties);
        this.type = type;
        this.factory = factory;
    }

    public AbstractSailShip.Type getType() {
        return type;
    }

    public T createShipForRender(World world) {
        T ship = factory.create(world, 0.0D, 0.0D, 0.0D);
        if (ship != null) {
            ship.setWoodType(type);
            onShipCreated(ship);
        }
        return ship;
    }

    protected T createShip(World world, double x, double y, double z) {
        T ship = factory.create(world, x, y, z);
        if (ship != null) {
            ship.setWoodType(type);
            onShipCreated(ship);
        }
        return ship;
    }

    protected void onShipCreated(T ship) {
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.hasTag() && stack.getTag().getBoolean(AbstractSailShip.BROKEN_TAG)) {
            return ActionResult.fail(stack);
        }

        RayTraceResult hitResult = getPlayerPOVHitResult(world, player, RayTraceContext.FluidMode.ANY);
        if (hitResult.getType() != RayTraceResult.Type.BLOCK) {
            return ActionResult.pass(stack);
        }

        Vector3d lookVector = player.getViewVector(1.0F);
        List<Entity> entities = world.getEntities(player, player.getBoundingBox().expandTowards(lookVector.scale(5.0D)).inflate(1.0D), SPAWN_BLOCKERS);
        if (!entities.isEmpty()) {
            Vector3d eyePosition = player.getEyePosition(1.0F);
            for (Entity entity : entities) {
                AxisAlignedBB box = entity.getBoundingBox().inflate(entity.getPickRadius());
                if (box.contains(eyePosition)) {
                    return ActionResult.pass(stack);
                }
            }
        }

        T ship = createShip(world, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
        if (ship == null) {
            return ActionResult.fail(stack);
        }

        ship.yRot = player.yRot + 90F;
        if (!world.noCollision(ship, ship.getBoundingBox().inflate(-0.1D))) {
            return ActionResult.fail(stack);
        }

        if (!world.isClientSide) {
            ship.applyItemData(stack);
            world.addFreshEntity(ship);
            if (ship.getStatus().equals(AbstractWaterVehicle.Status.IN_WATER)) {
                world.playSound(null, ship.getX(), ship.getY(), ship.getZ(), SoundEvents.PLAYER_SPLASH, SoundCategory.BLOCKS, 0.75F, 0.8F);
                world.playSound(null, ship.getX(), ship.getY(), ship.getZ(), SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundCategory.BLOCKS, 0.75F, 0.8F);
            }
            if (!player.abilities.instabuild) {
                stack.shrink(1);
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        return ActionResult.sidedSuccess(stack, world.isClientSide());
    }

    @FunctionalInterface
    public interface ShipFactory<T extends AbstractSailShip> {
        T create(World world, double x, double y, double z);
    }
}
