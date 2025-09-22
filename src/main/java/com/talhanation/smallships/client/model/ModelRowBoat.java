package com.talhanation.smallships.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.talhanation.smallships.entities.AbstractBannerUser;
import com.talhanation.smallships.entities.RowBoatEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelRowBoat extends EntityModel<AbstractBannerUser> {
   public ModelRenderer RowBoat;
   public ModelRenderer Cargo_1;
   public ModelRenderer Cargo_0;
   public ModelRenderer ruder0_1;
   public ModelRenderer ruder_l;
   public ModelRenderer ruder_r;
   public ModelRenderer ruder0_2;

   private static final float RUDER0_1_BASE_X_ROT = 0.71279246F;
   private static final float RUDER0_1_BASE_Y_ROT = 0.2645919F;
   private static final float RUDER0_1_BASE_Z_ROT = 1.6029103F;
   private static final float RUDER0_2_BASE_X_ROT = 0.05235988F;
   private static final float RUDER0_2_BASE_Y_ROT = 0.12217305F;
   private static final float RUDER0_2_BASE_Z_ROT = -0.4537856F;

   private static final float PADDLE_MIN_X_ROT = -1.0471975803375244F;
   private static final float PADDLE_MAX_X_ROT = -0.2617993950843811F;
   private static final float PADDLE_MIN_Y_ROT = -0.7853981852531433F;
   private static final float PADDLE_MAX_Y_ROT = 0.7853981852531433F;

   private final ModelRenderer[] leftPaddles;
   private final ModelRenderer[] rightPaddles;

   public ModelRowBoat() {
      this.texWidth = 128;
      this.texHeight = 64;
      this.ruder_r = new ModelRenderer(this, 62, 20);
      this.ruder_r.setPos(10.0F, -9.0F, -9.0F);
      this.ruder_r.addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F, 0.0F, 0.0F, 0.0F);
      this.ruder_r.addBox(-0.2F, -3.0F, 12.0F, 1.0F, 6.0F, 7.0F, 0.0F, 0.0F, 0.0F);
      this.setRotateAngle(this.ruder_r, -0.65449846F, 2.4813344F, 0.19634955F);
      this.RowBoat = new ModelRenderer(this, 0, 0);
      this.RowBoat.setPos(0.0F, 16.0F, 14.0F);
      this.RowBoat.texOffs(0, 8).addBox(0.0F, 0.0F, -9.0F, 19.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(0, 43).addBox(0.0F, -6.0F, -11.0F, 14.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(0, 35).addBox(14.0F, -6.0F, -11.0F, 14.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(0, 43).addBox(14.0F, -6.0F, 9.0F, 14.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(0, 43).addBox(0.0F, -6.0F, 9.0F, 14.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(2, 42).addBox(-12.0F, -7.0F, -9.0F, 12.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(2, 42).addBox(-12.0F, -7.0F, 7.0F, 12.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(3, 15).addBox(40.0F, -8.0F, -7.0F, 2.0F, 8.0F, 14.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(2, 42).addBox(28.0F, -7.0F, 7.0F, 12.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(2, 42).addBox(28.0F, -7.0F, -9.0F, 12.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(3, 15).addBox(-14.0F, -8.0F, -7.0F, 2.0F, 8.0F, 14.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(0, 7).addBox(3.0F, 3.0F, -7.0F, 22.0F, 4.0F, 7.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(0, 1).addBox(25.0F, 3.0F, -4.5F, 11.0F, 3.0F, 9.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(0, 1).addBox(-7.0F, 3.0F, -4.5F, 11.0F, 3.0F, 9.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(10, 0).addBox(-17.0F, -12.0F, -2.0F, 3.0F, 10.0F, 4.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(10, 0).addBox(42.0F, -12.0F, -2.0F, 3.0F, 10.0F, 4.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(0, 8).addBox(0.0F, 0.0F, -3.0F, 19.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(0, 8).addBox(0.0F, 0.0F, 3.0F, 19.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(0, 8).addBox(19.0F, 0.0F, -9.0F, 21.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(0, 8).addBox(19.0F, 0.0F, -3.0F, 21.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(0, 8).addBox(19.0F, 0.0F, 3.0F, 21.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(0, 8).addBox(-12.0F, 0.0F, 3.0F, 12.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(0, 8).addBox(-12.0F, 0.0F, -3.0F, 12.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.texOffs(0, 8).addBox(-12.0F, 0.0F, -9.0F, 12.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.addBox(3.0F, 3.0F, 0.0F, 22.0F, 4.0F, 7.0F, 0.0F, 0.0F, 0.0F);
      this.setRotateAngle(this.RowBoat, -0.0F, 1.5707964F, 0.0F);
      this.Cargo_1 = new ModelRenderer(this, 0, 0);
      this.Cargo_1.setPos(31.2F, -8.0F, -6.0F);
      this.Cargo_1.texOffs(96, 38).addBox(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F);
      this.Cargo_1.texOffs(30, 55).addBox(-3.2F, 3.0F, 0.0F, 3.0F, 5.0F, 4.0F, 0.0F, 0.0F, 0.0F);
      this.Cargo_1.texOffs(31, 56).addBox(4.4F, 3.0F, 8.0F, 3.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
      this.Cargo_1.addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
      this.ruder0_2 = new ModelRenderer(this, 0, 0);
      this.ruder0_2.setPos(0.0F, 7.0F, -7.0F);
      this.ruder0_2.texOffs(62, 0).addBox(-1.0F, 0.0F, -8.0F, 2.0F, 2.0F, 18.0F, 0.0F, 0.0F, 0.0F);
      this.ruder0_2.texOffs(62, 0).addBox(-0.8F, -3.0F, 15.0F, 1.0F, 6.0F, 7.0F, 0.0F, 0.0F, 0.0F);
      this.ruder0_2.texOffs(74, 32).addBox(-1.0F, 0.0F, 10.0F, 2.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
      this.setRotateAngle(this.ruder0_2, 0.05235988F, 0.12217305F, -0.4537856F);
      this.Cargo_0 = new ModelRenderer(this, 0, 0);
      this.Cargo_0.mirror = true;
      this.Cargo_0.setPos(-11.2F, -16.8F, 2.0F);
      this.Cargo_0.texOffs(96, 38).addBox(0.0F, 9.0F, 0.0F, 8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F);
      this.Cargo_0.texOffs(31, 56).addBox(-3.0F, 13.0F, 1.0F, 3.0F, 5.0F, 3.0F, 0.0F, -1.0F, 0.0F);
      this.Cargo_0.texOffs(31, 56).addBox(1.0F, 12.0F, 8.3F, 3.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
      this.setRotateAngle(this.Cargo_0, 0.0F, 1.5707964F, 0.0F);
      this.ruder0_1 = new ModelRenderer(this, 0, 0);
      this.ruder0_1.setPos(12.0F, -7.0F, -9.0F);
      this.ruder0_1.texOffs(62, 0).addBox(-1.0F, 0.0F, -8.0F, 2.0F, 2.0F, 18.0F, 0.0F, 0.0F, 0.0F);
      this.ruder0_1.texOffs(62, 20).addBox(-0.2F, -3.0F, 15.0F, 1.0F, 6.0F, 7.0F, 0.0F, 0.0F, 0.0F);
      this.ruder0_1.texOffs(74, 32).addBox(-1.0F, 0.0F, 10.0F, 2.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
      this.setRotateAngle(this.ruder0_1, 0.71279246F, 0.2645919F, 1.6029103F);
      this.ruder_l = new ModelRenderer(this, 62, 0);
      this.ruder_l.setPos(10.0F, -9.0F, 9.0F);
      this.ruder_l.addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F, 0.0F, 0.0F, 0.0F);
      this.ruder_l.addBox(-0.8F, -3.0F, 12.0F, 1.0F, 6.0F, 7.0F, 0.0F, 0.0F, 0.0F);
      this.setRotateAngle(this.ruder_l, -0.65449846F, 0.6602581F, 0.19634955F);
      this.RowBoat.addChild(this.ruder_r);
      this.RowBoat.addChild(this.Cargo_1);
      this.ruder0_1.addChild(this.ruder0_2);
      this.RowBoat.addChild(this.Cargo_0);
      this.RowBoat.addChild(this.ruder0_1);
      this.RowBoat.addChild(this.ruder_l);

      this.rightPaddles = new ModelRenderer[]{this.ruder_r};
      this.leftPaddles = new ModelRenderer[]{this.ruder_l};
   }

   @Override
   public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
      ImmutableList.of(this.RowBoat).forEach((modelRenderer) -> {
         modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
      });
   }

   @Override
   public void setupAnim(AbstractBannerUser entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (!(entityIn instanceof RowBoatEntity)) {
         return;
      }

      RowBoatEntity rowBoat = (RowBoatEntity) entityIn;

      float steer = -rowBoat.getRotSpeed();

      this.RowBoat.xRot = 0.0F;
      this.RowBoat.yRot = 1.5707964F;
      this.RowBoat.zRot = 0.0F;

      this.ruder0_1.xRot = RUDER0_1_BASE_X_ROT;
      this.ruder0_1.yRot = RUDER0_1_BASE_Y_ROT + steer;
      this.ruder0_1.zRot = RUDER0_1_BASE_Z_ROT;
      this.ruder0_2.xRot = RUDER0_2_BASE_X_ROT;
      this.ruder0_2.yRot = RUDER0_2_BASE_Y_ROT;
      this.ruder0_2.zRot = RUDER0_2_BASE_Z_ROT;

      if (!rowBoat.isPassenger()) {
         this.ruder0_1.visible = true;
         this.ruder0_2.visible = true;
         this.ruder_r.visible = false;
         this.ruder_l.visible = false;
      } else {
         this.ruder0_1.visible = false;
         this.ruder0_2.visible = false;
         this.ruder_r.visible = true;
         this.ruder_l.visible = true;
      }

      int cargo = rowBoat.getCargo();
      this.Cargo_1.visible = cargo >= 1;
      this.Cargo_0.visible = cargo >= 2;
      animatePaddles(rowBoat, 0, limbSwing, this.leftPaddles, false);
      animatePaddles(rowBoat, 1, limbSwing, this.rightPaddles, true);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.xRot = x;
      modelRenderer.yRot = y;
      modelRenderer.zRot = z;
   }

   private void animatePaddles(RowBoatEntity galleyEntity, int side, float limbSwing, ModelRenderer[] paddles, boolean rightSide) {
      float rowingTime = galleyEntity.getRowingTime(side, limbSwing);
      float steer = -galleyEntity.getRotSpeed();

      for (ModelRenderer paddle : paddles) {
         setPaddleRotation(paddle, rowingTime, steer, rightSide);
      }
   }

   private static void setPaddleRotation(ModelRenderer paddle, float rowingTime, float steer, boolean rightSide) {
      float xRot = (float)MathHelper.clamp(PADDLE_MIN_X_ROT, PADDLE_MAX_X_ROT, (double)((MathHelper.sin(-rowingTime) + 1.0F) / 2.0F));
      float yawOffset = (float)MathHelper.clamp(PADDLE_MIN_Y_ROT, PADDLE_MAX_Y_ROT, (double)((MathHelper.sin(-rowingTime + 1.0F) + 1.0F) / 2.0F));
      if (rightSide) {
         paddle.xRot = xRot;
         paddle.yRot = (float)Math.PI - yawOffset + steer;
      } else {
         paddle.xRot = xRot;
         paddle.yRot = yawOffset - steer;
      }
   }
}
