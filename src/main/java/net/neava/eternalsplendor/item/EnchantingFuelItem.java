package net.neava.eternalsplendor.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class EnchantingFuelItem extends Item {

    public final int minLevel;

    public final int maxLevel;

    public final Predicate<Enchantment> rule;

    public final String location;

    //public final class accessorClass;

    public EnchantingFuelItem(Properties pProperties, int minLevel, int maxLevel, Predicate<Enchantment> rule, String location) {
        super(pProperties);

        this.minLevel = minLevel;
        this.maxLevel = maxLevel;

        this.rule = rule != null ? rule : item -> true;
        this.location = location;

    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getMinLevel() {
        return minLevel;
    }


    public boolean matchesRule(Enchantment enchantment) {
        return rule.test(enchantment);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

        if (location != null) {
            pTooltipComponents.add(Component.translatable(location));
        }

        pTooltipComponents.add(Component.literal(""));
        pTooltipComponents.add(Component.translatable("tooltip.neava_eternalsplendor.enchantingfuel"));

    }
}
