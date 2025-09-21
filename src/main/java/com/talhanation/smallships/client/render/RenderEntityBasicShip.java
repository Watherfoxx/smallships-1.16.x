package com.talhanation.smallships.client.render;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.client.model.ModelCog;
import com.talhanation.smallships.client.model.ModelCogSail;
import com.talhanation.smallships.entities.AbstractBasicShip;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class RenderEntityBasicShip<T extends AbstractBasicShip> extends AbstractShipRenderer<T> {

    private static final ResourceLocation[] COG_TEXTURES = new ResourceLocation[]{
            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/oak_cog.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/spruce_cog.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/birch_cog.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/jungle_cog.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/acacia_cog.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/dark_oak_cog.png")
    };

    public RenderEntityBasicShip(EntityRendererManager manager) {
        super(manager, new ModelCog(), ModelCogSail::new);
        this.shadowRadius = 1.0F;
    }

    @Override
    protected double getRenderYOffset(T entity) {
        return 0.4D * getSizeRatio(entity);
    }

    @Override
    protected float getModelScale(T entity) {
        return 1.3F * getSizeRatio(entity);
    }

    @Override
    protected Vector3d getModelTranslation(T entity) {
        float ratio = getSizeRatio(entity);
        return new Vector3d(0.0D, -1.8D * ratio, -1.0D * ratio);
    }

    @Override
    protected float getModelYawOffset(T entity) {
        return -90F;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return COG_TEXTURES[entity.getWoodType().ordinal()];
    }

    private float getSizeRatio(T entity) {
        float ratio = (float) (entity.getWidth() / 3.0D);
        return MathHelper.clamp(ratio, 0.6F, 1.2F);
    }
}
