package com.talhanation.smallships.items;

import com.talhanation.smallships.entities.AbstractSailShip;
import com.talhanation.smallships.entities.BriggEntity;

public class BriggItem extends ShipItem<BriggEntity> {

    public BriggItem(AbstractSailShip.Type typeIn, Properties properties) {
        super(typeIn, properties, (world, x, y, z) -> new BriggEntity(world, x, y, z));
    }

    @Override
    protected void onShipCreated(BriggEntity ship) {
        super.onShipCreated(ship);
        ship.setSailState(4);
    }
}