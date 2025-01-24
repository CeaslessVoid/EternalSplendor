package net.neava.eternalsplendor.mixin;

import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.neava.eternalsplendor.world.inventory.NewEnchantmentMenu;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(EnchantmentTableBlock.class)
public abstract class EnchantmentTableBlockMixin {

    /**
     * @author Neava
     * @reason As we are doing a total rework on enchants, we will need a whole new enchanting system and thus enchanting table. Done here for max compatibility
     */
    @Overwrite
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        //Component component = ((Nameable)blockentity).getDisplayName();

        if (pPlayer instanceof ServerPlayer player) {
            NetworkHooks.openScreen(player, new MenuProvider() {
                @Override
                public @NotNull Component getDisplayName() {
                    return Component.literal("");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
                    return new NewEnchantmentMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pPos));
                }
            }, pPos);
        }
        return InteractionResult.SUCCESS;

//        if (pLevel.isClientSide) {
//            return InteractionResult.SUCCESS;
//        } else {
//            pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
//            return InteractionResult.CONSUME;
//        }
    }

//    /**
//     * @author Neava
//     * @reason Bleh
//     */
//    @Overwrite
//    public @Nullable MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
//        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
//        if (blockEntity instanceof EnchantmentTableBlockEntity enchantmentTableBlockEntity) {
//            return new SimpleMenuProvider(
//                (containerId, inventory, player) ->
//                {
//                    return new NewEnchantmentMenu(containerId, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pPos));
//                },
//                Component.literal("")
//            );
//        }
//        else {
//        return null;
//        }
//    }

}
