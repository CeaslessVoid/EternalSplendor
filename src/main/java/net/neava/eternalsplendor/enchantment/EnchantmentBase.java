package net.neava.eternalsplendor.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentBase extends Enchantment {

    private final String series;

    private final String identity;

    protected EnchantmentBase(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots, String series, String identity) {
        super(pRarity, pCategory, pApplicableSlots);

        this.series = series;

        this.identity = identity;
    }

    public String getSeries() {
        return series;
    }

    public String getIdentity() {
        return identity;
    }

    public int getMinCost(int pLevel) {
        return 1;
    }

    public int getMaxCost(int pLevel) {
        return 50;
    }
}
