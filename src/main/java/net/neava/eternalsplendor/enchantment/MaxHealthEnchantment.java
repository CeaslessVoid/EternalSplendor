package net.neava.eternalsplendor.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class MaxHealthEnchantment extends EnchantmentBase{


    protected MaxHealthEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots, "Basic", null);
    }

    public int getMinCost(int level) {
        return 15 + (level - 1) * 9;
    }

    public int getMaxCost(int level) {
        return this.getMinCost(level) + 15;
    }

    public int getMaxLevel() {
        return 3;
    }

}
