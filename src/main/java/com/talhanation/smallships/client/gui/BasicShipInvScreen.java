package com.talhanation.smallships.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.talhanation.smallships.Main;
import com.talhanation.smallships.entities.AbstractInventoryEntity;
import com.talhanation.smallships.entities.AbstractShipDamage;
import com.talhanation.smallships.inventory.BasicShipContainer;
import com.talhanation.smallships.network.MessageOpenGui;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class BasicShipInvScreen extends ScreenBase<BasicShipContainer> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");

    private final BasicShipContainer container;
    private final AbstractInventoryEntity ship;
    private final PlayerInventory playerInventory;

    public BasicShipInvScreen(BasicShipContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(GUI_TEXTURE, container, playerInventory, title);
        this.ship = container.getShip();
        this.playerInventory = playerInventory;
        this.container = container;
        imageWidth = 176;
        imageHeight = 222;
    }


    @Override
    protected void init() {
        super.init();
        int zeroLeftPos = leftPos + 160;
        int zeroTopPos = topPos + 15;
        int pageSize = Math.max(1, ship.getInventoryPageSize());

        if (ship.getMaxInvPage() > 1 && ship.getInvPage() > 1){
            addButton(new Button(zeroLeftPos - 205, zeroTopPos, 40, 20, new StringTextComponent("<-"), button -> {
                int targetPage = MathHelper.clamp(ship.getInvPage() - 1, 1, ship.getMaxInvPage());
                ship.setInvPage(targetPage);
                int startSlot = (targetPage - 1) * pageSize;
                Main.SIMPLE_CHANNEL.sendToServer(new MessageOpenGui(playerInventory.player, ship, startSlot));
            }));
        }

        if(ship.getMaxInvPage() > 1 && ship.getInvPage() < ship.getMaxInvPage()){
            addButton(new Button(zeroLeftPos + 20, zeroTopPos, 40, 20, new StringTextComponent("->"), button -> {
                    int targetPage = MathHelper.clamp(ship.getInvPage() + 1, 1, ship.getMaxInvPage());
                    ship.setInvPage(targetPage);
                    int startSlot = (targetPage - 1) * pageSize;
                    Main.SIMPLE_CHANNEL.sendToServer(new MessageOpenGui(playerInventory.player, ship, startSlot));
            }));
        }
    }


    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);
        boolean renderInvPage = ship.getMaxInvPage() != 1;
        font.draw(matrixStack, ship.getDisplayName().getVisualOrderText(), 8, 6, FONT_COLOR);
        font.draw(matrixStack, playerInventory.getDisplayName().getVisualOrderText(), 8, imageHeight - 95, FONT_COLOR);
        if (renderInvPage)font.draw(matrixStack,ship.getInvPage() + "/"  + ship.getMaxInvPage(), 50, 6, FONT_COLOR);
        font.draw(matrixStack,"Damage: " + (double) Math.round(((AbstractShipDamage) ship).getShipDamage()) + "%", 95, 6, FONT_COLOR);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(matrixStack, partialTicks, mouseX, mouseY);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }


}