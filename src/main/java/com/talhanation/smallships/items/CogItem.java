package com.talhanation.smallships.items;

import com.talhanation.smallships.entities.AbstractSailShip;
import com.talhanation.smallships.entities.CogEntity;
import net.minecraft.item.Item;

public class CogItem extends ShipItem<CogEntity> {

    public CogItem(AbstractSailShip.Type typeIn, Item.Properties properties) {
        super(typeIn, properties, (world, x, y, z) -> new CogEntity(world, x, y, z));
    }

    @Override
    protected void onShipCreated(CogEntity ship) {
        super.onShipCreated(ship);
        ship.setSailState(4);
    }
}