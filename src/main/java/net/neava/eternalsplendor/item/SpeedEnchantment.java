package net.neava.eternalsplendor.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.neava.eternalsplendor.enchantment.EnchantmentBase;

public class SpeedEnchantment extends EnchantmentBase {

    public SpeedEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots, "Basic", null);
    }

    @Override
    public int getMinCost(int level) {
        return 10 + (level - 1) * 9;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 15;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
