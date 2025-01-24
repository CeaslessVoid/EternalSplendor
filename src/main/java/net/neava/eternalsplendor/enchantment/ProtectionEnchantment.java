package net.neava.eternalsplendor.enchantment;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class ProtectionEnchantment extends EnchantmentBase{

    public final Type type;

    public ProtectionEnchantment(Rarity pRarity, Type pType, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pType == ProtectionEnchantment.Type.FALL ? EnchantmentCategory.ARMOR_FEET : EnchantmentCategory.ARMOR, pApplicableSlots, "Basic", null);
        this.type = pType;
    }

    public int getMinCost(int pEnchantmentLevel) {
        return this.type.getMinCost() + (pEnchantmentLevel - 1) * this.type.getLevelCost();
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return this.getMinCost(pEnchantmentLevel) + this.type.getLevelCost();
    }

    public int getMaxLevel() {
        return 4;
    }

    public boolean checkCompatibility(@NotNull Enchantment pEnch) {
        if (pEnch instanceof ProtectionEnchantment protectionenchantment) {
            if (this.type == protectionenchantment.type) {
                return false;
            } else {
                return this.type == Type.FALL || protectionenchantment.type == Type.FALL;
            }
        } else {
            return super.checkCompatibility(pEnch);
        }
    }

    public int getDamageProtection(int pLevel, DamageSource pSource) {
        if (pSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return 0;
        } else if (this.type == Type.ALL) {
            return pSource.getDirectEntity() instanceof LivingEntity ? pLevel / 2 : 0; // For some reason the damage reduc is 4x
        } else if (this.type == Type.FIRE && pSource.is(DamageTypeTags.IS_FIRE)) {
            return pLevel;
        } else if (this.type == Type.FALL && pSource.is(DamageTypeTags.IS_FALL)) {
            return pLevel * 3;
        } else if (this.type == Type.EXPLOSION && pSource.is(DamageTypeTags.IS_EXPLOSION)) {
            return pLevel / 2;
        } else {
            return this.type == Type.PROJECTILE && pSource.is(DamageTypeTags.IS_PROJECTILE) ? pLevel / 2 : 0;
        }
    }

    public static enum Type {
        ALL(1, 11), // Now Melee
        FIRE(10, 8), // Now elemental. We will calc this later
        FALL(5, 6), // Kept the same
        EXPLOSION(5, 8), // Kept the same for now (will probably rework later)
        PROJECTILE(3, 6); // Kept the same

        private final int minCost;
        private final int levelCost;

        private Type(int pMinCost, int pLevelCost) {
            this.minCost = pMinCost;
            this.levelCost = pLevelCost;
        }

        public int getMinCost() {
            return this.minCost;
        }

        public int getLevelCost() {
            return this.levelCost;
        }
    }
}
