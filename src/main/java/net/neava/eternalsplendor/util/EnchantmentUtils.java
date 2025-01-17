package net.neava.eternalsplendor.util;

import com.google.common.collect.Lists;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.neava.eternalsplendor.enchantment.EnchantmentBase;

import java.util.List;
import java.util.Objects;

public class EnchantmentUtils {

    public static List<EnchantmentInstance> getAvailableEnchantmentResults(int pLevel, ItemStack pStack, boolean pAllowTreasure) {
        List<EnchantmentInstance> list = Lists.newArrayList();
        Item item = pStack.getItem();

        for(Enchantment enchantment : BuiltInRegistries.ENCHANTMENT) {
            if ((!enchantment.isTreasureOnly() || pAllowTreasure) && enchantment.isDiscoverable() && (enchantment.canApplyAtEnchantingTable(pStack)) && isBasicEnchant(enchantment)) {
                for(int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i) {
                    if (pLevel >= enchantment.getMinCost(i) && pLevel <= enchantment.getMaxCost(i)) {
                        list.add(new EnchantmentInstance(enchantment, i));
                        break;
                    }
                }
            }
        }

        return list;
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

}
