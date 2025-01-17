package net.neava.eternalsplendor.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.neava.eternalsplendor.EternalSplendorMod;

import java.util.List;

public class Enchantments {
    public static void register(IEventBus eventBus){
        ENCHANTMENTS.register(eventBus);
    }

    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, EternalSplendorMod.MODID);

    //public static final RegistryObject<Enchantment> TEST_ENCHANT = ENCHANTMENTS.register("test_enchant", () -> new EnchantmentBase(Enchantment.Rarity.COMMON, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND}, "Basic"));

    //public static final RegistryObject<Enchantment> TEST_ENCHANT2 = ENCHANTMENTS.register("test_enchant2", () -> new EnchantmentBase(Enchantment.Rarity.COMMON, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND}, "NotBasic"));
}

