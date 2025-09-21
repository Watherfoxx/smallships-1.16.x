package com.talhanation.smallships.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.talhanation.smallships.entities.AbstractBannerUser;
import com.talhanation.smallships.entities.AbstractSailShip;
import net.minecraft.client.renderer.entity.model.ModelRenderer;

public class ModelDhowSail extends ModelSail {

    private final ModelDhow model = new ModelDhow();

    @Override
    public void setupAnim(AbstractSailShip entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        model.setupAnim((AbstractBannerUser) entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        matrixStack.pushPose();
        model.Dhow.translateAndRotate(matrixStack);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.mast_1, model.untermast_1, model.Sail_z0_1);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.mast_2, model.untermast_2, model.Sail_z0_2);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.mast_1, model.segel_z1_1);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.mast_2, model.segel_z1_2);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.mast_1, model.segel_z2_1);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.mast_2, model.segel_z2_2);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.mast_1, model.segel_z3_1);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.mast_2, model.segel_z3_2);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.mast_1, model.segel_z4_1);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.mast_2, model.segel_z4_2);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.seil_0);
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
