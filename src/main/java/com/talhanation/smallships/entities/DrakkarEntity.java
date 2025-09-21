package com.talhanation.smallships.entities;

import com.talhanation.smallships.entities.sailboats.AbstractDrakkarEntity;
import com.talhanation.smallships.init.ModEntityTypes;
import com.talhanation.smallships.inventory.DrakkarContainer;
import com.talhanation.smallships.items.ModItems;
import com.talhanation.smallships.util.DrakkarItemStackHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BannerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemStackHandler;

public class DrakkarEntity extends AbstractDrakkarEntity {
   public boolean Cargo_0;
   public boolean Cargo_1;
   private static final DataParameter<Integer> CARGO;

   public DrakkarEntity(EntityType<? extends AbstractDrakkarEntity> entityType, World worldIn) {
      super(entityType, worldIn);
      this.Cargo_0 = true;
      this.Cargo_1 = true;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      this.getCargo();
      if (0 < this.getCargo()) {
         this.Cargo_0 = true;
      } else {
         this.Cargo_0 = false;
      }

      if (1 < this.getCargo()) {
         this.Cargo_1 = true;
      } else {
         this.Cargo_1 = false;
      }

   }

   public ActionResultType func_184230_a(PlayerEntity player, Hand hand) {
      ItemStack itemInHand = player.func_184586_b(hand);
      if (itemInHand.func_77973_b() instanceof BannerItem) {
         this.onInteractionWithBanner(itemInHand, player);
         return ActionResultType.SUCCESS;
      } else if (itemInHand.func_77973_b() instanceof ShearsItem) {
         if (this.getHasBanner()) {
            this.onInteractionWithShears(player);
            return ActionResultType.SUCCESS;
         } else {
            return ActionResultType.PASS;
         }
      } else if (player.func_226563_dT_()) {
         if (this.func_184207_aI() && !(this.func_184179_bs() instanceof PlayerEntity)) {
            this.func_184226_ay();
            this.passengerwaittime = 200;
            return ActionResultType.PASS;
         } else {
            if (!(this.func_184179_bs() instanceof PlayerEntity)) {
               this.openContainer(player);
            }

            return ActionResultType.func_233537_a_(this.field_70170_p.field_72995_K);
         }
      } else if (this.outOfControlTicks < 60.0F) {
         if (!this.field_70170_p.field_72995_K) {
            return player.func_184220_m(this) ? ActionResultType.CONSUME : ActionResultType.PASS;
         } else {
            return ActionResultType.SUCCESS;
         }
      } else {
         return ActionResultType.PASS;
      }
   }

   public DrakkarEntity(World worldIn, double x, double y, double z) {
      this((EntityType)ModEntityTypes.DRAKKAR_ENTITY.get(), worldIn);
      this.func_70107_b(x, y, z);
      this.func_213317_d(Vector3d.field_186680_a);
      this.field_70169_q = x;
      this.field_70167_r = y;
      this.field_70166_s = z;
   }

   @OnlyIn(Dist.CLIENT)
   public void func_70103_a(byte id) {
      super.func_70103_a(id);
   }

   public double func_70042_X() {
      return 0.75D;
   }

