package com.talhanation.smallships.items;

import com.talhanation.smallships.client.render.RenderItemBrigg;
import com.talhanation.smallships.entities.AbstractSailShip;
import com.talhanation.smallships.entities.BriggEntity;
import net.minecraft.world.World;

public class BriggItem extends ShipItem<BriggEntity> {

    public BriggItem(AbstractSailShip.Type typeIn, Properties properties) {
        super(typeIn, properties.setISTER(() -> RenderItemBrigg::new), (world, x, y, z) -> new BriggEntity(world, x, y, z));
    }

    @Override
    protected void onShipCreated(BriggEntity ship) {
        super.onShipCreated(ship);
        ship.setSailState(4);
    }

    public BriggEntity getBriggEntity(World world) {
        return createShipForRender(world);
    }
}