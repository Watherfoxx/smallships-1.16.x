package com.talhanation.smallships.inventory;

import com.talhanation.smallships.Main;
import com.talhanation.smallships.entities.AbstractInventoryEntity;
import de.maxhenkel.corelib.inventory.ContainerBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class BasicShipContainer extends ContainerBase {

    private final IInventory shipInventory;
    private final AbstractInventoryEntity ship;
    private final int startSlot;
    private final int pageSlotCount;

    public BasicShipContainer(int id, AbstractInventoryEntity ship, PlayerInventory playerInventory, int startSlot) {
        super(Main.BASIC_SHIP_CONTAINER_TYPE, id, playerInventory, ship.getInventory());
        this.ship = ship;
        this.shipInventory = ship.getInventory();
        this.startSlot = startSlot;

        this.pageSlotCount = addShipInventorySlots();
        addPlayerInventorySlots();
    }

    @Override
    public int getInvOffset() {
        return 56;
    }

    public int addShipInventorySlots() {
        int columns = Math.max(1, ship.getInventoryPageColumns());
        int pageSize = Math.max(1, ship.getInventoryPageSize());
        int totalSlots = shipInventory.getContainerSize();
        int available = Math.max(0, totalSlots - startSlot);
        int visibleSlots = Math.min(pageSize, available);
        int rows = Math.max(1, (int) Math.ceil((double) pageSize / (double) columns));
        int added = 0;

        for (int row = 0; row < rows && added < visibleSlots; ++row) {
            for (int col = 0; col < columns && added < visibleSlots; ++col) {
                int slotIndex = startSlot + col + row * columns;
                if (slotIndex >= totalSlots) {
                    continue;
                }
                this.addSlot(new Slot(shipInventory, slotIndex, 8 + col * 18, 18 + row * 18));
                added++;
            }
        }

        return added;
    }

    public AbstractInventoryEntity getShip() {
        return ship;
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return this.shipInventory.stillValid(playerIn) && this.ship.distanceTo(playerIn) < 8.0F;
    }

    @Override
    public void removed(PlayerEntity playerIn) {
        super.removed(playerIn);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();

            int invSize = this.pageSlotCount;

            if (index < invSize) {
                if (!this.moveItemStackTo(stack, invSize, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (invSize > 0) {
                if (!this.moveItemStackTo(stack, 0, invSize, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    public void broadcastChanges() {
        super.broadcastChanges();
        ship.updateCargo();
    }
}