   public void func_184232_k(Entity passenger) {
      if (this.func_184196_w(passenger)) {
         float f = -2.25F;
         float d = 0.0F;
         float f1 = (float)((this.field_70128_L ? 0.02D : this.func_70042_X()) + passenger.func_70033_W());
         int i;
         if (this.func_184188_bt().size() == 2) {
            i = this.func_184188_bt().indexOf(passenger);
            if (i == 0) {
               f = -2.25F;
               d = 0.0F;
            } else {
               f = -1.5F;
               d = -0.75F;
            }
         } else if (this.func_184188_bt().size() == 3) {
            i = this.func_184188_bt().indexOf(passenger);
            if (i == 0) {
               f = -2.25F;
               d = 0.0F;
            } else if (i == 1) {
               f = -1.5F;
               d = -0.75F;
            } else {
               f = -1.5F;
               d = 0.75F;
            }
         } else if (this.func_184188_bt().size() == 4) {
            i = this.func_184188_bt().indexOf(passenger);
            if (i == 0) {
               f = -2.25F;
               d = 0.0F;
            } else if (i == 1) {
               f = -1.5F;
               d = -0.75F;
            } else if (i == 2) {
               f = -1.5F;
               d = 0.75F;
            } else {
               f = 0.0F;
               d = 0.75F;
            }
         } else if (this.func_184188_bt().size() == 5) {
            i = this.func_184188_bt().indexOf(passenger);
            if (i == 0) {
               f = -2.25F;
               d = 0.0F;
            } else if (i == 1) {
               f = -1.5F;
               d = -0.75F;
            } else if (i == 2) {
               f = -1.5F;
               d = 0.75F;
            } else if (i == 3) {
               f = 0.0F;
               d = 0.75F;
            } else {
               f = 0.0F;
               d = -0.75F;
            }
         } else if (this.func_184188_bt().size() == 6) {
            i = this.func_184188_bt().indexOf(passenger);
            if (i == 0) {
               f = -2.25F;
               d = 0.0F;
            } else if (i == 1) {
               f = -1.5F;
               d = -0.75F;
            } else if (i == 2) {
               f = -1.5F;
               d = 0.75F;
            } else if (i == 3) {
               f = 0.0F;
               d = 0.75F;
            } else if (i == 4) {
               f = 0.0F;
               d = -0.75F;
            } else {
               f = 1.5F;
               d = -0.75F;
            }
         } else if (this.func_184188_bt().size() == 7) {
            i = this.func_184188_bt().indexOf(passenger);
            if (i == 0) {
               f = -2.25F;
               d = 0.0F;
            } else if (i == 1) {
               f = -1.5F;
               d = -0.75F;
            } else if (i == 2) {
               f = -1.5F;
               d = 0.75F;
            } else if (i == 3) {
               f = 0.0F;
               d = 0.75F;
            } else if (i == 4) {
               f = 0.0F;
               d = -0.75F;
            } else if (i == 5) {
               f = 1.5F;
               d = -0.75F;
            } else {
               f = 1.5F;
               d = 0.75F;
            }
         } else if (this.func_184188_bt().size() == 8) {
            i = this.func_184188_bt().indexOf(passenger);
            if (i == 0) {
               f = -2.25F;
               d = 0.0F;
            } else if (i == 1) {
               f = -1.5F;
               d = -0.75F;
            } else if (i == 2) {
               f = -1.5F;
               d = 0.75F;
            } else if (i == 3) {
               f = 0.0F;
               d = 0.75F;
            } else if (i == 4) {
               f = 0.0F;
               d = -0.75F;
            } else if (i == 5) {
               f = 1.5F;
               d = -0.75F;
            } else if (i == 6) {
               f = 1.5F;
               d = 0.75F;
            } else {
               f = 2.25F;
               d = 0.0F;
            }
         }

         Vector3d vector3d = (new Vector3d((double)f, 0.0D, (double)d)).func_178785_b(-this.field_70177_z * 0.017453292F - 1.5707964F);
         passenger.func_70107_b(this.func_226277_ct_() + vector3d.field_72450_a, this.func_226278_cu_() + (double)f1, this.func_226281_cx_() + vector3d.field_72449_c);
         passenger.field_70177_z += this.deltaRotation;
         this.clampRotation(passenger);
      }

   }

