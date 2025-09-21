package com.talhanation.smallships.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.talhanation.smallships.entities.AbstractBannerUser;
import com.talhanation.smallships.entities.AbstractSailShip;
import net.minecraft.client.renderer.entity.model.ModelRenderer;

public class ModelDrakkarSail extends ModelSail {

    private final ModelDrakkar model = new ModelDrakkar();

    @Override
    public void setupAnim(AbstractSailShip entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        model.setupAnim((AbstractBannerUser) entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        matrixStack.pushPose();
        model.botom_1.translateAndRotate(matrixStack);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.Mast_1, model.Sail_z0);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.Mast_1, model.Sail_z1);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.Mast_1, model.Sail_z2);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.Mast_1, model.Sail_z3);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.Mast_1, model.Sail_z4);
        matrixStack.popPose();
    }

    private void renderSail(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha, ModelRenderer... parts) {
        matrixStack.pushPose();
        for (int i = 0; i < parts.length - 1; i++) {
            parts[i].translateAndRotate(matrixStack);
        }
        ModelRenderer sailPart = parts[parts.length - 1];
        if (sailPart.visible) {
            sailPart.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        }
        matrixStack.popPose();
    }
}
