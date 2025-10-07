package com.talhanation.smallships.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class SmallShipsConfig {


    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec CONFIG;
    public static ForgeConfigSpec.IntValue VERSION;
    public static final int NEW_VERSION = 12;

    public static ForgeConfigSpec.BooleanValue PlaySwimmSound;
    public static ForgeConfigSpec.BooleanValue WaterMobFlee;

    public static ForgeConfigSpec.DoubleValue ShipZoom;
    public static ForgeConfigSpec.BooleanValue EnterThirdPerson;
    public static ForgeConfigSpec.BooleanValue MakeWaveAnimation;

    public static ForgeConfigSpec.DoubleValue RowBoatHealth;
    public static ForgeConfigSpec.DoubleValue RowBoatSpeedFactor;
    public static ForgeConfigSpec.DoubleValue RowBoatTurnFactor;
    public static ForgeConfigSpec.DoubleValue RowBoatMaxReverseSpeed;
    public static ForgeConfigSpec.DoubleValue RowBoatAcceleration;
    public static ForgeConfigSpec.DoubleValue RowBoatRotationAcceleration;
    public static ForgeConfigSpec.DoubleValue RowBoatVelocityResistance;
    public static ForgeConfigSpec.IntValue RowBoatInventorySize;
    public static ForgeConfigSpec.IntValue RowBoatInventoryPages;

    public static ForgeConfigSpec.DoubleValue DrakkarHealth;
    public static ForgeConfigSpec.DoubleValue DrakkarSpeedFactor;
    public static ForgeConfigSpec.DoubleValue DrakkarTurnFactor;
    public static ForgeConfigSpec.DoubleValue DrakkarMaxReverseSpeed;
    public static ForgeConfigSpec.DoubleValue DrakkarAcceleration;
    public static ForgeConfigSpec.DoubleValue DrakkarRotationAcceleration;
    public static ForgeConfigSpec.DoubleValue DrakkarVelocityResistance;
    public static ForgeConfigSpec.DoubleValue DrakkarIceBreakSpeed;
    public static ForgeConfigSpec.IntValue DrakkarInventorySize;
    public static ForgeConfigSpec.IntValue DrakkarInventoryPages;
    public static ForgeConfigSpec.IntValue DrakkarMaxCannons;

    public static ForgeConfigSpec.DoubleValue GalleyHealth;
    public static ForgeConfigSpec.DoubleValue GalleySpeedFactor;
    public static ForgeConfigSpec.DoubleValue GalleyTurnFactor;
    public static ForgeConfigSpec.DoubleValue GalleyMaxReverseSpeed;
    public static ForgeConfigSpec.DoubleValue GalleyAcceleration;
    public static ForgeConfigSpec.DoubleValue GalleyRotationAcceleration;
    public static ForgeConfigSpec.DoubleValue GalleyVelocityResistance;
    public static ForgeConfigSpec.IntValue GalleyInventorySize;
    public static ForgeConfigSpec.IntValue GalleyInventoryPages;
    public static ForgeConfigSpec.IntValue GalleyMaxCannons;

    public static ForgeConfigSpec.DoubleValue WarGalleyHealth;
    public static ForgeConfigSpec.DoubleValue WarGalleySpeedFactor;
    public static ForgeConfigSpec.DoubleValue WarGalleyTurnFactor;
    public static ForgeConfigSpec.DoubleValue WarGalleyMaxReverseSpeed;
    public static ForgeConfigSpec.DoubleValue WarGalleyAcceleration;
    public static ForgeConfigSpec.DoubleValue WarGalleyRotationAcceleration;
    public static ForgeConfigSpec.DoubleValue WarGalleyVelocityResistance;
    public static ForgeConfigSpec.IntValue WarGalleyInventorySize;
    public static ForgeConfigSpec.IntValue WarGalleyInventoryPages;
    public static ForgeConfigSpec.IntValue WarGalleyMaxCannons;

    public static ForgeConfigSpec.DoubleValue DhowHealth;
    public static ForgeConfigSpec.DoubleValue DhowSpeedFactor;
    public static ForgeConfigSpec.DoubleValue DhowTurnFactor;
    public static ForgeConfigSpec.DoubleValue DhowMaxReverseSpeed;
    public static ForgeConfigSpec.DoubleValue DhowAcceleration;
    public static ForgeConfigSpec.DoubleValue DhowRotationAcceleration;
    public static ForgeConfigSpec.DoubleValue DhowVelocityResistance;
    public static ForgeConfigSpec.IntValue DhowInventorySize;
    public static ForgeConfigSpec.IntValue DhowInventoryPages;
    public static ForgeConfigSpec.IntValue DhowMaxCannons;

    public static ForgeConfigSpec.DoubleValue CogHealth;
    public static ForgeConfigSpec.DoubleValue CogSpeedFactor;
    public static ForgeConfigSpec.DoubleValue CogTurnFactor;
    public static ForgeConfigSpec.DoubleValue CogMaxReverseSpeed;
    public static ForgeConfigSpec.DoubleValue CogAcceleration;
    public static ForgeConfigSpec.DoubleValue CogRotationAcceleration;
    public static ForgeConfigSpec.DoubleValue CogVelocityResistance;
    public static ForgeConfigSpec.IntValue CogInventorySize;
    public static ForgeConfigSpec.IntValue CogInventoryPages;
    public static ForgeConfigSpec.IntValue CogMaxCannons;

    public static ForgeConfigSpec.DoubleValue BriggHealth;
    public static ForgeConfigSpec.DoubleValue BriggSpeedFactor;
    public static ForgeConfigSpec.DoubleValue BriggTurnFactor;
    public static ForgeConfigSpec.DoubleValue BriggMaxReverseSpeed;
    public static ForgeConfigSpec.DoubleValue BriggAcceleration;
    public static ForgeConfigSpec.DoubleValue BriggRotationAcceleration;
    public static ForgeConfigSpec.DoubleValue BriggVelocityResistance;
    public static ForgeConfigSpec.IntValue BriggInventorySize;
    public static ForgeConfigSpec.IntValue BriggInventoryPages;
    public static ForgeConfigSpec.IntValue BriggMaxCannons;

    public static ForgeConfigSpec.ConfigValue<List<String>> PassengerBlackList;

    static {
        VERSION = BUILDER.comment("\n" +"##Version, do not change!##")
                .defineInRange("Version", 0, 0, Integer.MAX_VALUE);

        BUILDER.comment("Small Ships Config:").push("Ships");

        PlaySwimmSound = BUILDER.comment("\n" + "----Should Ships Make Swimming sounds?----" + "\n" +
                        "\t" + "(takes effect after restart)" + "\n" +
                        "\t" + "default: true")
                .define("PlaySwimmSound", true);

        WaterMobFlee = BUILDER.comment("\n" + "----Should Ships Make WaterMobs flee?----" + "\n" +
                        "\t" + "(takes effect after restart)" + "\n" +
                        "\t" + "default: true")
                .define("WaterMobFlee", true);

        ShipZoom = BUILDER.comment("\n" +"----Ship Zoom.----" + "\n" +
                        "\t" + "(takes effect after restart)" + "\n" +
                        "\t" + "default: 16")
                .worldRestart()
                .defineInRange("ShipZoom", 16D, 1D, 20D);

        EnterThirdPerson = BUILDER.comment("\n" +"----Should the player enter ships in third person?----" + "\n" +
                        "\t" + "(takes effect after restart)" + "\n" +
                        "\t" + "default: true")
                .worldRestart()
                .define("EnterThirdPerson", true);

        MakeWaveAnimation = BUILDER.comment("\n" +"----Should the Ships make waving animation?----" + "\n" +
                        "\t" + "(takes effect after restart)" + "\n" +
                        "\t" + "default: true")
                .worldRestart()
                .define("MakeWaveAnimation", true);

        PassengerBlackList = BUILDER.comment("\n" + "----Passenger Blacklist----" + "\n" +
                        "\t" + "(takes effect after restart)" + "\n" +
                        "\t" + "Entities in this list won't be able to get on the boat, for example: [\"minecraft:creeper\", \"minecraft:sheep\"]")
                .worldRestart()
                .define("Passenger BlackList", new ArrayList<>());

        RowBoatHealth = BUILDER.comment("\n" +"----Row Boat Health.----" + "\n" +
                        "\t" + "(takes effect after restart)" + "\n" +
                        "\t" + "(vanilla boat value = 60)" + "\n" +
                        "\t" + "default: 100")
                .worldRestart()
                .defineInRange("RowBoatHealth", 100.0, 0.0, 10000.0);

        RowBoatSpeedFactor = BUILDER.comment("\n" +"----Row Boat Speed Factor.----" + "\n" +
                        "\t" + "(takes effect after restart)" + "\n" +
                        "\t" + "default: 2.0")
                .worldRestart()
                .defineInRange("RowBoatSpeedFactor", 2.0, 0.0, 20.0);

        RowBoatTurnFactor = BUILDER.comment("\n" +"----Row Boat Turn Factor.----" + "\n" +
                        "\t" + "(takes effect after restart)" + "\n" +
                        "\t" + "default: 0.8")
                .worldRestart()
                .defineInRange("RowBoatTurnFactor", 0.8, 0.0, 1.0);

        RowBoatMaxReverseSpeed = BUILDER.comment("\n" +"----Row Boat Max Reverse Speed.----" + "\n" +
                        "\t" + "(takes effect after restart)" + "\n" +
                        "\t" + "default: 0.5")
                .worldRestart()
                .defineInRange("RowBoatMaxReverseSpeed", 0.5, 0.0, 20.0);

        RowBoatAcceleration = BUILDER.comment("\n" +"----Row Boat Acceleration.----" + "\n" +
                        "\t" + "(takes effect after restart)" + "\n" +
                        "\t" + "default: 0.3")
                .worldRestart()
                .defineInRange("RowBoatAcceleration", 0.3, 0.0, 5.0);

        RowBoatRotationAcceleration = BUILDER.comment("\n" +"----Row Boat Rotation Acceleration.----" + "\n" +
                        "\t" + "(takes effect after restart)" + "\n" +
                        "\t" + "default: 0.6")
                .worldRestart()
                .defineInRange("RowBoatRotationAcceleration", 0.6, 0.0, 5.0);

        RowBoatVelocityResistance = BUILDER.comment("\n" +"----Row Boat Velocity Resistance.----" + "\n" +
                        "\t" + "(takes effect after restart)" + "\n" +
                        "\t" + "default: 0.009")
                .worldRestart()
                .defineInRange("RowBoatVelocityResistance", 0.009, 0.0, 1.0);

        RowBoatInventorySize = BUILDER.comment("\n" +"----Row Boat Inventory Size.----" + "\n" +
                        "\t" + "(takes effect after restart)" + "\n" +
                        "\t" + "default: 9")
                .worldRestart()
                .defineInRange("RowBoatInventorySize", 9, 0, 108);

        RowBoatInventoryPages = BUILDER.comment("\n" +"----Row Boat Inventory Pages.----" + "\n" +
                        "\t" + "(takes effect after restart)" + "\n" +
                        "\t" + "default: 1")
                .worldRestart()
                .defineInRange("RowBoatInventoryPages", 1, 1, 2);

        GalleyHealth = BUILDER.comment("\n" +"----Galley Health.----" + "\n" +
                        "\t" + "(takes effect after restart)" + "\n" +
                        "\t" + "(vanilla boat value = 60)" + "\n" +
                        "\t" + "default: 600")
                .worldRestart()
                .defineInRange("GalleyHealth", 600.0, 0.0, 10000.0);

        GalleySpeedFactor = BUILDER.comment("\n" +"----Galley Speed Factor.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 5.0")
                .worldRestart()
                .defineInRange("GalleySpeedFactor", 5.0, 0.0, 20.0);

        GalleyTurnFactor = BUILDER.comment("\n" +"----Galley Turn Factor.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.5")
                .worldRestart()
                .defineInRange("GalleyTurnFactor", 0.5, 0.0, 1.0);

        GalleyMaxReverseSpeed = BUILDER.comment("\n" +"----Galley Max Reverse Speed.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 1.0")
                .worldRestart()
                .defineInRange("GalleyMaxReverseSpeed", 1.0, 0.0, 20.0);

        GalleyAcceleration = BUILDER.comment("\n" +"----Galley Acceleration.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.024")
                .worldRestart()
                .defineInRange("GalleyAcceleration", 0.024, 0.0, 5.0);

        GalleyRotationAcceleration = BUILDER.comment("\n" +"----Galley Rotation Acceleration.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.3")
                .worldRestart()
                .defineInRange("GalleyRotationAcceleration", 0.3, 0.0, 5.0);

        GalleyVelocityResistance = BUILDER.comment("\n" +"----Galley Velocity Resistance.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.008")
                .worldRestart()
                .defineInRange("GalleyVelocityResistance", 0.008, 0.0, 1.0);

        GalleyInventorySize = BUILDER.comment("\n" +"----Galley Inventory Size.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 36")
                .worldRestart()
                .defineInRange("GalleyInventorySize", 36, 0, 108);

        GalleyInventoryPages = BUILDER.comment("\n" +"----Galley Inventory Pages.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 1")
                .worldRestart()
                .defineInRange("GalleyInventoryPages", 1, 1, 2);

        GalleyMaxCannons = BUILDER.comment("\n" +"----Galley Max Cannons.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 4")
                .worldRestart()
                .defineInRange("GalleyMaxCannons", 4, 0, 8);

        CogHealth = BUILDER.comment("\n" +"----Cog Health.----" + "\n" +
                        "\t" + "(takes effect after restart)" + "\n" +
                        "\t" + "(vanilla boat value = 60)" + "\n" +
                        "\t" + "default: 300.0")
                .worldRestart()
                .defineInRange("CogHealth", 300.0, 0.0, 10000.0);

        CogSpeedFactor = BUILDER.comment("\n" +"----Cog Speed Factor.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 5.5" )
                .worldRestart()
                .defineInRange("CogSpeedFactor", 5.5, 0.0, 20.0);

        CogTurnFactor = BUILDER.comment("\n" +"----Cog Turn Factor.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.1")
                .worldRestart()
                .defineInRange("CogTurnFactor", 0.1, 0.0, 1.0);

        CogMaxReverseSpeed = BUILDER.comment("\n" +"----Cog Max Reverse Speed.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 1.1")
                .worldRestart()
                .defineInRange("CogMaxReverseSpeed", 1.1, 0.0, 20.0);

        CogAcceleration = BUILDER.comment("\n" +"----Cog Acceleration.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.015")
                .worldRestart()
                .defineInRange("CogAcceleration", 0.015, 0.0, 5.0);

        CogRotationAcceleration = BUILDER.comment("\n" +"----Cog Rotation Acceleration.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.3")
                .worldRestart()
                .defineInRange("CogRotationAcceleration", 0.3, 0.0, 5.0);

        CogVelocityResistance = BUILDER.comment("\n" +"----Cog Velocity Resistance.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.009")
                .worldRestart()
                .defineInRange("CogVelocityResistance", 0.009, 0.0, 1.0);

        CogInventorySize = BUILDER.comment("\n" +"----Cog Inventory Size.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 54")
                .worldRestart()
                .defineInRange("CogInventorySize", 54, 0, 108);

        CogInventoryPages = BUILDER.comment("\n" +"----Cog Inventory Pages.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 1")
                .worldRestart()
                .defineInRange("CogInventoryPages", 1, 1, 2);

        CogMaxCannons = BUILDER.comment("\n" +"----Cog Max Cannons.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 4")
                .worldRestart()
                .defineInRange("CogMaxCannons", 4, 0, 6);

        WarGalleyHealth = BUILDER.comment("\n" +"----War Galley Health.----" + "\n" +
                        "\t" + "(takes effect after restart)" + "\n" +
                        "\t" + "(vanilla boat value = 60)" + "\n" +
                        "\t" + "default: 600")
                .worldRestart()
                .defineInRange("WarGalleyHealth", 600.0, 0.0, 10000.0);

        WarGalleySpeedFactor = BUILDER.comment("\n" +"----War Galley Speed Factor.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 5.5")
                .worldRestart()
                .defineInRange("WarGalleySpeedFactor", 5.5, 0.0, 20.0);

        WarGalleyTurnFactor = BUILDER.comment("\n" +"----War Galley Turn Factor.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.4")
                .worldRestart()
                .defineInRange("WarGalleyTurnFactor", 0.4, 0.0, 1.0);

        WarGalleyMaxReverseSpeed = BUILDER.comment("\n" +"----War Galley Max Reverse Speed.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 1.1")
                .worldRestart()
                .defineInRange("WarGalleyMaxReverseSpeed", 1.1, 0.0, 20.0);

        WarGalleyAcceleration = BUILDER.comment("\n" +"----War Galley Acceleration.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.022")
                .worldRestart()
                .defineInRange("WarGalleyAcceleration", 0.022, 0.0, 5.0);

        WarGalleyRotationAcceleration = BUILDER.comment("\n" +"----War Galley Rotation Acceleration.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.28")
                .worldRestart()
                .defineInRange("WarGalleyRotationAcceleration", 0.28, 0.0, 5.0);

        WarGalleyVelocityResistance = BUILDER.comment("\n" +"----War Galley Velocity Resistance.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.0085")
                .worldRestart()
                .defineInRange("WarGalleyVelocityResistance", 0.0085, 0.0, 1.0);

        WarGalleyInventorySize = BUILDER.comment("\n" +"----War Galley Inventory Size.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 36")
                .worldRestart()
                .defineInRange("WarGalleyInventorySize", 36, 0, 108);

        WarGalleyInventoryPages = BUILDER.comment("\n" +"----War Galley Inventory Pages.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 1")
                .worldRestart()
                .defineInRange("WarGalleyInventoryPages", 1, 1, 2);

        WarGalleyMaxCannons = BUILDER.comment("\n" +"----War Galley Max Cannons.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 6")
                .worldRestart()
                .defineInRange("WarGalleyMaxCannons", 6, 0, 10);

        DrakkarHealth = BUILDER.comment("\n" +"----Drakkar Health.----" + "\n" +
                        "\t" + "(takes effect after restart)" + "\n" +
                        "\t" + "(vanilla boat value = 60)" + "\n" +
                        "\t" + "default: 360")
                .worldRestart()
                .defineInRange("DrakkarHealth", 360.0, 0.0, 10000.0);

        DrakkarSpeedFactor = BUILDER.comment("\n" +"----Drakkar Speed Factor.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 5.5")
                .worldRestart()
                .defineInRange("DrakkarSpeedFactor", 5.5, 0.0, 20.0);

        DrakkarTurnFactor = BUILDER.comment("\n" +"----Drakkar Turn Factor.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.3")
                .worldRestart()
                .defineInRange("DrakkarTurnFactor", 0.3, 0.0, 1.0);

        DrakkarMaxReverseSpeed = BUILDER.comment("\n" +"----Drakkar Max Reverse Speed.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 1.1")
                .worldRestart()
                .defineInRange("DrakkarMaxReverseSpeed", 1.1, 0.0, 20.0);

        DrakkarAcceleration = BUILDER.comment("\n" +"----Drakkar Acceleration.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.022")
                .worldRestart()
                .defineInRange("DrakkarAcceleration", 0.022, 0.0, 5.0);

        DrakkarRotationAcceleration = BUILDER.comment("\n" +"----Drakkar Rotation Acceleration.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.32")
                .worldRestart()
                .defineInRange("DrakkarRotationAcceleration", 0.32, 0.0, 5.0);

        DrakkarVelocityResistance = BUILDER.comment("\n" +"----Drakkar Velocity Resistance.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.0085")
                .worldRestart()
                .defineInRange("DrakkarVelocityResistance", 0.0085, 0.0, 1.0);

        DrakkarIceBreakSpeed = BUILDER.comment("\n" +"----Drakkar Ice Break Speed.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "higher values = slower break speed"  + "\n" +
                "\t" + "default: 2.0")
                .worldRestart()
                .defineInRange("DrakkarIceBreakSpeed", 2.0, 0.0, 100);

        DrakkarInventorySize = BUILDER.comment("\n" +"----Drakkar Inventory Size.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 18")
                .worldRestart()
                .defineInRange("DrakkarInventorySize", 18, 0, 108);

        DrakkarInventoryPages = BUILDER.comment("\n" +"----Drakkar Inventory Pages.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 1")
                .worldRestart()
                .defineInRange("DrakkarInventoryPages", 1, 1, 2);

        DrakkarMaxCannons = BUILDER.comment("\n" +"----Drakkar Max Cannons.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0")
                .worldRestart()
                .defineInRange("DrakkarMaxCannons", 0, 0, 6);

        BriggHealth = BUILDER.comment("\n" +"----Brigg Health.----" + "\n" +
                        "\t" + "(takes effect after restart)" + "\n" +
                        "\t" + "(vanilla boat value = 60)" + "\n" +
                        "\t" + "default: 600")
                .worldRestart()
                .defineInRange("BriggHealth", 600.0, 0.0, 10000.0);

        BriggSpeedFactor = BUILDER.comment("\n" +"----Brigg Speed Factor.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 6.0" )
                .worldRestart()
                .defineInRange("BriggSpeedFactor", 6.0, 0.0, 20.0);

        BriggTurnFactor = BUILDER.comment("\n" +"----Brigg Turn Factor.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.2")
                .worldRestart()
                .defineInRange("BriggTurnFactor", 0.2, 0.0, 1.0);

        BriggMaxReverseSpeed = BUILDER.comment("\n" +"----Brigg Max Reverse Speed.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 1.2")
                .worldRestart()
                .defineInRange("BriggMaxReverseSpeed", 1.2, 0.0, 20.0);

        BriggAcceleration = BUILDER.comment("\n" +"----Brigg Acceleration.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.0135")
                .worldRestart()
                .defineInRange("BriggAcceleration", 0.0135, 0.0, 5.0);

        BriggRotationAcceleration = BUILDER.comment("\n" +"----Brigg Rotation Acceleration.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.3")
                .worldRestart()
                .defineInRange("BriggRotationAcceleration", 0.3, 0.0, 5.0);

        BriggVelocityResistance = BUILDER.comment("\n" +"----Brigg Velocity Resistance.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.009")
                .worldRestart()
                .defineInRange("BriggVelocityResistance", 0.009, 0.0, 1.0);

        BriggInventorySize = BUILDER.comment("\n" +"----Brigg Inventory Size.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 108")
                .worldRestart()
                .defineInRange("BriggInventorySize", 108, 0, 108);

        BriggInventoryPages = BUILDER.comment("\n" +"----Brigg Inventory Pages.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 1")
                .worldRestart()
                .defineInRange("BriggInventoryPages", 1, 1, 2);

        BriggMaxCannons = BUILDER.comment("\n" +"----Brigg Max Cannons.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 6")
                .worldRestart()
                .defineInRange("BriggMaxCannons", 6, 0, 10);

        DhowHealth = BUILDER.comment("\n" +"----Dhow Health.----" + "\n" +
                        "\t" + "(takes effect after restart)" + "\n" +
                        "\t" + "(vanilla boat value = 60)" + "\n" +
                        "\t" + "default: 400")
                .worldRestart()
                .defineInRange("DhowHealth", 400.0, 0.0, 10000.0);

        DhowSpeedFactor = BUILDER.comment("\n" +"----Dhow Speed Factor.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 4.0" )
                .worldRestart()
                .defineInRange("DhowSpeedFactor", 4.0, 0.0, 20.0);

        DhowTurnFactor = BUILDER.comment("\n" +"----Dhow Turn Factor.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.3")
                .worldRestart()
                .defineInRange("DhowTurnFactor", 0.3, 0.0, 1.0);

        DhowMaxReverseSpeed = BUILDER.comment("\n" +"----Dhow Max Reverse Speed.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.8")
                .worldRestart()
                .defineInRange("DhowMaxReverseSpeed", 0.8, 0.0, 20.0);

        DhowAcceleration = BUILDER.comment("\n" +"----Dhow Acceleration.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.025")
                .worldRestart()
                .defineInRange("DhowAcceleration", 0.025, 0.0, 5.0);

        DhowRotationAcceleration = BUILDER.comment("\n" +"----Dhow Rotation Acceleration.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.105")
                .worldRestart()
                .defineInRange("DhowRotationAcceleration", 0.105, 0.0, 5.0);

        DhowVelocityResistance = BUILDER.comment("\n" +"----Dhow Velocity Resistance.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 0.0085")
                .worldRestart()
                .defineInRange("DhowVelocityResistance", 0.0085, 0.0, 1.0);

        DhowInventorySize = BUILDER.comment("\n" +"----Dhow Inventory Size.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 27")
                .worldRestart()
                .defineInRange("DhowInventorySize", 27, 0, 108);

        DhowInventoryPages = BUILDER.comment("\n" +"----Dhow Inventory Pages.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 1")
                .worldRestart()
                .defineInRange("DhowInventoryPages", 1, 1, 2);

        DhowMaxCannons = BUILDER.comment("\n" +"----Dhow Max Cannons.----" + "\n" +
                "\t" + "(takes effect after restart)" + "\n" +
                "\t" + "default: 4")
                .worldRestart()
                .defineInRange("DhowMaxCannons", 4, 0, 6);

        CONFIG = BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        configData.load();
        spec.setConfig(configData);
        if (VERSION.get() != NEW_VERSION) {
            configData = CommentedFileConfig.builder(path)
                    .sync()
                    .autosave()
                    .writingMode(WritingMode.REPLACE)
                    .build();
            spec.setConfig(configData);
            VERSION.set(NEW_VERSION);
            configData.save();
        }
    }
}
