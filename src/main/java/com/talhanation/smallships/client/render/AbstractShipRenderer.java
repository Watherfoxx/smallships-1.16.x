package com.talhanation.smallships.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.talhanation.smallships.client.model.ModelSail;
import com.talhanation.smallships.config.SmallShipsConfig;
import com.talhanation.smallships.entities.AbstractSailShip;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import java.util.function.Supplier;

public abstract class AbstractShipRenderer<T extends AbstractSailShip> extends EntityRenderer<T> {

    private final EntityModel<AbstractSailShip> hullModel;
    private final Supplier<ModelSail> sailModelSupplier;

    protected AbstractShipRenderer(EntityRendererManager renderManager, EntityModel<AbstractSailShip> hullModel, Supplier<ModelSail> sailModelSupplier) {
        super(renderManager);
        this.hullModel = hullModel;
        this.sailModelSupplier = sailModelSupplier;
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        matrixStack.pushPose();
        matrixStack.translate(0.0D, getRenderYOffset(entity), 0.0D);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - entityYaw));

        if (SmallShipsConfig.MakeWaveAnimation.get()) {
            float waveAngle = entity.getWaveAngle(partialTicks);
            if (!MathHelper.equal(waveAngle, 0F)) {
                matrixStack.mulPose(Vector3f.ZP.rotationDegrees(waveAngle));
            }
        }

        float scale = getModelScale(entity);
        matrixStack.scale(-scale, -scale, scale);

        Vector3d translation = getModelTranslation(entity);
        matrixStack.translate(translation.x, translation.y, translation.z);

        float yawOffset = getModelYawOffset(entity);
        if (!MathHelper.equal(yawOffset, 0F)) {
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(yawOffset));
        }

        hullModel.setupAnim(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        IVertexBuilder vertexBuilder = buffer.getBuffer(hullModel.renderType(getTextureLocation(entity)));
        hullModel.renderToBuffer(matrixStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        entity.renderBanner(matrixStack, buffer, packedLight, partialTicks);

        if (sailModelSupplier != null) {
            ModelSail sail = sailModelSupplier.get();
            entity.renderSailColor(matrixStack, buffer, packedLight, partialTicks, sail);
        }

        renderAdditionalParts(entity, partialTicks, matrixStack, buffer, packedLight);
        matrixStack.popPose();
    }

    protected void renderAdditionalParts(T entity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
    }

    protected double getRenderYOffset(T entity) {
        return 0.0D;
    }

    protected float getModelScale(T entity) {
        return 1.0F;
    }

    protected Vector3d getModelTranslation(T entity) {
        return Vector3d.ZERO;
    }

    protected float getModelYawOffset(T entity) {
        return 0F;
    }
}
