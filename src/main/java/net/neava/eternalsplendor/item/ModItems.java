package net.neava.eternalsplendor.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.neava.eternalsplendor.EternalSplendorMod;
import net.neava.eternalsplendor.util.EnchantmentUtils;

public class ModItems {
    //neava_eternalsplendor

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EternalSplendorMod.MODID);

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

    public static final RegistryObject<Item> LAPISDUST = ITEMS.register("lapisdust",
            () -> new CatalystItem(new Item.Properties(), 0.05, 0, false, null, "tooltip.neava_eternalsplendor.lapisdust"));

    public static final RegistryObject<Item> BASICENCHANTCORE = ITEMS.register("basicenchantcore",
            () -> new EnchantingFuelItem(new Item.Properties(), 15, 15, EnchantmentUtils::isBasicEnchant, "tooltip.neava_eternalsplendor.basicenchantcore"));

    public static final RegistryObject<Item> RUNICPAGE = ITEMS.register("runicpage",
            () -> new EnchantingFuelItem(new Item.Properties().stacksTo(1), 0, 0, EnchantmentUtils::isBasicEnchant, "tooltip.neava_eternalsplendor.runicpage"));
}
