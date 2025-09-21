package com.talhanation.smallships.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.talhanation.smallships.entities.AbstractBannerUser;
import com.talhanation.smallships.entities.AbstractSailShip;
import net.minecraft.client.renderer.entity.model.ModelRenderer;

public class ModelWarGalleySail extends ModelSail {

    private final ModelWarGalley model = new ModelWarGalley();

    @Override
    public void setupAnim(AbstractSailShip entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        model.setupAnim((AbstractBannerUser) entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        matrixStack.pushPose();
        model.botom_1.translateAndRotate(matrixStack);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.Mast_2, model.Mast_2_oben_1, model.Segel_1_z0);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.Mast_2, model.Mast_2_oben_1, model.Segel_1_z1);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.Mast_2, model.Mast_2_oben_1, model.Segel_1_z2);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.Mast_2, model.Mast_2_oben_1, model.Segel_1_z3);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.Mast_2, model.Mast_2_oben_1, model.Segel_1_z4);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.Mast_1, model.Mast_2_oben, model.Segel_2_z0);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.Mast_1, model.Mast_2_oben, model.Segel_2_z1);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.Mast_1, model.Mast_2_oben, model.Segel_2_z2);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.Mast_1, model.Mast_2_oben, model.Segel_2_z3);
        renderSail(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.Mast_1, model.Mast_2_oben, model.Segel_2_z4);
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
