package net.neava.eternalsplendor.enchantment;

import net.minecraft.core.Registry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.neava.eternalsplendor.EternalSplendorMod;
import net.neava.eternalsplendor.item.SpeedEnchantment;

import java.util.List;

public class Enchantments {
    public static void register(IEventBus eventBus){
        ENCHANTMENTS.register(eventBus);
    }

    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, EternalSplendorMod.MODID);

    private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};


    public static final RegistryObject<Enchantment> MELEE_PROTECTION = ENCHANTMENTS.register("basic_melee_protect_enchant", () -> new ProtectionEnchantment(Enchantment.Rarity.COMMON, ProtectionEnchantment.Type.ALL, ARMOR_SLOTS));

    public static final RegistryObject<Enchantment> ELEMENT_PROTECTION = ENCHANTMENTS.register("basic_element_protect_enchant", () -> new ProtectionEnchantment(Enchantment.Rarity.UNCOMMON, ProtectionEnchantment.Type.FIRE, ARMOR_SLOTS));

    public static final RegistryObject<Enchantment> FALL_PROTECTION = ENCHANTMENTS.register("basic_fall_protect_enchant", () -> new ProtectionEnchantment(Enchantment.Rarity.UNCOMMON, ProtectionEnchantment.Type.FALL, ARMOR_SLOTS));

    public static final RegistryObject<Enchantment> BLAST_PROTECTION = ENCHANTMENTS.register("basic_blast_protect_enchant", () -> new ProtectionEnchantment(Enchantment.Rarity.RARE, ProtectionEnchantment.Type.EXPLOSION, ARMOR_SLOTS));

    public static final RegistryObject<Enchantment> PROJECTILE_PROTECTION = ENCHANTMENTS.register("basic_projectile_protect_enchant", () -> new ProtectionEnchantment(Enchantment.Rarity.UNCOMMON, ProtectionEnchantment.Type.PROJECTILE, ARMOR_SLOTS));

    public static final RegistryObject<Enchantment> MAX_HEALTH = ENCHANTMENTS.register("basic_maxhealth_enchant", () -> new MaxHealthEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.ARMOR, ARMOR_SLOTS));

    public static final RegistryObject<Enchantment> SPEED = ENCHANTMENTS.register("basic_speed_enchant", () -> new SpeedEnchantment(Enchantment.Rarity.COMMON, EnchantmentCategory.ARMOR, ARMOR_SLOTS));

}

