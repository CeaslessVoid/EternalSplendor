package net.neava.eternalsplendor.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neava.eternalsplendor.world.inventory.NewEnchantmentMenu;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class NewEnchantmentScreen extends AbstractContainerScreen<NewEnchantmentMenu> {

    private final static HashMap<String, Object> guistate = NewEnchantmentMenu.guistate;
//    private final Level world;
//    private final int x, y, z;
//    private final Player entity;

    private ImageButton ImageButton_Enchant;

    private final NewEnchantmentMenu menu;

    private boolean enchantment = false;

    public NewEnchantmentScreen(NewEnchantmentMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.menu = container;
    }

    public void containerTick() {
        super.containerTick();

        enchantment = this.menu.enchantable;

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
    public void init() {
        super.init();

        ImageButton_Enchant = new ImageButton(this.leftPos + 64, this.topPos + 7, 48, 16, 0, 0, 16, new ResourceLocation("neava_eternalsplendor:textures/screens/enchanting_button.png"), 48, 32, e -> {
        }) {
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
