package net.neava.eternalsplendor.anvil;


import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.neava.eternalsplendor.EternalSplendorMod;

@Mod.EventBusSubscriber(modid = EternalSplendorMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AnvilPatches {

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event)
    {
        ItemStack leftStack = event.getLeft();
        ItemStack rightStack = event.getRight();

        // No Combine 2 books
        if (rightStack.getItem() instanceof EnchantedBookItem && leftStack.getItem() instanceof EnchantedBookItem) {
            event.setCanceled(true);
        }
        // No Combine Weapons
        else if (rightStack.isEnchanted() && leftStack.isEnchanted()) {
            event.setCanceled(true);
        }
    }

}
