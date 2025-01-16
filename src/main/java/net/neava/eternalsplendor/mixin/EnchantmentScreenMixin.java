package net.neava.eternalsplendor.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EnchantmentScreen.class})
public class EnchantmentScreenMixin {

    public EnchantmentScreenMixin() {}

//    @Inject(
//            method = {"render"},
//            at ={@At("HEAD")}
//    )
    private void injectModifier(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick, CallbackInfo ci)
    {
    }
}

