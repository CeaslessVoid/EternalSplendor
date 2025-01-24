package net.neava.eternalsplendor.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.neava.eternalsplendor.EternalSplendorMod;

@Mod.EventBusSubscriber(modid = EternalSplendorMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TooltipEventHandler {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        if (stack.is(Items.BOOKSHELF)) {
            event.getToolTip().add(Component.literal(""));
            event.getToolTip().add(Component.translatable("tooltip.neava_eternalsplendor.bookshelf"));
        }
        else if (stack.is(Items.ENCHANTED_BOOK))
        {
            event.getToolTip().add(Component.literal(""));
            event.getToolTip().add(Component.translatable("tooltip.neava_eternalsplendor.enchantedbook"));
        }
    }
}
