package net.neava.eternalsplendor.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neava.eternalsplendor.EternalSplendorMod;

public class ModTags {

    public static class Items {

        public static final TagKey<Item> ETERNALSPLENDOR_ENCHANTING_FUEL = tag("eternalsplendor_enchanting_fuel");

        public static final TagKey<Item> ETERNALSPLENDOR_ENCHANTING_CATALYST = tag("eternalsplendor_enchanting_catalyst");

        private static TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(EternalSplendorMod.MODID, name));
        }

    }

}
