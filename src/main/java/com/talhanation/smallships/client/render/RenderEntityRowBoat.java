package com.talhanation.smallships.client.render;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.client.model.ModelRowBoat;
import com.talhanation.smallships.entities.RowBoatEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class RenderEntityRowBoat extends AbstractShipRenderer<RowBoatEntity> {

    private static final ResourceLocation[] ROWBOAT_TEXTURES = new ResourceLocation[]{
            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/oak_cog.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/spruce_cog.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/birch_cog.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/jungle_cog.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/acacia_cog.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/dark_oak_cog.png")
    };

    public RenderEntityRowBoat(EntityRendererManager manager) {
        super(manager, new ModelRowBoat(), null);
        this.shadowRadius = 0.8F;
    }

    @Override
    protected double getRenderYOffset(RowBoatEntity entity) {
        return 0.4D * getSizeRatio(entity);
    }

    @Override
    protected float getModelScale(RowBoatEntity entity) {
        return 1.3F * getSizeRatio(entity);
    }

    @Override
    protected Vector3d getModelTranslation(RowBoatEntity entity) {
        float ratio = getSizeRatio(entity);
        return new Vector3d(0.0D, -1.8D * ratio, -1.0D * ratio);
    }

    @Override
    protected float getModelYawOffset(RowBoatEntity entity) {
        return -90F;
    }

    @Override
    public ResourceLocation getTextureLocation(RowBoatEntity entity) {
        return ROWBOAT_TEXTURES[entity.getWoodType().ordinal()];
    }

    private float getSizeRatio(RowBoatEntity entity) {
        float ratio = (float) (entity.getWidth() / 3.0D);
        return MathHelper.clamp(ratio, 0.6F, 1.2F);
    }
}
