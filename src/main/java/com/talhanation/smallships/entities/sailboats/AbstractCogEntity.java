package com.talhanation.smallships.entities.sailboats;

import com.talhanation.smallships.config.SmallShipsConfig;
import com.talhanation.smallships.entities.AbstractBannerUser;
import com.talhanation.smallships.entities.TNBoatEntity;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class AbstractCogEntity extends AbstractBannerUser {
   public float momentum;
   public float outOfControlTicks;
   public float deltaRotation;
   public boolean field_70128_L;
   private double waterLevel;
   public boolean leftInputDown;
   public boolean rightInputDown;
   public boolean forwardInputDown;
   public boolean backInputDown;
   private float boatGlide;
   private TNBoatEntity.Status status;
   private TNBoatEntity.Status previousStatus;
   public int passengerwaittime;
   public float passengerfaktor;

   public AbstractCogEntity(EntityType<? extends AbstractCogEntity> entityType, World worldIn) {
      super(entityType, worldIn);
   }

   protected void func_70088_a() {
      super.func_70088_a();
   }

   public void func_70071_h_() {
      --this.passengerwaittime;
      this.previousStatus = this.status;
      this.status = this.getBoatStatus();
      if (this.status != TNBoatEntity.Status.UNDER_WATER && this.status != TNBoatEntity.Status.UNDER_FLOWING_WATER) {
         this.outOfControlTicks = 0.0F;
      } else {
         ++this.outOfControlTicks;
      }

      if (!this.field_70170_p.field_72995_K && this.outOfControlTicks >= 60.0F) {
         this.func_184226_ay();
      }

      if (this.getTimeSinceHit() > 0) {
         this.setTimeSinceHit(this.getTimeSinceHit() - 1);
      }

      super.func_70071_h_();
      this.tickLerp();
      if (this.func_184186_bw()) {
         this.updateMotion();
         if (this.field_70170_p.field_72995_K) {
            this.controlBoat();
         }

         this.func_213315_a(MoverType.SELF, this.func_213322_ci());
      } else {
         this.func_213317_d(Vector3d.field_186680_a);
      }

      if (this.getSailState() != 0 && this.getBoatStatus().equals(TNBoatEntity.Status.IN_WATER) && this.func_184179_bs() instanceof PlayerEntity && (Boolean)SmallShipsConfig.PlaySwimmSound.get()) {
         this.field_70170_p.func_184148_a((PlayerEntity)null, this.func_226277_ct_(), this.func_226278_cu_(), this.func_226281_cx_(), SoundEvents.field_187549_bG, this.func_184176_by(), 0.05F, 0.8F + 0.4F * this.field_70146_Z.nextFloat());
      }

      this.func_145775_I();
      List<Entity> list = this.field_70170_p.func_175674_a(this, this.func_174813_aQ().func_72314_b(0.20000000298023224D, -0.009999999776482582D, 0.20000000298023224D), EntityPredicates.func_200823_a(this));
      if (!list.isEmpty()) {
         boolean flag = !this.field_70170_p.field_72995_K && !(this.func_184179_bs() instanceof PlayerEntity);

         for(int j = 0; j < list.size(); ++j) {
            Entity entity = (Entity)list.get(j);
            if (!entity.func_184196_w(this)) {
               if (flag && this.func_184188_bt().size() < 5 && !entity.func_184218_aH() && entity.func_213311_cf() < this.func_213311_cf() && entity instanceof LivingEntity && !(entity instanceof WaterMobEntity) && !(entity instanceof PlayerEntity)) {
                  if (this.passengerwaittime < 0) {
                     entity.func_184220_m(this);
                  }
               } else {
                  this.func_70108_f(entity);
               }
            }
         }
      }

   }

   public void tickLerp() {
   }

   public void Watersplash() {
      super.Watersplash();
      Vector3d vector3d = this.func_70676_i(0.0F);
      float f0 = MathHelper.func_76134_b(this.field_70177_z * 0.017453292F) * 0.8F;
      float f1 = MathHelper.func_76126_a(this.field_70177_z * 0.017453292F) * 0.8F;
      float f0_1 = MathHelper.func_76134_b(this.field_70177_z * 0.017453292F) * 1.6F;
      float f1_1 = MathHelper.func_76126_a(this.field_70177_z * 0.017453292F) * 1.6F;
      float f2 = 2.5F - this.field_70146_Z.nextFloat() * 0.7F;
      float f2_ = -1.3F - this.field_70146_Z.nextFloat() * 0.7F;
      float x = 0.0F;

      for(int i = 0; i < 2; ++i) {
         this.field_70170_p.func_195594_a(ParticleTypes.field_206864_X, this.func_226277_ct_() - vector3d.field_72450_a * (double)f2 + (double)f0, this.func_226278_cu_() - vector3d.field_72448_b + 0.5D, this.func_226281_cx_() - vector3d.field_72449_c * (double)f2 + (double)f1, 0.0D, 0.0D, 0.0D);
         this.field_70170_p.func_195594_a(ParticleTypes.field_206864_X, this.func_226277_ct_() - vector3d.field_72450_a * (double)f2 - (double)f0, this.func_226278_cu_() - vector3d.field_72448_b + 0.5D, this.func_226281_cx_() - vector3d.field_72449_c * (double)f2 - (double)f1, 0.0D, 0.0D, 0.0D);
         this.field_70170_p.func_195594_a(ParticleTypes.field_206864_X, this.func_226277_ct_() - vector3d.field_72450_a * (double)f2 + (double)f0, this.func_226278_cu_() - vector3d.field_72448_b + 0.5D, this.func_226281_cx_() - vector3d.field_72449_c * (double)f2 + (double)f1 * 1.1D, 0.0D, 0.0D, 0.0D);
         this.field_70170_p.func_195594_a(ParticleTypes.field_206864_X, this.func_226277_ct_() - vector3d.field_72450_a * (double)f2 - (double)f0, this.func_226278_cu_() - vector3d.field_72448_b + 0.5D, this.func_226281_cx_() - vector3d.field_72449_c * (double)f2 - (double)f1 * 1.1D, 0.0D, 0.0D, 0.0D);
         this.field_70170_p.func_195594_a(ParticleTypes.field_218422_X, this.func_226277_ct_() - vector3d.field_72450_a * (double)f2 + (double)f0, this.func_226278_cu_() - vector3d.field_72448_b + 0.8D, this.func_226281_cx_() - vector3d.field_72449_c * (double)f2 + (double)f1, 0.0D, 0.0D, 0.0D);
         this.field_70170_p.func_195594_a(ParticleTypes.field_218422_X, this.func_226277_ct_() - vector3d.field_72450_a * (double)f2 - (double)f0, this.func_226278_cu_() - vector3d.field_72448_b + 0.8D, this.func_226281_cx_() - vector3d.field_72449_c * (double)f2 - (double)f1, 0.0D, 0.0D, 0.0D);
         this.field_70170_p.func_195594_a(ParticleTypes.field_218422_X, this.func_226277_ct_() - vector3d.field_72450_a * (double)f2 + (double)f0, this.func_226278_cu_() - vector3d.field_72448_b + 0.8D, this.func_226281_cx_() - vector3d.field_72449_c * (double)f2 + (double)f1 * 1.1D, 0.0D, 0.0D, 0.0D);
         this.field_70170_p.func_195594_a(ParticleTypes.field_218422_X, this.func_226277_ct_() - vector3d.field_72450_a * (double)f2 - (double)f0, this.func_226278_cu_() - vector3d.field_72448_b + 0.8D, this.func_226281_cx_() - vector3d.field_72449_c * (double)f2 - (double)f1 * 1.1D, 0.0D, 0.0D, 0.0D);
         this.field_70170_p.func_195594_a(ParticleTypes.field_218422_X, this.func_226277_ct_() - vector3d.field_72450_a * (double)f2_ + (double)f0_1, this.func_226278_cu_() - vector3d.field_72448_b + 0.8D, this.func_226281_cx_() - vector3d.field_72449_c * (double)(f2_ - x) + (double)f1_1, 0.0D, 0.0D, 0.0D);
         this.field_70170_p.func_195594_a(ParticleTypes.field_218422_X, this.func_226277_ct_() - vector3d.field_72450_a * (double)f2_ - (double)f0_1, this.func_226278_cu_() - vector3d.field_72448_b + 0.8D, this.func_226281_cx_() - vector3d.field_72449_c * (double)(f2_ - x) - (double)f1_1, 0.0D, 0.0D, 0.0D);
         this.field_70170_p.func_195594_a(ParticleTypes.field_218422_X, this.func_226277_ct_() - vector3d.field_72450_a * (double)f2_ + (double)f0_1, this.func_226278_cu_() - vector3d.field_72448_b + 0.8D, this.func_226281_cx_() - vector3d.field_72449_c * (double)(f2_ - x) + (double)f1_1 * 1.1D, 0.0D, 0.0D, 0.0D);
         this.field_70170_p.func_195594_a(ParticleTypes.field_218422_X, this.func_226277_ct_() - vector3d.field_72450_a * (double)f2_ - (double)f0_1, this.func_226278_cu_() - vector3d.field_72448_b + 0.8D, this.func_226281_cx_() - vector3d.field_72449_c * (double)(f2_ - x) - (double)f1_1 * 1.1D, 0.0D, 0.0D, 0.0D);
         this.field_70170_p.func_195594_a(ParticleTypes.field_197612_e, this.func_226277_ct_() - vector3d.field_72450_a * (double)f2_ + (double)f0_1, this.func_226278_cu_() - vector3d.field_72448_b + 0.8D, this.func_226281_cx_() - vector3d.field_72449_c * (double)(f2_ - x) + (double)f1_1, 0.0D, 0.0D, 0.0D);
         this.field_70170_p.func_195594_a(ParticleTypes.field_197612_e, this.func_226277_ct_() - vector3d.field_72450_a * (double)f2_ - (double)f0_1, this.func_226278_cu_() - vector3d.field_72448_b + 0.8D, this.func_226281_cx_() - vector3d.field_72449_c * (double)(f2_ - x) - (double)f1_1, 0.0D, 0.0D, 0.0D);
         this.field_70170_p.func_195594_a(ParticleTypes.field_197612_e, this.func_226277_ct_() - vector3d.field_72450_a * (double)f2_ + (double)f0_1, this.func_226278_cu_() - vector3d.field_72448_b + 0.8D, this.func_226281_cx_() - vector3d.field_72449_c * (double)(f2_ - x) + (double)f1_1 * 1.1D, 0.0D, 0.0D, 0.0D);
         this.field_70170_p.func_195594_a(ParticleTypes.field_197612_e, this.func_226277_ct_() - vector3d.field_72450_a * (double)f2_ - (double)f0_1, this.func_226278_cu_() - vector3d.field_72448_b + 0.8D, this.func_226281_cx_() - vector3d.field_72449_c * (double)(f2_ - x) - (double)f1_1 * 1.1D, 0.0D, 0.0D, 0.0D);
      }

   }

   public TNBoatEntity.Status getBoatStatus() {
      TNBoatEntity.Status boatentity$status = this.getUnderwaterStatus();
      if (boatentity$status != null) {
         this.waterLevel = this.func_174813_aQ().field_72337_e;
         return boatentity$status;
      } else if (this.checkInWater()) {
         return TNBoatEntity.Status.IN_WATER;
      } else {
         float f = this.getBoatGlide();
         if (f > 0.0F) {
            this.boatGlide = 0.0F;
            return TNBoatEntity.Status.ON_LAND;
         } else {
            return TNBoatEntity.Status.IN_AIR;
         }
      }
   }

   public void updateMotion() {
      double d0 = -0.04D;
      double d1 = this.func_189652_ae() ? 0.0D : d0;
      double d2 = 0.0D;
      double CogTurnFactor = (Double)SmallShipsConfig.CogTurnFactor.get();
      this.momentum = 1.0F;
      if (this.func_184188_bt().size() == 2) {
         this.passengerfaktor = 0.05F;
      }

      if (this.func_184188_bt().size() == 4) {
         this.passengerfaktor = 0.1F;
      }

      if (this.func_184188_bt().size() == 6) {
         this.passengerfaktor = 0.2F;
      }

      if (this.func_184188_bt().size() == 8) {
         this.passengerfaktor = 0.3F;
      }

      if (this.func_184188_bt().size() > 8) {
         this.passengerfaktor = 0.4F;
      }

      if (this.previousStatus == TNBoatEntity.Status.IN_AIR && this.status != TNBoatEntity.Status.IN_AIR && this.status != TNBoatEntity.Status.ON_LAND) {
         this.waterLevel = this.func_174813_aQ().field_72338_b + (double)this.func_213302_cg();
         this.func_70107_b(this.func_226277_ct_(), (double)(this.getWaterLevelAbove() - this.func_213302_cg()) + 0.101D, this.func_226281_cx_());
         this.func_213317_d(this.func_213322_ci().func_216372_d(10.0D, 0.0D, 10.0D));
         this.status = TNBoatEntity.Status.IN_WATER;
      } else {
         if (this.status == TNBoatEntity.Status.IN_WATER) {
            d2 = (this.waterLevel - this.func_174813_aQ().field_72338_b + 0.1D) / (double)this.func_213302_cg();
            this.momentum = 0.9F;
         } else if (this.status == TNBoatEntity.Status.UNDER_FLOWING_WATER) {
            d1 = -7.0E-4D;
            this.momentum = 0.9F;
         } else if (this.status == TNBoatEntity.Status.UNDER_WATER) {
            d2 = 0.009999999776482582D;
            this.momentum = 0.45F;
         } else if (this.status == TNBoatEntity.Status.IN_AIR) {
            this.momentum = 0.9F;
         } else if (this.status == TNBoatEntity.Status.ON_LAND) {
            this.momentum = this.boatGlide * 0.001F;
            if (this.func_184179_bs() instanceof PlayerEntity) {
               this.boatGlide /= 1.0F;
            }
         }

         Vector3d vec3d = this.func_213322_ci();
         this.func_213293_j(vec3d.field_72450_a * (double)(this.momentum - this.passengerfaktor), vec3d.field_72448_b + d1, vec3d.field_72449_c * (double)(this.momentum - this.passengerfaktor));
         this.deltaRotation = (float)((double)this.deltaRotation * (double)(this.momentum - this.passengerfaktor) * CogTurnFactor);
         if (d2 > 0.0D) {
            Vector3d vec3d1 = this.func_213322_ci();
            this.func_213293_j(vec3d1.field_72450_a, (vec3d1.field_72448_b + d2 * 0.06D) * 0.75D, vec3d1.field_72449_c);
         }
      }

   }

   protected void controlBoat() {
      double CogSpeedFactor = (Double)SmallShipsConfig.CogSpeedFactor.get();
      int sailstate = this.getSailState();
      if (this.func_184207_aI()) {
         float f = 0.0F;
         if (this.leftInputDown) {
            --this.deltaRotation;
         }

         if (this.rightInputDown) {
            ++this.deltaRotation;
         }

         if (this.rightInputDown != this.leftInputDown && !this.forwardInputDown && !this.backInputDown) {
            f += 0.005F;
         }

         this.field_70177_z += this.deltaRotation;
         if (sailstate != 0) {
            switch(sailstate) {
            case 1:
               f = (float)((double)f + 0.03999999910593033D * CogSpeedFactor * 1.0D / 4.0D);
               break;
            case 2:
               f = (float)((double)f + 0.03999999910593033D * CogSpeedFactor * 2.0D / 4.0D);
               break;
            case 3:
               f = (float)((double)f + 0.03999999910593033D * CogSpeedFactor * 3.0D / 4.0D);
               break;
            case 4:
               f = (float)((double)f + 0.03999999910593033D * CogSpeedFactor * 1.0D);
            }
         }

         if (this.backInputDown) {
            f = (float)((double)f - 0.009999999776482582D * CogSpeedFactor);
         }

         if (this.forwardInputDown) {
            f = (float)((double)f + 0.009999999776482582D * CogSpeedFactor);
         }

         this.func_213317_d(this.func_213322_ci().func_72441_c((double)(MathHelper.func_76126_a(-this.field_70177_z * 0.017453292F) * f), 0.0D, (double)(MathHelper.func_76134_b(this.field_70177_z * 0.017453292F) * f)));
         this.setSteerState(this.rightInputDown && !this.leftInputDown, this.leftInputDown && !this.rightInputDown);
         this.setIsForward(this.forwardInputDown);
      }

   }

   public IPacket<?> func_213297_N() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   @Nullable
   private TNBoatEntity.Status getUnderwaterStatus() {
      AxisAlignedBB axisalignedbb = this.func_174813_aQ();
      double d0 = axisalignedbb.field_72337_e + 0.001D;
      int i = MathHelper.func_76128_c(axisalignedbb.field_72340_a);
      int j = MathHelper.func_76143_f(axisalignedbb.field_72336_d);
      int k = MathHelper.func_76128_c(axisalignedbb.field_72337_e);
      int l = MathHelper.func_76143_f(d0);
      int i1 = MathHelper.func_76128_c(axisalignedbb.field_72339_c);
      int j1 = MathHelper.func_76143_f(axisalignedbb.field_72334_f);
      boolean flag = false;
      Mutable blockpos$mutable = new Mutable();

      for(int k1 = i; k1 < j; ++k1) {
         for(int l1 = k; l1 < l; ++l1) {
            for(int i2 = i1; i2 < j1; ++i2) {
               blockpos$mutable.func_181079_c(k1, l1, i2);
               FluidState fluidstate = this.field_70170_p.func_204610_c(blockpos$mutable);
               if (fluidstate.func_206884_a(FluidTags.field_206959_a) && d0 < (double)((float)blockpos$mutable.func_177956_o() + fluidstate.func_215679_a(this.field_70170_p, blockpos$mutable))) {
                  if (!fluidstate.func_206889_d()) {
                     return TNBoatEntity.Status.UNDER_FLOWING_WATER;
                  }

                  flag = true;
               }
            }
         }
      }

      return flag ? TNBoatEntity.Status.UNDER_WATER : null;
   }

   private boolean checkInWater() {
      AxisAlignedBB axisalignedbb = this.func_174813_aQ();
      int i = MathHelper.func_76128_c(axisalignedbb.field_72340_a);
      int j = MathHelper.func_76143_f(axisalignedbb.field_72336_d);
      int k = MathHelper.func_76128_c(axisalignedbb.field_72338_b);
      int l = MathHelper.func_76143_f(axisalignedbb.field_72338_b + 0.001D);
      int i1 = MathHelper.func_76128_c(axisalignedbb.field_72339_c);
      int j1 = MathHelper.func_76143_f(axisalignedbb.field_72334_f);
      boolean flag = false;
      this.waterLevel = Double.MIN_VALUE;
      Mutable blockpos$mutable = new Mutable();

      for(int k1 = i; k1 < j; ++k1) {
         for(int l1 = k; l1 < l; ++l1) {
            for(int i2 = i1; i2 < j1; ++i2) {
               blockpos$mutable.func_181079_c(k1, l1, i2);
               FluidState fluidstate = this.field_70170_p.func_204610_c(blockpos$mutable);
               if (fluidstate.func_206884_a(FluidTags.field_206959_a)) {
                  float f = (float)l1 + fluidstate.func_215679_a(this.field_70170_p, blockpos$mutable);
                  this.waterLevel = Math.max((double)f, this.waterLevel);
                  flag |= axisalignedbb.field_72338_b < (double)f;
               }
            }
         }
      }

      return flag;
   }

   public boolean func_70104_M() {
      return false;
   }

   public void func_70108_f(Entity entityIn) {
      super.func_70108_f(entityIn);
   }

   public boolean func_70097_a(DamageSource source, float amount) {
      double CogHealth = (Double)SmallShipsConfig.CogHealth.get();
      if (this.func_180431_b(source)) {
         return false;
      } else if (!this.field_70170_p.field_72995_K && this.func_70089_S()) {
         if (source == DamageSource.field_76367_g) {
            return false;
         } else if (source instanceof IndirectEntityDamageSource && source.func_76346_g() != null && this.func_184196_w(source.func_76346_g())) {
            return false;
         } else {
            this.setForwardDirection(-this.getForwardDirection());
            this.setTimeSinceHit(3);
            this.setDamageTaken(this.getDamageTaken() + amount * 10.0F);
            boolean flag = source.func_76346_g() instanceof PlayerEntity && ((PlayerEntity)source.func_76346_g()).field_71075_bZ.field_75098_d;
            if (flag || (double)this.getDamageTaken() > CogHealth) {
               this.onDestroyed(source, flag);
               this.func_70106_y();
            }

            return true;
         }
      } else {
         return false;
      }
   }

   protected void func_184200_o(Entity passenger) {
      super.func_184200_o(passenger);
   }

   public Vector3d func_230268_c_(LivingEntity rider) {
      return super.func_230268_c_(rider);
   }

   public PlayerEntity getDriver() {
      return super.getDriver();
   }

   @OnlyIn(Dist.CLIENT)
   public void updateInputs(boolean leftInputDown, boolean rightInputDown, boolean forwardInputDown, boolean backInputDown) {
      this.leftInputDown = leftInputDown;
      this.rightInputDown = rightInputDown;
      this.forwardInputDown = forwardInputDown;
      this.backInputDown = backInputDown;
   }
}
