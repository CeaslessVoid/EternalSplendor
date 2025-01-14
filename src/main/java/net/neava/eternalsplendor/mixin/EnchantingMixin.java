package net.neava.eternalsplendor.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.EnchantmentMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantmentMenu.class)
public class EnchantingMixin {

    public EnchantingMixin() {}

    @Inject(
            method = {"slotsChanged"},
            at ={@At("HEAD")}
    )
    private void injectModifier(Container pInventory, CallbackInfo ci)
    {
        System.out.print("HELLO FROM HIEU");
        System.out.print("--------");
    }
}
