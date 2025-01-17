package net.neava.eternalsplendor.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentBase extends Enchantment {

    private final String series;

    protected EnchantmentBase(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots, String series) {
        super(pRarity, pCategory, pApplicableSlots);

        this.series = series;
    }

    public String getSeries() {
        return series;
    }

    public int getMinCost(int pLevel) {
        return 1;
    }

    public int getMaxCost(int pLevel) {
        return 50;
    }
}
