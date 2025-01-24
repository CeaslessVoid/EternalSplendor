package net.neava.eternalsplendor.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neava.eternalsplendor.EternalSplendorMod;
import net.neava.eternalsplendor.enchantment.EnchantingPacket;
import net.neava.eternalsplendor.world.inventory.NewEnchantmentMenu;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class NewEnchantmentScreen extends AbstractContainerScreen<NewEnchantmentMenu> {

    private final static HashMap<String, Object> guistate = NewEnchantmentMenu.guistate;

    private ImageButton ImageButton_Enchant;

    private final NewEnchantmentMenu menu;

    private boolean enchantment = false;
    private boolean hasFailed = false;

    public final int x,y,z;

    private static final Component FAILED_ENCHANT = Component.translatable("tooltip.neava_eternalsplendor.failenchant");

    public NewEnchantmentScreen(NewEnchantmentMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.menu = container;

        this.x = container.x;
        this.y = container.y;
        this.z = container.z;

    }

    public void containerTick() {
        super.containerTick();

        enchantment = this.menu.enchantable;
        hasFailed = this.menu.hasFailed;

        if (ImageButton_Enchant != null) {
            ImageButton_Enchant.visible = enchantment;
        }


    }

    private static final ResourceLocation texture = new ResourceLocation("neava_eternalsplendor:textures/screens/enchanting_menu.png");

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        pGuiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(key, b, c);
    }

    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);
        if (hasFailed)
        {
            Component component = FAILED_ENCHANT;
            int k = this.imageWidth - 8 - this.font.width(component) - 2;
            int l = 69;
            pGuiGraphics.fill(k - 2, 67, this.imageWidth - 8, 79, 1325400064);
            pGuiGraphics.drawString(this.font, component, k, 69, 16736352);
        }
    }


    @Override
    public void init() {
        super.init();

        ImageButton_Enchant = new ImageButton(this.leftPos + 64, this.topPos + 7, 48, 16, 0, 0, 16, new ResourceLocation("neava_eternalsplendor:textures/screens/enchanting_button.png"), 48, 32, e -> {

            EternalSplendorMod.PACKET_HANDLER.sendToServer(new EnchantingPacket(0,x,y,z));
            this.ImageButton_Enchant.setFocused(false);

        }) {

            @Override
            public void onPress() {
                super.onPress();
                this.setFocused(false);
            }

            @Override
            public void renderWidget(@NotNull GuiGraphics guiGraphics, int gx, int gy, float ticks) {
                super.renderWidget(guiGraphics, gx, gy, ticks);
            }
        };

        ImageButton_Enchant.visible = enchantment;

        guistate.put("button:enchanting_button", ImageButton_Enchant);
        this.addRenderableWidget(ImageButton_Enchant);
    }
}
