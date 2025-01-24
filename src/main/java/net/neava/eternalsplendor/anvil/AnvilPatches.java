package net.neava.eternalsplendor.anvil;


import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.neava.eternalsplendor.EternalSplendorMod;
import net.neava.eternalsplendor.util.EnchantmentUtils;
import net.neava.eternalsplendor.util.Utils;

import java.util.Map;

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
        // Booking
        else if (Utils.isEncahntable(leftStack) && rightStack.getItem() instanceof EnchantedBookItem) {


            if (EnchantedBookItem.getEnchantments(rightStack).size() + leftStack.getAllEnchantments().size() > 4){
                event.setCanceled(true);
            }
            else {

                Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(rightStack);

                ItemStack resultStack = leftStack.copy();
                EnchantmentHelper.setEnchantments(map, resultStack);

                event.setOutput(resultStack);

                event.setCost(35);
            }

        }
    }

}
