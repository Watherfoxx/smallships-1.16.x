package com.talhanation.smallships.init;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.entities.BriggEntity;
import com.talhanation.smallships.entities.CogEntity;
import com.talhanation.smallships.entities.DhowEntity;
import com.talhanation.smallships.entities.DrakkarEntity;
import com.talhanation.smallships.entities.GalleyEntity;
import com.talhanation.smallships.entities.RowBoatEntity;
import com.talhanation.smallships.entities.WarGalleyEntity;
import com.talhanation.smallships.entities.projectile.CannonBallEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MOD_ID);

    public static final RegistryObject<EntityType<CogEntity>> COG = ENTITY_TYPES.register("cog",
            () -> EntityType.Builder.<CogEntity>of(CogEntity::new, EntityClassification.MISC)
                    .sized(3.5F, 1.25F)
                    .clientTrackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "cog").toString()));

    public static final RegistryObject<EntityType<BriggEntity>> BRIGG = ENTITY_TYPES.register("brigg",
            () -> EntityType.Builder.<BriggEntity>of(BriggEntity::new, EntityClassification.MISC)
                    .sized(3.5F, 1.25F)
                    .clientTrackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "brigg").toString()));

    public static final RegistryObject<EntityType<RowBoatEntity>> ROWBOAT = ENTITY_TYPES.register("rowboat",
            () -> EntityType.Builder.<RowBoatEntity>of(RowBoatEntity::new, EntityClassification.MISC)
                    .sized(1.8F, 0.7F)
                    .clientTrackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "rowboat").toString()));

    public static final RegistryObject<EntityType<DhowEntity>> DHOW = ENTITY_TYPES.register("dhow",
            () -> EntityType.Builder.<DhowEntity>of(DhowEntity::new, EntityClassification.MISC)
                    .sized(2.8F, 1.2F)
                    .clientTrackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "dhow").toString()));

    public static final RegistryObject<EntityType<DrakkarEntity>> DRAKKAR = ENTITY_TYPES.register("drakkar",
            () -> EntityType.Builder.<DrakkarEntity>of(DrakkarEntity::new, EntityClassification.MISC)
                    .sized(3.0F, 1.2F)
                    .clientTrackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "drakkar").toString()));

    public static final RegistryObject<EntityType<GalleyEntity>> GALLEY = ENTITY_TYPES.register("galley",
            () -> EntityType.Builder.<GalleyEntity>of(GalleyEntity::new, EntityClassification.MISC)
                    .sized(3.4F, 1.3F)
                    .clientTrackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "galley").toString()));

    public static final RegistryObject<EntityType<WarGalleyEntity>> WAR_GALLEY = ENTITY_TYPES.register("war_galley",
            () -> EntityType.Builder.<WarGalleyEntity>of(WarGalleyEntity::new, EntityClassification.MISC)
                    .sized(3.4F, 1.3F)
                    .clientTrackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "war_galley").toString()));

/*
    public static final RegistryObject<EntityType<SailShipPart>> SHIP_PART = ENTITY_TYPES.register("ship_part",
            () -> EntityType.Builder.<SailShipPart>of(SailShipPart::new, EntityClassification.MISC)
                    .sized(3.5F, 1.25F)
                    .clientTrackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "ship_part").toString()));
    */
    public static final RegistryObject<EntityType<CannonBallEntity>> CANNON_BALL = ENTITY_TYPES.register("cannon_ball",
            () -> EntityType.Builder.<CannonBallEntity>of(CannonBallEntity::new, EntityClassification.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(20)
                    .setUpdateInterval(10)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "cannon_ball").toString()));
}