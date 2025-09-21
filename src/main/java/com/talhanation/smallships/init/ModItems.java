package com.talhanation.smallships.init;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.entities.AbstractSailShip;
import com.talhanation.smallships.entities.DhowEntity;
import com.talhanation.smallships.entities.DrakkarEntity;
import com.talhanation.smallships.entities.GalleyEntity;
import com.talhanation.smallships.entities.RowBoatEntity;
import com.talhanation.smallships.entities.WarGalleyEntity;
import com.talhanation.smallships.items.BriggItem;
import com.talhanation.smallships.items.CogItem;
import com.talhanation.smallships.items.ShipItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

    public static final RegistryObject<Item> SAIL_ITEM = ITEMS.register("sail_item", () -> new Item(new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_TRANSPORTATION)));
    public static final RegistryObject<Item> CANNON_ITEM = ITEMS.register("cannon_item", () -> new Item(new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_TRANSPORTATION)));
    public static final RegistryObject<Item> CANNONBALL = ITEMS.register("cannonball_item", () -> new Item(new Item.Properties().stacksTo(16).tab(ItemGroup.TAB_TRANSPORTATION)));
    public static final RegistryObject<Item> BROKEN_COG_HULL = ITEMS.register("broken_cog_hull", () -> new Item(new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_TRANSPORTATION)));
    public static final RegistryObject<Item> BROKEN_BRIGG_HULL = ITEMS.register("broken_brigg_hull", () -> new Item(new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_TRANSPORTATION)));

    // Cogs
    public static final RegistryObject<Item> OAK_COG_ITEM = ITEMS.register("oak_cog", () -> new CogItem(AbstractSailShip.Type.OAK, defaultShipProperties()));
    public static final RegistryObject<Item> SPRUCE_COG_ITEM = ITEMS.register("spruce_cog", () -> new CogItem(AbstractSailShip.Type.SPRUCE, defaultShipProperties()));
    public static final RegistryObject<Item> BIRCH_COG_ITEM = ITEMS.register("birch_cog", () -> new CogItem(AbstractSailShip.Type.BIRCH, defaultShipProperties()));
    public static final RegistryObject<Item> JUNGLE_COG_ITEM = ITEMS.register("jungle_cog", () -> new CogItem(AbstractSailShip.Type.JUNGLE, defaultShipProperties()));
    public static final RegistryObject<Item> ACACIA_COG_ITEM = ITEMS.register("acacia_cog", () -> new CogItem(AbstractSailShip.Type.ACACIA, defaultShipProperties()));
    public static final RegistryObject<Item> DARK_OAK_COG_ITEM = ITEMS.register("dark_oak_cog", () -> new CogItem(AbstractSailShip.Type.DARK_OAK, defaultShipProperties()));

    // Brigg
    public static final RegistryObject<Item> OAK_BRIGG_ITEM = ITEMS.register("oak_brigg", () -> new BriggItem(AbstractSailShip.Type.OAK, defaultShipProperties()));
    public static final RegistryObject<Item> SPRUCE_BRIGG_ITEM = ITEMS.register("spruce_brigg", () -> new BriggItem(AbstractSailShip.Type.SPRUCE, defaultShipProperties()));
    public static final RegistryObject<Item> BIRCH_BRIGG_ITEM = ITEMS.register("birch_brigg", () -> new BriggItem(AbstractSailShip.Type.BIRCH, defaultShipProperties()));
    public static final RegistryObject<Item> JUNGLE_BRIGG_ITEM = ITEMS.register("jungle_brigg", () -> new BriggItem(AbstractSailShip.Type.JUNGLE, defaultShipProperties()));
    public static final RegistryObject<Item> ACACIA_BRIGG_ITEM = ITEMS.register("acacia_brigg", () -> new BriggItem(AbstractSailShip.Type.ACACIA, defaultShipProperties()));
    public static final RegistryObject<Item> DARK_OAK_BRIGG_ITEM = ITEMS.register("dark_oak_brigg", () -> new BriggItem(AbstractSailShip.Type.DARK_OAK, defaultShipProperties()));

    // Rowboats
    public static final RegistryObject<Item> OAK_ROWBOAT_ITEM = registerShip("oak_rowboat", AbstractSailShip.Type.OAK, RowBoatEntity::new);
    public static final RegistryObject<Item> SPRUCE_ROWBOAT_ITEM = registerShip("spruce_rowboat", AbstractSailShip.Type.SPRUCE, RowBoatEntity::new);
    public static final RegistryObject<Item> BIRCH_ROWBOAT_ITEM = registerShip("birch_rowboat", AbstractSailShip.Type.BIRCH, RowBoatEntity::new);
    public static final RegistryObject<Item> JUNGLE_ROWBOAT_ITEM = registerShip("jungle_rowboat", AbstractSailShip.Type.JUNGLE, RowBoatEntity::new);
    public static final RegistryObject<Item> ACACIA_ROWBOAT_ITEM = registerShip("acacia_rowboat", AbstractSailShip.Type.ACACIA, RowBoatEntity::new);
    public static final RegistryObject<Item> DARK_OAK_ROWBOAT_ITEM = registerShip("dark_oak_rowboat", AbstractSailShip.Type.DARK_OAK, RowBoatEntity::new);

    // Dhows
    public static final RegistryObject<Item> OAK_DHOW_ITEM = registerShip("oak_dhow", AbstractSailShip.Type.OAK, DhowEntity::new);
    public static final RegistryObject<Item> SPRUCE_DHOW_ITEM = registerShip("spruce_dhow", AbstractSailShip.Type.SPRUCE, DhowEntity::new);
    public static final RegistryObject<Item> BIRCH_DHOW_ITEM = registerShip("birch_dhow", AbstractSailShip.Type.BIRCH, DhowEntity::new);
    public static final RegistryObject<Item> JUNGLE_DHOW_ITEM = registerShip("jungle_dhow", AbstractSailShip.Type.JUNGLE, DhowEntity::new);
    public static final RegistryObject<Item> ACACIA_DHOW_ITEM = registerShip("acacia_dhow", AbstractSailShip.Type.ACACIA, DhowEntity::new);
    public static final RegistryObject<Item> DARK_OAK_DHOW_ITEM = registerShip("dark_oak_dhow", AbstractSailShip.Type.DARK_OAK, DhowEntity::new);

    // Drakkars
    public static final RegistryObject<Item> OAK_DRAKKAR_ITEM = registerShip("oak_drakkar", AbstractSailShip.Type.OAK, DrakkarEntity::new);
    public static final RegistryObject<Item> SPRUCE_DRAKKAR_ITEM = registerShip("spruce_drakkar", AbstractSailShip.Type.SPRUCE, DrakkarEntity::new);
    public static final RegistryObject<Item> BIRCH_DRAKKAR_ITEM = registerShip("birch_drakkar", AbstractSailShip.Type.BIRCH, DrakkarEntity::new);
    public static final RegistryObject<Item> JUNGLE_DRAKKAR_ITEM = registerShip("jungle_drakkar", AbstractSailShip.Type.JUNGLE, DrakkarEntity::new);
    public static final RegistryObject<Item> ACACIA_DRAKKAR_ITEM = registerShip("acacia_drakkar", AbstractSailShip.Type.ACACIA, DrakkarEntity::new);
    public static final RegistryObject<Item> DARK_OAK_DRAKKAR_ITEM = registerShip("dark_oak_drakkar", AbstractSailShip.Type.DARK_OAK, DrakkarEntity::new);

    // Galleys
    public static final RegistryObject<Item> OAK_GALLEY_ITEM = registerShip("oak_galley", AbstractSailShip.Type.OAK, GalleyEntity::new);
    public static final RegistryObject<Item> SPRUCE_GALLEY_ITEM = registerShip("spruce_galley", AbstractSailShip.Type.SPRUCE, GalleyEntity::new);
    public static final RegistryObject<Item> BIRCH_GALLEY_ITEM = registerShip("birch_galley", AbstractSailShip.Type.BIRCH, GalleyEntity::new);
    public static final RegistryObject<Item> JUNGLE_GALLEY_ITEM = registerShip("jungle_galley", AbstractSailShip.Type.JUNGLE, GalleyEntity::new);
    public static final RegistryObject<Item> ACACIA_GALLEY_ITEM = registerShip("acacia_galley", AbstractSailShip.Type.ACACIA, GalleyEntity::new);
    public static final RegistryObject<Item> DARK_OAK_GALLEY_ITEM = registerShip("dark_oak_galley", AbstractSailShip.Type.DARK_OAK, GalleyEntity::new);

    // War Galleys
    public static final RegistryObject<Item> OAK_WAR_GALLEY_ITEM = registerShip("oak_war_galley", AbstractSailShip.Type.OAK, WarGalleyEntity::new);
    public static final RegistryObject<Item> SPRUCE_WAR_GALLEY_ITEM = registerShip("spruce_war_galley", AbstractSailShip.Type.SPRUCE, WarGalleyEntity::new);
    public static final RegistryObject<Item> BIRCH_WAR_GALLEY_ITEM = registerShip("birch_war_galley", AbstractSailShip.Type.BIRCH, WarGalleyEntity::new);
    public static final RegistryObject<Item> JUNGLE_WAR_GALLEY_ITEM = registerShip("jungle_war_galley", AbstractSailShip.Type.JUNGLE, WarGalleyEntity::new);
    public static final RegistryObject<Item> ACACIA_WAR_GALLEY_ITEM = registerShip("acacia_war_galley", AbstractSailShip.Type.ACACIA, WarGalleyEntity::new);
    public static final RegistryObject<Item> DARK_OAK_WAR_GALLEY_ITEM = registerShip("dark_oak_war_galley", AbstractSailShip.Type.DARK_OAK, WarGalleyEntity::new);

    private static Item.Properties defaultShipProperties() {
        return new Item.Properties().stacksTo(1).durability(100).tab(ItemGroup.TAB_TRANSPORTATION);
    }

    private static <T extends AbstractSailShip> RegistryObject<Item> registerShip(String name, AbstractSailShip.Type type, ShipItem.ShipFactory<T> factory) {
        return ITEMS.register(name, () -> new ShipItem<>(type, defaultShipProperties(), factory));
    }
}
