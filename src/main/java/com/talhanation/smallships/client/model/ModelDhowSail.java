package com.talhanation.smallships.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.talhanation.smallships.entities.AbstractBannerUser;
import com.talhanation.smallships.entities.AbstractSailShip;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelDhowSail extends ModelSail {

    private final ModelDhow model;

    public ModelDhowSail() {
        this.model = new ModelDhow();
    }

    @Override
    public void setupAnim(AbstractSailShip entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity instanceof AbstractBannerUser) {
            model.setupAnim((AbstractBannerUser) entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        }
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        renderPart(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.seil_0, model.Dhow);

        renderPart(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.Sail_z0_1, model.Dhow, model.mast_1, model.untermast_1);
        renderPart(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.segel_z1_1, model.Dhow, model.mast_1);
        renderPart(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.segel_z2_1, model.Dhow, model.mast_1);
        renderPart(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.segel_z3_1, model.Dhow, model.mast_1);
        renderPart(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.segel_z4_1, model.Dhow, model.mast_1);

        renderPart(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.Sail_z0_2, model.Dhow, model.mast_2, model.untermast_2);
        renderPart(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.segel_z1_2, model.Dhow, model.mast_2);
        renderPart(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.segel_z2_2, model.Dhow, model.mast_2);
        renderPart(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.segel_z3_2, model.Dhow, model.mast_2);
        renderPart(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha, model.segel_z4_2, model.Dhow, model.mast_2);
    }

    private void renderPart(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha, ModelRenderer part, ModelRenderer... parents) {
        if (!part.visible) {
            return;
        }
        matrixStack.pushPose();
        for (ModelRenderer parent : parents) {
            parent.translateAndRotate(matrixStack);
        }
        part.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        matrixStack.popPose();
    }
}
