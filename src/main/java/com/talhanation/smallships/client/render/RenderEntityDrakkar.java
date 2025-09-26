package com.talhanation.smallships.client.render;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.client.model.ModelDrakkar;
import com.talhanation.smallships.client.model.ModelDrakkarSail;
import com.talhanation.smallships.entities.DrakkarEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class RenderEntityDrakkar extends AbstractShipRenderer<DrakkarEntity> {

    private static final ResourceLocation[] DRAKKAR_TEXTURES = new ResourceLocation[]{
            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/oak_cog.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/spruce_cog.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/birch_cog.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/jungle_cog.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/acacia_cog.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/cog/dark_oak_cog.png")
    };

    public RenderEntityDrakkar(EntityRendererManager manager) {
        super(manager, new ModelDrakkar(), ModelDrakkarSail::new);
        this.shadowRadius = 1.1F;
    }

    @Override
    protected double getRenderYOffset(DrakkarEntity entity) {
        return 0.4D * getSizeRatio(entity);
    }

    @Override
    protected float getModelScale(DrakkarEntity entity) {
        return 1.3F * getSizeRatio(entity);
    }

    @Override
    protected Vector3d getModelTranslation(DrakkarEntity entity) {
        float ratio = getSizeRatio(entity);
        return new Vector3d(0.0D, -1.8D * ratio, -1.0D * ratio);
    }

    @Override
    protected float getModelYawOffset(DrakkarEntity entity) {
        return 0F;
    }

    @Override
    public ResourceLocation getTextureLocation(DrakkarEntity entity) {
        return DRAKKAR_TEXTURES[entity.getWoodType().ordinal()];
    }

    private float getSizeRatio(DrakkarEntity entity) {
        float ratio = (float) (entity.getWidth() / 3.0D);
        return MathHelper.clamp(ratio, 0.6F, 1.2F);
    }
}
