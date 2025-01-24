package net.neava.eternalsplendor.world.inventory;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.neava.eternalsplendor.enchantment.EnchantmentBase;
import net.neava.eternalsplendor.init.ModMenus;
import net.neava.eternalsplendor.item.CatalystItem;
import net.neava.eternalsplendor.item.EnchantingFuelItem;
import net.neava.eternalsplendor.item.ModItems;
import net.neava.eternalsplendor.util.EnchantmentUtils;
import net.neava.eternalsplendor.util.ModTags;
import net.neava.eternalsplendor.util.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

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
    public boolean hasFailed = false;
    public int x, y, z;
    public BlockPos pos;

    public final Player entity;
    public final Level world;

    private ContainerLevelAccess access;

    public NewEnchantmentMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf friendlyByteBuf) {
        super(ModMenus.NEW_ENCHANTMENT_MENU.get(), pContainerId);

        this.entity = pPlayerInventory.player;
        this.world = pPlayerInventory.player.level();

        BlockPos pos = null;

        pos = friendlyByteBuf.readBlockPos();
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        access = ContainerLevelAccess.create(world, pos);
        //access = ContainerLevelAccess.NULL;

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
            this.access.execute((world, pos) -> {

                ItemStack itemstack = pInventory.getItem(0);
                ItemStack itemstack1 = pInventory.getItem(1);

                enchantable = !itemstack.isEmpty() && isEnchantable(itemstack) && !itemstack1.isEmpty();

                hasFailed = false;

                this.broadcastChanges();
            });
        }
    }

    public boolean execute(){
        AtomicBoolean failed = new AtomicBoolean(false);

        this.access.execute((world, pos) -> {

            ItemStack target = this.enchantSlots.getItem(0);

            ItemStack soul = this.enchantSlots.getItem(1);

            if (!(soul.getItem() instanceof EnchantingFuelItem enchantingFuelItem)){
                failed.set(true);
                this.broadcastChanges();
                return;
            }

            if (enchantingFuelItem == ModItems.RUNICPAGE.get())
            {
                if (!target.isEnchanted())
                {
                    failed.set(true);
                    this.broadcastChanges();
                    return;
                }

                ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);

                Map<Enchantment, Integer> enchantments = target.getAllEnchantments();
                for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                    EnchantedBookItem.addEnchantment(enchantedBook, new EnchantmentInstance(entry.getKey(), entry.getValue()));
                }

                this.enchantSlots.setItem(1, enchantedBook);

                EnchantmentHelper.setEnchantments(Collections.emptyMap(), target);
                target.removeTagKey("Enchantments");
                target.removeTagKey("StoredEnchantments");

                this.enchantSlots.setItem(0, target);

                this.enchantSlots.setChanged();
                this.slotsChanged(this.enchantSlots);
                return;

            }

            if (target.getAllEnchantments().size() >= 4)
            {
                failed.set(true);
                this.broadcastChanges();
                return;
            }

            int minLevel = enchantingFuelItem.getMinLevel();
            int maxLevel = enchantingFuelItem.getMaxLevel();

            int enchantingLevel = minLevel + (int) (Math.random() * (maxLevel - minLevel + 1));

            double totalExpMod = 0.0;
            List<Predicate<Enchantment>> rules = new ArrayList<>();


            boolean catalystUsed = false;
            for (int i = 2; i <= 4; i++){
                ItemStack catalyst = this.enchantSlots.getItem(i);
                if (!catalyst.isEmpty() && catalyst.getItem() instanceof CatalystItem catalystItem) {
                    totalExpMod += catalystItem.getExpMod();

                    if (catalystItem.isHasRule()) {
                        rules.add(catalystItem.rule);
                        catalystUsed = true;
                    }
                }
            }
            if (catalystUsed) {
                rules.add(enchantment -> enchantment instanceof EnchantmentBase);
            }

            float j = 0;

            for(BlockPos blockpos : EnchantmentTableBlock.BOOKSHELF_OFFSETS) {
                if (EnchantmentTableBlock.isValidBookShelf(world, pos, blockpos)) {
                    j += world.getBlockState(pos.offset(blockpos)).getEnchantPowerBonus(world, pos.offset(blockpos));
                }
            }

            if (j > 15){
                j = 15;
            }

            totalExpMod += (j / 100f) * 5;

            enchantingLevel = (int) Math.round(enchantingLevel * (1 + totalExpMod));

            rules.add(enchantingFuelItem.rule);

            List<EnchantmentInstance> enchantments = EnchantmentUtils.getAvailableEnchantmentResults(enchantingLevel, target);

            enchantments.removeIf(enchantmentInstance -> {
                Enchantment enchantment = enchantmentInstance.enchantment;
                if (target.getEnchantmentLevel(enchantment) > 0) {
                    return true;
                }

                for (Predicate<Enchantment> rule : rules) {
                    if (!rule.test(enchantment)) {
                        return true;
                    }
                }
                return EnchantmentUtils.isBannedEnchant(enchantment);
            });

            if (enchantments.isEmpty()) {
                failed.set(true);
                this.broadcastChanges();
                return;
            }

            EnchantmentInstance selectedEnchantment = enchantments.get((int) (Math.random() * enchantments.size()));

            target.enchant(selectedEnchantment.enchantment, selectedEnchantment.level);

            this.enchantSlots.setItem(0, target);

            //this.broadcastChanges();
            //this.slots.get(0).setChanged();

            for (int i = 1; i <= 4; i++) {
                ItemStack stack = this.enchantSlots.getItem(i).copy();
                if (!stack.isEmpty()) {
                    stack.shrink(1);
                    if (stack.getCount() <= 0) {
                        //this.enchantSlots.setItem(i, ItemStack.EMPTY);
                        stack = ItemStack.EMPTY;
                    }
                    this.enchantSlots.setItem(i, stack);
                    //this.slots.get(i).setChanged();
                }
            }

            this.entity.awardStat(Stats.ENCHANT_ITEM);
            if (this.entity instanceof ServerPlayer) {
                CriteriaTriggers.ENCHANTED_ITEM.trigger((ServerPlayer)this.entity, target, enchantingLevel);
            }

            this.enchantSlots.setChanged();
            this.slotsChanged(this.enchantSlots);
        });

        this.broadcastChanges();
        return failed.get();
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

        //execute();

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
        //return itemStack.getMaxStackSize() == 1;//&& itemStack.getAllEnchantments().size() < 4;

        return Utils.isEncahntable(itemStack);
    }
}
