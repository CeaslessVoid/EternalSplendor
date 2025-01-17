package net.neava.eternalsplendor.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;
import java.util.function.Predicate;

public class CatalystItem extends Item {

    public final double expMod;
    public final int chanceMod;
    public final boolean hasRule;
    public final Predicate<Enchantment> rule;
    public final String location;

    public CatalystItem(Properties pProperties, double expMod, int chanceMod, boolean hasRule, Predicate<Enchantment> rule, String location) {
        super(pProperties);

        this.expMod = expMod;
        this.chanceMod = chanceMod;
        this.hasRule = hasRule;
        this.rule = rule != null ? rule : item -> true;
        this.location = location;
    }

    public double getExpMod() {
        return expMod;
    }

    public int getChanceMod() {
        return chanceMod;
    }

    public boolean isHasRule(){
        return hasRule;
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
        pTooltipComponents.add(Component.translatable("tooltip.neava_eternalsplendor.catalyst"));

    }
}
