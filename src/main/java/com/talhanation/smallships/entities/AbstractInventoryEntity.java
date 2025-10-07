package com.talhanation.smallships.entities;

import com.talhanation.smallships.Main;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public abstract class AbstractInventoryEntity extends AbstractSailShip {

    private static final DataParameter<Integer> CARGO = EntityDataManager.defineId(AbstractInventoryEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> INV_PAGE = EntityDataManager.defineId(AbstractInventoryEntity.class, DataSerializers.INT);

    private final Inventory inventory = new Inventory(this.getInventorySize());

    public AbstractInventoryEntity(EntityType<? extends AbstractInventoryEntity> type, World world) {
        super(type, world);
    }

    ///////////////////////////////////TICK/////////////////////////////////////////

    public void tick() {
        super.tick();
    }

    ////////////////////////////////////DATA////////////////////////////////////


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(CARGO, 0);
        entityData.define(INV_PAGE, 1);
    }


    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        ListNBT list = new ListNBT();
        for (int i = 0; i < this.inventory.getContainerSize(); ++i) {
            ItemStack itemstack = this.inventory.getItem(i);
            if (!itemstack.isEmpty()) {
                CompoundNBT compoundnbt = new CompoundNBT();
                compoundnbt.putByte("Slot", (byte) i);
                itemstack.save(compoundnbt);
                list.add(compoundnbt);
            }
        }

        nbt.put("Inventory", list);
        nbt.putInt("Cargo", getCargo());
        nbt.putInt("InventoryPage", getInvPage());
    }

    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        ListNBT list = nbt.getList("Inventory", 10);
        for (int i = 0; i < list.size(); ++i) {
            CompoundNBT compoundnbt = list.getCompound(i);
            int j = compoundnbt.getByte("Slot") & 255;

            this.inventory.setItem(j, ItemStack.of(compoundnbt));
        }
        this.setCargo(nbt.getInt("Cargo"));
        this.setInvPage(nbt.getInt("InventoryPage"));
    }

    ////////////////////////////////////GET////////////////////////////////////

    public Inventory getInventory() {
        return this.inventory;
    }

    public abstract int getInventorySize();


    public int getCargo(){
        return entityData.get(CARGO);
    }

    public int getMaxInvPage(){
        int pageSize = Math.max(1, getInventoryPageSize());
        int configured = Math.max(1, getConfiguredInventoryPages());
        int size = Math.max(0, this.getInventorySize());
        int required = Math.max(1, (size + pageSize - 1) / pageSize);
        return Math.max(configured, required);
    }

    public int getConfiguredInventoryPages() {
        return 1;
    }

    public int getInventoryPageSize() {
        return 54;
    }

    public int getInventoryPageColumns() {
        return 9;
    }

    public int getInvPage(){
        return entityData.get(INV_PAGE);
    }

    ////////////////////////////////////SET////////////////////////////////////

    public boolean setSlot(int slot, ItemStack itemStack) {
        if (super.setSlot(slot, itemStack)) {
            return true;
        } else {
            int i = slot - 300;
            if (i >= 0 && i < this.inventory.items.size()) {
                this.inventory.setItem(i, itemStack);
                return true;
            } else {
                return false;
            }
        }
    }

    public void setCargo(int cargo){
        entityData.set(CARGO, cargo);
    }

    public void setInvPage(int page){
        entityData.set(INV_PAGE, page);
    }

    ////////////////////////////////////OTHER FUNCTIONS////////////////////////////////////

    public void destroyShip(DamageSource dmg) {
        for (int i = 0; i < this.inventory.items.size(); i++)
            InventoryHelper.dropItemStack(this.level, getX(), getY(), getZ(), this.inventory.getItem(i));
        super.destroyShip(dmg);
    }

    public void updateCargo(){
        int x, slotCount = 0;
        int oldSlots = this.getCargo();

        for (int i = 0; i < this.inventory.items.size(); i++) {
            if (!inventory.getItem(i).isEmpty())
                slotCount++;
        }

        int average = (slotCount + oldSlots) / 2;


        int pageSize = Math.max(1, getInventoryPageSize());
        int capacity = Math.max(1, pageSize * Math.max(1, getMaxInvPage()));
        double ratio = capacity > 0 ? (double) average / (double) capacity : 0D;

        if (ratio > 0.5D) {
            x = 4;
        } else if (ratio > 16D / 54D) {
            x = 3;
        } else if (ratio > 8D / 54D) {
            x = 2;
        } else if (ratio > 2D / 54D) {
            x = 1;
        } else {
            x = 0;
        }

        setCargo(x);
    }
}

