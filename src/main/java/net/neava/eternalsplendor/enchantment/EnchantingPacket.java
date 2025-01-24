package net.neava.eternalsplendor.enchantment;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.neava.eternalsplendor.EternalSplendorMod;
import net.neava.eternalsplendor.world.inventory.NewEnchantmentMenu;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnchantingPacket {

    private final int buttonID, x, y, z;


    // Constructor for sending the packet (if needed in the future)
    public EnchantingPacket(int buttonID, int x, int y, int z) {
        this.buttonID = buttonID;
        this.x = x;
        this.y = y;
        this.z = z;

    }

    public EnchantingPacket(FriendlyByteBuf buffer) {
        this.buttonID = buffer.readInt();
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();

    }

    public static void buffer(EnchantingPacket message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.buttonID);
        buffer.writeInt(message.x);
        buffer.writeInt(message.y);
        buffer.writeInt(message.z);
    }

    public static void handler(EnchantingPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player entity = context.getSender();
            int buttonID = message.buttonID;
            int x = message.x;
            int y = message.y;
            int z = message.z;

            handleButtonAction(entity, buttonID, x, y, z);
        });
        context.setPacketHandled(true);
    }

    public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
        Level world = entity.level();

        if (!world.hasChunkAt(new BlockPos(x, y, z)))
            return;
        if (entity.containerMenu instanceof NewEnchantmentMenu menu) {
            boolean failed = menu.execute();

            EternalSplendorMod.PACKET_HANDLER.sendTo(
                    new EnchantFailureSyncPacket(failed),
                    ((ServerPlayer) entity).connection.connection,
                    NetworkDirection.PLAY_TO_CLIENT
            );

        }

    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        EternalSplendorMod.addNetworkMessage(EnchantingPacket.class, EnchantingPacket::buffer, EnchantingPacket::new, EnchantingPacket::handler);
    }
}
