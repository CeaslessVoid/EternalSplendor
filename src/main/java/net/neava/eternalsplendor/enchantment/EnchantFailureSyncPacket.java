package net.neava.eternalsplendor.enchantment;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;
import net.neava.eternalsplendor.EternalSplendorMod;
import net.neava.eternalsplendor.world.inventory.NewEnchantmentMenu;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnchantFailureSyncPacket {

    private final boolean failed;

    public EnchantFailureSyncPacket(boolean failed) {
        this.failed = failed;
    }

    public EnchantFailureSyncPacket(FriendlyByteBuf buffer) {
        this.failed = buffer.readBoolean();
    }

    public static void buffer(EnchantFailureSyncPacket message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.failed);
    }

    public static void handler(EnchantFailureSyncPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {

            if (Minecraft.getInstance().player.containerMenu instanceof NewEnchantmentMenu menu)
            {
               menu.hasFailed = message.failed;
            }

        });
        context.setPacketHandled(true);
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        EternalSplendorMod.addNetworkMessage(EnchantFailureSyncPacket.class, EnchantFailureSyncPacket::buffer, EnchantFailureSyncPacket::new, EnchantFailureSyncPacket::handler);
    }

}
