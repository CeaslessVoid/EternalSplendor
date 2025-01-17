package net.neava.eternalsplendor.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.neava.eternalsplendor.EternalSplendorMod;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EternalSplendorMod.MODID);

    public static final RegistryObject<CreativeModeTab> ETERNAL_SPLENDOR_TAB = CREATIVE_MODE_TABS.register("eternalsplendor_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.LAPISDUST.get()))
                    .title(Component.translatable("creativetab.eternalsplendor_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        for(RegistryObject<Item> item : ModItems.ITEMS.getEntries()) {
                            pOutput.accept(item.get());
                        }
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
