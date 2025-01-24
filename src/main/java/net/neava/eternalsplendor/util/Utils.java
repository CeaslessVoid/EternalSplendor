package net.neava.eternalsplendor.util;

import net.minecraft.world.item.*;

public class Utils {

    public static boolean isEncahntable(ItemStack itemStack)
    {
        return isWeapon(itemStack) || isArmor(itemStack) || isMisc(itemStack);
    }

    public static boolean isWeapon(ItemStack itemStack)
    {
        return isRangedWeapon(itemStack) || isMeleeWeapon(itemStack);
    }

    public static boolean isRangedWeapon(ItemStack itemStack)
    {
        Item item = itemStack.getItem();
        return item instanceof BowItem || item instanceof CrossbowItem;
    }

    public static boolean isMeleeWeapon(ItemStack itemStack)
    {
        Item item = itemStack.getItem();
        return item instanceof SwordItem || item instanceof AxeItem || item instanceof TridentItem;
    }

    public static boolean isMisc(ItemStack itemStack)
    {
        Item item = itemStack.getItem();
        return item instanceof DiggerItem || item instanceof FishingRodItem || item instanceof ShieldItem  || item instanceof ElytraItem;
    }

    public static boolean isArmor(ItemStack itemStack)
    {
        Item item = itemStack.getItem();
        return item instanceof ArmorItem;
    }
}
