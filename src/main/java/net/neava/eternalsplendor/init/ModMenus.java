package net.neava.eternalsplendor.init;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.neava.eternalsplendor.EternalSplendorMod;
import net.neava.eternalsplendor.world.inventory.NewEnchantmentMenu;

public class ModMenus {

    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, EternalSplendorMod.MODID);
    public static final RegistryObject<MenuType<NewEnchantmentMenu>> NEW_ENCHANTMENT_MENU = REGISTRY.register("new_enchantment_menu", () -> IForgeMenuType.create(NewEnchantmentMenu::new));

}
