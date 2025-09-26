package com.talhanation.smallships.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.talhanation.smallships.Main;
import com.talhanation.smallships.client.model.ModelGalley;
import com.talhanation.smallships.client.model.ModelGalleySail;
import com.talhanation.smallships.entities.GalleyEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class RenderEntityGalley extends AbstractShipRenderer<GalleyEntity> {

    private static final ResourceLocation[] GALLEY_TEXTURES = new ResourceLocation[]{
            new ResourceLocation(Main.MOD_ID, "textures/entity/galley/oak_galley.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/galley/spruce_galley.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/galley/birch_galley.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/galley/jungle_galley.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/galley/acacia_galley.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/galley/dark_oak_galley.png")
    };

    public RenderEntityGalley(EntityRendererManager manager) {
        super(manager, new ModelGalley(), ModelGalleySail::new);
        this.shadowRadius = 1.4F;
    }

    @Override
    protected double getRenderYOffset(GalleyEntity entity) {
        return -0.15D;
    }

    @Override
    protected float getModelScale(GalleyEntity entity) {
        return 1.25F;
    }

    @Override
    protected Vector3d getModelTranslation(GalleyEntity entity) {
        return new Vector3d(0.0D, -1.2D, -0.95D);
    }

    @Override
    protected float getModelYawOffset(GalleyEntity entity) {
        return 0F;
    }

    @Override
    protected void renderAdditionalParts(GalleyEntity entity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        entity.renderCannons(matrixStack, buffer, packedLight, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(GalleyEntity entity) {
        return GALLEY_TEXTURES[entity.getWoodType().ordinal()];
    }
}
