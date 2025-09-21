package com.talhanation.smallships.client.render;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.client.model.ModelWarGalley;
import com.talhanation.smallships.entities.WarGalleyEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import com.mojang.blaze3d.matrix.MatrixStack;

public class RenderEntityWarGalley extends AbstractShipRenderer<WarGalleyEntity> {

    private static final ResourceLocation[] WAR_GALLEY_TEXTURES = new ResourceLocation[]{
            new ResourceLocation(Main.MOD_ID, "textures/entity/galley/oak_galley.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/galley/spruce_galley.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/galley/birch_galley.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/galley/jungle_galley.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/galley/acacia_galley.png"),
            new ResourceLocation(Main.MOD_ID, "textures/entity/galley/dark_oak_galley.png")
    };

    public RenderEntityWarGalley(EntityRendererManager manager) {
        super(manager, new ModelWarGalley(), null);
        this.shadowRadius = 1.5F;
    }

    @Override
    protected double getRenderYOffset(WarGalleyEntity entity) {
        return -0.1D;
    }

    @Override
    protected float getModelScale(WarGalleyEntity entity) {
        return 1.3F;
    }

    @Override
    protected Vector3d getModelTranslation(WarGalleyEntity entity) {
        return new Vector3d(0.0D, -1.8D, -1.0D);
    }

    @Override
    protected void renderAdditionalParts(WarGalleyEntity entity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        entity.renderCannon(-0.75D, -0.55D, -90F, matrixStack, buffer, packedLight, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(WarGalleyEntity entity) {
        return WAR_GALLEY_TEXTURES[entity.getWoodType().ordinal()];
    }
}
