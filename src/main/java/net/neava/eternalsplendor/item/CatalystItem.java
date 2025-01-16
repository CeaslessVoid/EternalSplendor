package net.neava.eternalsplendor.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.function.Predicate;

public class CatalystItem extends Item {

    public final float expMod;
    public final int chanceMod;
    public final Predicate<Enchantment> rule;

    public CatalystItem(Properties pProperties, float expMod, int chanceMod, Predicate<Enchantment> rule) {
        super(pProperties);

        this.expMod = expMod;
        this.chanceMod = chanceMod;
        this.rule = rule != null ? rule : item -> true;
    }

    public float getExpMod() {
        return expMod;
    }

    public int getChanceMod() {
        return chanceMod;
    }

    public boolean matchesRule(Enchantment enchantment) {
        return rule.test(enchantment);
    }

}
