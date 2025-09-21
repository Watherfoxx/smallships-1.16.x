package com.talhanation.smallships.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.talhanation.smallships.entities.AbstractBannerUser;
import com.talhanation.smallships.entities.AbstractSailShip;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ModelDrakkarSail extends ModelSail {

    private final ModelDrakkar model;
    private final List<ModelRenderer> sailStates;

    public ModelDrakkarSail() {
        this.model = new ModelDrakkar();
        this.sailStates = ImmutableList.of(
                model.Sail_z0,
                model.Sail_z1,
                model.Sail_z2,
                model.Sail_z3,
                model.Sail_z4
        );
    }

    @Override
    public void setupAnim(AbstractSailShip entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity instanceof AbstractBannerUser) {
            model.setupAnim((AbstractBannerUser) entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        }
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        matrixStack.pushPose();
        model.botom_1.translateAndRotate(matrixStack);
        model.Mast_1.translateAndRotate(matrixStack);
        for (ModelRenderer sail : sailStates) {
            if (sail.visible) {
                sail.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            }
        }
        matrixStack.popPose();
    }
}
