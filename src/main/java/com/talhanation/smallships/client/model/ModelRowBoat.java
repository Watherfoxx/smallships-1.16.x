package com.talhanation.smallships.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.talhanation.smallships.entities.RowBoatEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelRowBoat<T extends RowBoatEntity> extends EntityModel<T> {
   public ModelRenderer RowBoat;
   public ModelRenderer Cargo_1;
   public ModelRenderer Cargo_0;
   public ModelRenderer ruder0_1;
   public ModelRenderer ruder_l;
   public ModelRenderer ruder_r;
   public ModelRenderer ruder0_2;

   public ModelRowBoat() {
      this.field_78090_t = 128;
      this.field_78089_u = 64;
      this.ruder_r = new ModelRenderer(this, 62, 20);
      this.ruder_r.func_78793_a(10.0F, -9.0F, -9.0F);
      this.ruder_r.func_228302_a_(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F, 0.0F, 0.0F, 0.0F);
      this.ruder_r.func_228302_a_(-0.2F, -3.0F, 12.0F, 1.0F, 6.0F, 7.0F, 0.0F, 0.0F, 0.0F);
      this.setRotateAngle(this.ruder_r, -0.65449846F, 2.4813344F, 0.19634955F);
      this.RowBoat = new ModelRenderer(this, 0, 0);
      this.RowBoat.func_78793_a(0.0F, 16.0F, 14.0F);
      this.RowBoat.func_78784_a(0, 8).func_228302_a_(0.0F, 0.0F, -9.0F, 19.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(0, 43).func_228302_a_(0.0F, -6.0F, -11.0F, 14.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(0, 35).func_228302_a_(14.0F, -6.0F, -11.0F, 14.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(0, 43).func_228302_a_(14.0F, -6.0F, 9.0F, 14.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(0, 43).func_228302_a_(0.0F, -6.0F, 9.0F, 14.0F, 6.0F, 2.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(2, 42).func_228302_a_(-12.0F, -7.0F, -9.0F, 12.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(2, 42).func_228302_a_(-12.0F, -7.0F, 7.0F, 12.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(3, 15).func_228302_a_(40.0F, -8.0F, -7.0F, 2.0F, 8.0F, 14.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(2, 42).func_228302_a_(28.0F, -7.0F, 7.0F, 12.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(2, 42).func_228302_a_(28.0F, -7.0F, -9.0F, 12.0F, 7.0F, 2.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(3, 15).func_228302_a_(-14.0F, -8.0F, -7.0F, 2.0F, 8.0F, 14.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(0, 7).func_228302_a_(3.0F, 3.0F, -7.0F, 22.0F, 4.0F, 7.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(0, 1).func_228302_a_(25.0F, 3.0F, -4.5F, 11.0F, 3.0F, 9.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(0, 1).func_228302_a_(-7.0F, 3.0F, -4.5F, 11.0F, 3.0F, 9.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(10, 0).func_228302_a_(-17.0F, -12.0F, -2.0F, 3.0F, 10.0F, 4.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(10, 0).func_228302_a_(42.0F, -12.0F, -2.0F, 3.0F, 10.0F, 4.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(0, 8).func_228302_a_(0.0F, 0.0F, -3.0F, 19.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(0, 8).func_228302_a_(0.0F, 0.0F, 3.0F, 19.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(0, 8).func_228302_a_(19.0F, 0.0F, -9.0F, 21.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(0, 8).func_228302_a_(19.0F, 0.0F, -3.0F, 21.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(0, 8).func_228302_a_(19.0F, 0.0F, 3.0F, 21.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(0, 8).func_228302_a_(-12.0F, 0.0F, 3.0F, 12.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(0, 8).func_228302_a_(-12.0F, 0.0F, -3.0F, 12.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_78784_a(0, 8).func_228302_a_(-12.0F, 0.0F, -9.0F, 12.0F, 3.0F, 6.0F, 0.0F, 0.0F, 0.0F);
      this.RowBoat.func_228302_a_(3.0F, 3.0F, 0.0F, 22.0F, 4.0F, 7.0F, 0.0F, 0.0F, 0.0F);
      this.setRotateAngle(this.RowBoat, -0.0F, 1.5707964F, 0.0F);
      this.Cargo_1 = new ModelRenderer(this, 0, 0);
      this.Cargo_1.func_78793_a(31.2F, -8.0F, -6.0F);
      this.Cargo_1.func_78784_a(96, 38).func_228302_a_(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F);
      this.Cargo_1.func_78784_a(30, 55).func_228302_a_(-3.2F, 3.0F, 0.0F, 3.0F, 5.0F, 4.0F, 0.0F, 0.0F, 0.0F);
      this.Cargo_1.func_78784_a(31, 56).func_228302_a_(4.4F, 3.0F, 8.0F, 3.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
      this.Cargo_1.func_228302_a_(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
      this.ruder0_2 = new ModelRenderer(this, 0, 0);
      this.ruder0_2.func_78793_a(0.0F, 7.0F, -7.0F);
      this.ruder0_2.func_78784_a(62, 0).func_228302_a_(-1.0F, 0.0F, -8.0F, 2.0F, 2.0F, 18.0F, 0.0F, 0.0F, 0.0F);
      this.ruder0_2.func_78784_a(62, 0).func_228302_a_(-0.8F, -3.0F, 15.0F, 1.0F, 6.0F, 7.0F, 0.0F, 0.0F, 0.0F);
      this.ruder0_2.func_78784_a(74, 32).func_228302_a_(-1.0F, 0.0F, 10.0F, 2.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
      this.setRotateAngle(this.ruder0_2, 0.05235988F, 0.12217305F, -0.4537856F);
      this.Cargo_0 = new ModelRenderer(this, 0, 0);
      this.Cargo_0.field_78809_i = true;
      this.Cargo_0.func_78793_a(-11.2F, -16.8F, 2.0F);
      this.Cargo_0.func_78784_a(96, 38).func_228302_a_(0.0F, 9.0F, 0.0F, 8.0F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F);
      this.Cargo_0.func_78784_a(31, 56).func_228302_a_(-3.0F, 13.0F, 1.0F, 3.0F, 5.0F, 3.0F, 0.0F, -1.0F, 0.0F);
      this.Cargo_0.func_78784_a(31, 56).func_228302_a_(1.0F, 12.0F, 8.3F, 3.0F, 5.0F, 3.0F, 0.0F, 0.0F, 0.0F);
      this.setRotateAngle(this.Cargo_0, 0.0F, 1.5707964F, 0.0F);
      this.ruder0_1 = new ModelRenderer(this, 0, 0);
      this.ruder0_1.func_78793_a(12.0F, -7.0F, -9.0F);
      this.ruder0_1.func_78784_a(62, 0).func_228302_a_(-1.0F, 0.0F, -8.0F, 2.0F, 2.0F, 18.0F, 0.0F, 0.0F, 0.0F);
      this.ruder0_1.func_78784_a(62, 20).func_228302_a_(-0.2F, -3.0F, 15.0F, 1.0F, 6.0F, 7.0F, 0.0F, 0.0F, 0.0F);
      this.ruder0_1.func_78784_a(74, 32).func_228302_a_(-1.0F, 0.0F, 10.0F, 2.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
      this.setRotateAngle(this.ruder0_1, 0.71279246F, 0.2645919F, 1.6029103F);
      this.ruder_l = new ModelRenderer(this, 62, 0);
      this.ruder_l.func_78793_a(10.0F, -9.0F, 9.0F);
      this.ruder_l.func_228302_a_(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F, 0.0F, 0.0F, 0.0F);
      this.ruder_l.func_228302_a_(-0.8F, -3.0F, 12.0F, 1.0F, 6.0F, 7.0F, 0.0F, 0.0F, 0.0F);
      this.setRotateAngle(this.ruder_l, -0.65449846F, 0.6602581F, 0.19634955F);
      this.RowBoat.func_78792_a(this.ruder_r);
      this.RowBoat.func_78792_a(this.Cargo_1);
      this.ruder0_1.func_78792_a(this.ruder0_2);
      this.RowBoat.func_78792_a(this.Cargo_0);
      this.RowBoat.func_78792_a(this.ruder0_1);
      this.RowBoat.func_78792_a(this.ruder_l);
   }

   public void func_225598_a_(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
      ImmutableList.of(this.RowBoat).forEach((modelRenderer) -> {
         modelRenderer.func_228309_a_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
      });
   }

   public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (!entityIn.func_184207_aI()) {
         this.ruder0_1.field_78806_j = true;
         this.ruder0_2.field_78806_j = true;
         this.ruder_r.field_78806_j = false;
         this.ruder_l.field_78806_j = false;
      } else {
         this.ruder0_1.field_78806_j = false;
         this.ruder0_2.field_78806_j = false;
         this.ruder_r.field_78806_j = true;
         this.ruder_l.field_78806_j = true;
      }

      this.Cargo_1.field_78806_j = entityIn.Cargo_0;
      this.Cargo_0.field_78806_j = entityIn.Cargo_1;
      this.paddels(entityIn, 0, limbSwing);
      this.paddels(entityIn, 1, limbSwing);
   }

   public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
      modelRenderer.field_78795_f = x;
      modelRenderer.field_78796_g = y;
      modelRenderer.field_78808_h = z;
   }

   protected void paddels(RowBoatEntity galleyEntity, int side, float limbSwing) {
      float f = galleyEntity.getRowingTime(side, limbSwing);
      this.ruder_r.field_78795_f = (float)MathHelper.func_151238_b(-1.0471975803375244D, -0.2617993950843811D, (double)((MathHelper.func_76126_a(-f) + 1.0F) / 2.0F));
      this.ruder_r.field_78796_g = 3.1415927F - (float)MathHelper.func_151238_b(-0.7853981852531433D, 0.7853981852531433D, (double)((MathHelper.func_76126_a(-f + 1.0F) + 1.0F) / 2.0F));
      if (side == 0) {
         this.ruder_l.field_78795_f = (float)MathHelper.func_151238_b(-1.0471975803375244D, -0.2617993950843811D, (double)((MathHelper.func_76126_a(-f) + 1.0F) / 2.0F));
         this.ruder_l.field_78796_g = (float)MathHelper.func_151238_b(-0.7853981852531433D, 0.7853981852531433D, (double)((MathHelper.func_76126_a(-f + 1.0F) + 1.0F) / 2.0F));
      }

   }
}
