package net.neava.eternalsplendor.world.inventory;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neava.eternalsplendor.init.ModMenus;
import net.neava.eternalsplendor.util.ModTags;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class NewEnchantmentMenu extends AbstractContainerMenu {

    private final Container enchantSlots = new SimpleContainer(5) {
        /**
         * For block entities, ensures the chunk containing the block entity is saved to disk later - the game won't think
         * it hasn't changed and skip it.
         */
        public void setChanged() {
            super.setChanged();
            NewEnchantmentMenu.this.slotsChanged(this);
        }
    };

    public final static HashMap<String, Object> guistate = new HashMap<>();

    public boolean enchantable = false;


    private final ContainerLevelAccess access;

    public NewEnchantmentMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf friendlyByteBuf) {
        super(ModMenus.NEW_ENCHANTMENT_MENU.get(), pContainerId);

        this.access = ContainerLevelAccess.NULL;

        this.addSlot(new Slot(this.enchantSlots, 0, 8, 33) {
            public boolean mayPlace(@NotNull ItemStack target) {
                return true;
            }
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.enchantSlots, 1, 62, 33) {
            public boolean mayPlace(@NotNull ItemStack mainFuel) {
                return mainFuel.is(ModTags.Items.ETERNALSPLENDOR_ENCHANTING_FUEL);
            }
        });
        this.addSlot(new Slot(this.enchantSlots, 2, 104, 33) {
            public boolean mayPlace(@NotNull ItemStack catalyst) {
                return catalyst.is(ModTags.Items.ETERNALSPLENDOR_ENCHANTING_CATALYST);
            }
        });
        this.addSlot(new Slot(this.enchantSlots, 3, 127, 33) {
            public boolean mayPlace(@NotNull ItemStack catalyst) {
                return catalyst.is(ModTags.Items.ETERNALSPLENDOR_ENCHANTING_CATALYST);
            }
        });
        this.addSlot(new Slot(this.enchantSlots, 4, 150, 33) {
            public boolean mayPlace(@NotNull ItemStack catalyst) {
                return catalyst.is(ModTags.Items.ETERNALSPLENDOR_ENCHANTING_CATALYST);
            }
        });


        // Inventory Slots
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(pPlayerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(pPlayerInventory, k, 8 + k * 18, 142));
        }

    }

    public void slotsChanged(@NotNull Container pInventory) {
        if (pInventory == this.enchantSlots)
        {
            ItemStack itemstack = pInventory.getItem(0);
            ItemStack itemstack1 = pInventory.getItem(1);

            enchantable = !itemstack.isEmpty() && isEnchantable(itemstack) && !itemstack1.isEmpty();

            this.broadcastChanges();

            // Temp

//            List<EnchantmentInstance> temp = EnchantmentUtils.getAvailableEnchantmentResults(15, itemstack, true);
//
//            for (EnchantmentInstance instance : temp) {
//                String enchantmentName = instance.enchantment.getFullname(instance.level).getString();
//                System.out.println("Enchantment: " + enchantmentName);
//                System.out.println("---------------------------");
//            }
        }
    }

    public void removed(@NotNull Player pPlayer) {
        super.removed(pPlayer);

        if (pPlayer instanceof ServerPlayer serverPlayer) {
            if (!serverPlayer.isAlive() || serverPlayer.hasDisconnected()) {
                for (int j = 0; j < enchantSlots.getContainerSize(); ++j) {
                    ItemStack stack = enchantSlots.removeItemNoUpdate(j);
                    if (!stack.isEmpty()) {
                        pPlayer.drop(stack, false);
                    }
                }
            } else {
                Inventory inventory = pPlayer.getInventory();
                for (int i = 0; i < enchantSlots.getContainerSize(); ++i) {
                    ItemStack stack = enchantSlots.removeItemNoUpdate(i);
                    if (!stack.isEmpty()) {
                        inventory.placeItemBackInInventory(stack);
                    }
                }
            }
        }

        enchantSlots.clearContent();
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);

        if (slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();

            if (pIndex >= 5) {
                if (stackInSlot.is(ModTags.Items.ETERNALSPLENDOR_ENCHANTING_FUEL)) {
                    if (!this.moveItemStackTo(stackInSlot, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stackInSlot.is(ModTags.Items.ETERNALSPLENDOR_ENCHANTING_CATALYST)) {
                    if (!this.moveItemStackTo(stackInSlot, 2, 5, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (!this.moveItemStackTo(stackInSlot, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else {
                if (!this.moveItemStackTo(stackInSlot, 5, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            if (stackInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(this.access, pPlayer, Blocks.ENCHANTING_TABLE);
    }



    // Enchanting Stuffs

    public boolean isEnchantable(ItemStack itemStack)
    {
        return itemStack.getMaxStackSize() == 1 && itemStack.isDamageableItem() && itemStack.getEnchantmentTags().size() < 4;
    }
}
