package net.neava.eternalsplendor.util;

import com.google.common.collect.Lists;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.*;
import net.neava.eternalsplendor.enchantment.EnchantmentBase;

import java.util.List;
import java.util.Objects;

public class EnchantmentUtils {

    public static List<EnchantmentInstance> getAvailableEnchantmentResults(int pLevel, ItemStack pStack) {
        List<Enchantment> existingEnchantments = EnchantmentHelper.getEnchantments(pStack).keySet().stream().toList();
        List<EnchantmentInstance> list = Lists.newArrayList();
        Item item = pStack.getItem();

        for(Enchantment enchantment : BuiltInRegistries.ENCHANTMENT) {
            if (!enchantment.isCurse() && enchantment.isDiscoverable() && enchantment.canEnchant(pStack)) {

                boolean isCompatible = existingEnchantments.stream()
                        .allMatch(existing -> existing.isCompatibleWith(enchantment) && enchantment.isCompatibleWith(existing));

                if (isCompatible) {
                    for (int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i) {
                        if (pLevel >= enchantment.getMinCost(i) && pLevel <= enchantment.getMaxCost(i)) {
                            list.add(new EnchantmentInstance(enchantment, i));
                            break;
                        }
                    }
                }
            }
        }

        return list;
    }

    public static boolean isBannedEnchant(Enchantment enchantment)
    {
        return enchantment == Enchantments.MENDING
                || enchantment == Enchantments.UNBREAKING
                || enchantment == Enchantments.FLAMING_ARROWS
                || enchantment == Enchantments.FIRE_ASPECT
                || enchantment instanceof ProtectionEnchantment;
    }


    public static boolean isBasicEnchant(Enchantment enchantment)
    {
        if (enchantment instanceof EnchantmentBase enchantmentBase)
        {
            return Objects.equals(enchantmentBase.getSeries(), "Basic");
        }
        else
        {
            return true;
        }
    }

//    public static boolean isBookable(ItemStack itemStack)
//    {
//        return Utils.isEncahntable(itemStack) && !itemStack.isEnchanted();
//    }

}
