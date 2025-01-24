package net.neava.eternalsplendor.init;

import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.neava.eternalsplendor.EternalSplendorMod;
import net.neava.eternalsplendor.world.inventory.NewEnchantmentMenu;

public class ModMenus {

    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, EternalSplendorMod.MODID);
    public static final RegistryObject<MenuType<NewEnchantmentMenu>> NEW_ENCHANTMENT_MENU = REGISTRY.register("new_enchantment_menu",
//            () -> IForgeMenuType.create((windowId, inventory, data) -> {
//                BlockPos pos = data.readBlockPos();
//                Level pLevel = inventory.player.level();
//                return new NewEnchantmentMenu(windowId, inventory, ContainerLevelAccess.create(pLevel, pos));
//            })
            () -> IForgeMenuType.create(NewEnchantmentMenu::new)
    );
}
