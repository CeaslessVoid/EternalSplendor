package net.neava.eternalsplendor.init;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.neava.eternalsplendor.EternalSplendorMod;
import net.neava.eternalsplendor.client.gui.NewEnchantmentScreen;
import net.neava.eternalsplendor.world.inventory.NewEnchantmentMenu;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModScreens {
    @SubscribeEvent
    public static void clientLoad(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenus.NEW_ENCHANTMENT_MENU.get(), NewEnchantmentScreen::new);
        });
    }
}