   public Item getItemBoat() {
      switch(this.getBoatType()) {
      case OAK:
      default:
         return (Item)ModItems.OAK_DRAKKAR_ITEM.get();
      case SPRUCE:
         return (Item)ModItems.SPRUCE_DRAKKAR_ITEM.get();
      case BIRCH:
         return (Item)ModItems.BIRCH_DRAKKAR_ITEM.get();
      case JUNGLE:
         return (Item)ModItems.JUNGLE_DRAKKAR_ITEM.get();
      case ACACIA:
         return (Item)ModItems.ACACIA_DRAKKAR_ITEM.get();
      case DARK_OAK:
         return (Item)ModItems.DARK_OAK_DRAKKAR_ITEM.get();
      case BOP_CHERRY:
         return (Item)ModItems.BOP_CHERRY_DRAKKAR_ITEM.get();
      case BOP_DEAD:
         return (Item)ModItems.BOP_DEAD_DRAKKAR_ITEM.get();
      case BOP_FIR:
         return (Item)ModItems.BOP_FIR_DRAKKAR_ITEM.get();
      case BOP_HELLBARK:
         return (Item)ModItems.BOP_HELLBARK_DRAKKAR_ITEM.get();
      case BOP_JACARANDA:
         return (Item)ModItems.BOP_JACARANDA_DRAKKAR_ITEM.get();
      case BOP_MAGIC:
         return (Item)ModItems.BOP_MAGIC_DRAKKAR_ITEM.get();
      case BOP_MAHOGANY:
         return (Item)ModItems.BOP_MAHOGANY_DRAKKAR_ITEM.get();
      case BOP_PALM:
         return (Item)ModItems.BOP_PALM_DRAKKAR_ITEM.get();
      case BOP_REDWOOD:
         return (Item)ModItems.BOP_REDWOOD_DRAKKAR_ITEM.get();
      case BOP_UMBRAN:
         return (Item)ModItems.BOP_UMBRAN_DRAKKAR_ITEM.get();
      case BOP_WILLOW:
         return (Item)ModItems.BOP_WILLOW_DRAKKAR_ITEM.get();
      case LOTR_APPLE:
         return (Item)ModItems.LOTR_APPLE_DRAKKAR_ITEM.get();
      case LOTR_ASPEN:
         return (Item)ModItems.LOTR_ASPEN_DRAKKAR_ITEM.get();
      case LOTR_BEECH:
         return (Item)ModItems.LOTR_BEECH_DRAKKAR_ITEM.get();
      case LOTR_CEDAR:
         return (Item)ModItems.LOTR_CEDAR_DRAKKAR_ITEM.get();
      case LOTR_CHERRY:
         return (Item)ModItems.LOTR_CHERRY_DRAKKAR_ITEM.get();
      case LOTR_CHARRED:
         return (Item)ModItems.LOTR_CHARRED_DRAKKAR_ITEM.get();
      case LOTR_CYPRESS:
         return (Item)ModItems.LOTR_CYPRESS_DRAKKAR_ITEM.get();
      case LOTR_FIR:
         return (Item)ModItems.LOTR_FIR_DRAKKAR_ITEM.get();
      case LOTR_GREEN_OAK:
         return (Item)ModItems.LOTR_GREEN_OAK_DRAKKAR_ITEM.get();
      case LOTR_HOLLY:
         return (Item)ModItems.LOTR_HOLLY_DRAKKAR_ITEM.get();
      case LOTR_LAIRELOSSE:
         return (Item)ModItems.LOTR_LAIRELOSSE_DRAKKAR_ITEM.get();
      case LOTR_LARCH:
         return (Item)ModItems.LOTR_LARCH_DRAKKAR_ITEM.get();
      case LOTR_LEBETHRON:
         return (Item)ModItems.LOTR_LEBETHRON_DRAKKAR_ITEM.get();
      case LOTR_MALLORN:
         return (Item)ModItems.LOTR_MALLORN_DRAKKAR_ITEM.get();
      case LOTR_MAPLE:
         return (Item)ModItems.LOTR_MAPLE_DRAKKAR_ITEM.get();
      case LOTR_MIRK_OAK:
         return (Item)ModItems.LOTR_MIRK_OAK_DRAKKAR_ITEM.get();
      case LOTR_PEAR:
         return (Item)ModItems.LOTR_PEAR_DRAKKAR_ITEM.get();
      case LOTR_PINE:
         return (Item)ModItems.LOTR_PINE_DRAKKAR_ITEM.get();
      case LOTR_ROTTEN:
         return (Item)ModItems.LOTR_ROTTEN_DRAKKAR_ITEM.get();
      case ENVI_CHERRY:
         return (Item)ModItems.ENVI_CHERRY_DRAKKAR_ITEM.get();
      case ENVI_WILLOW:
         return (Item)ModItems.ENVI_WILLOW_DRAKKAR_ITEM.get();
      case ENVI_WISTERIA:
         return (Item)ModItems.ENVI_WISTERIA_DRAKKAR_ITEM.get();
      }
   }

   protected ItemStackHandler initInventory() {
      return new DrakkarItemStackHandler<DrakkarEntity>(9, this) {
         protected void onContentsChanged(int slot) {
            int tempload = 0;

            for(int i = 0; i < this.getSlots(); ++i) {
               if (!this.getStackInSlot(i).func_190926_b()) {
                  ++tempload;
               }
            }

            byte sigma;
            if (tempload > 7) {
               sigma = 2;
            } else if (tempload > 3) {
               sigma = 1;
            } else {
               sigma = 0;
            }

            ((DrakkarEntity)this.drakkar).func_184212_Q().func_187227_b(DrakkarEntity.CARGO, Integer.valueOf(sigma));
         }
      };
   }

   public int getCargo() {
      return (Integer)this.field_70180_af.func_187225_a(CARGO);
   }

   public void openContainer(PlayerEntity player) {
      player.func_213829_a(new SimpleNamedContainerProvider((id, inv, plyr) -> {
         return new DrakkarContainer(id, inv, this);
      }, this.func_145748_c_()));
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(CARGO, 0);
   }

   protected void func_70037_a(CompoundNBT compound) {
      super.func_70037_a(compound);
      this.field_70180_af.func_187227_b(CARGO, compound.func_74762_e("Cargo"));
   }

   protected void func_213281_b(CompoundNBT compound) {
      super.func_213281_b(compound);
      compound.func_74768_a("Cargo", (Integer)this.field_70180_af.func_187225_a(CARGO));
   }

   protected boolean func_184219_q(Entity passenger) {
      return this.func_184188_bt().size() < 7;
   }

   static {
      CARGO = EntityDataManager.func_187226_a(AbstractDrakkarEntity.class, DataSerializers.field_187192_b);
   }
}
