package com.talhanation.smallships;

import com.talhanation.smallships.entities.AbstractInventoryEntity;
import com.talhanation.smallships.inventory.BasicShipContainer;
import com.talhanation.smallships.network.MessageOpenGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.network.NetworkHooks;

public class InventoryEvents {

    public static void openShipGUI(PlayerEntity player, AbstractInventoryEntity invEntity, int startSlot) {
        int pageSize = Math.max(1, invEntity.getInventoryPageSize());
        int maxPage = Math.max(1, invEntity.getMaxInvPage());
        int requestedPage = startSlot / pageSize + 1;
        int clampedPage = MathHelper.clamp(requestedPage, 1, maxPage);
        int slotOffset = (clampedPage - 1) * pageSize;
        int totalSize = Math.max(0, invEntity.getInventorySize());
        if (totalSize > 0) {
            slotOffset = Math.min(slotOffset, Math.max(0, totalSize - 1));
            slotOffset = (slotOffset / pageSize) * pageSize;
        } else {
            slotOffset = 0;
        }
        invEntity.setInvPage(clampedPage);

        if (player instanceof ServerPlayerEntity) {
            NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
                @Override
                public ITextComponent getDisplayName() {
                    return invEntity.getName();
                }

                @Override
                public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                    return new BasicShipContainer(i, invEntity, playerInventory, slotOffset);
                }
            }, packetBuffer -> {packetBuffer.writeUUID(invEntity.getUUID());
            });
        } else {
            Main.SIMPLE_CHANNEL.sendToServer(new MessageOpenGui(player, invEntity, slotOffset));
        }
    }


}
