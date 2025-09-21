package com.talhanation.smallships.client.events;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.client.model.ModelCogSail;
import com.talhanation.smallships.client.model.ModelDhow;
import com.talhanation.smallships.client.model.ModelDrakkar;
import com.talhanation.smallships.client.model.ModelRowBoat;
import com.talhanation.smallships.client.render.RenderCannonBall;
import com.talhanation.smallships.client.render.RenderEntityBasicShip;
import com.talhanation.smallships.client.render.RenderEntityBrigg;
import com.talhanation.smallships.client.render.RenderEntityCog;
import com.talhanation.smallships.client.render.RenderEntityGalley;
import com.talhanation.smallships.client.render.RenderEntityWarGalley;
import com.talhanation.smallships.init.ModEntityTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD , value = Dist.CLIENT)
public class ClientRenderEvent {

    @SubscribeEvent
    public static void clientsetup(FMLClientSetupEvent event){
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.COG.get(), RenderEntityCog::new );
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BRIGG.get(), RenderEntityBrigg::new );
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ROWBOAT.get(), manager -> new RenderEntityBasicShip<>(manager, new ModelRowBoat(), ModelCogSail::new, 0.8F));
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.DHOW.get(), manager -> new RenderEntityBasicShip<>(manager, new ModelDhow(), ModelCogSail::new, 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.DRAKKAR.get(), manager -> new RenderEntityBasicShip<>(manager, new ModelDrakkar(), ModelCogSail::new, 1.1F));
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.GALLEY.get(), RenderEntityGalley::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.WAR_GALLEY.get(), RenderEntityWarGalley::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.CANNON_BALL.get(), RenderCannonBall::new );
    }
}
