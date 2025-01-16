package net.neava.eternalsplendor.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract boolean isEnchanted();

    @Shadow @Nullable private CompoundTag tag;

    /**
     * @author Neava
     * @reason New Enchantment functions require at least 4 enchants
     */
//    @Overwrite
//    public boolean isEnchantable() {
//        if (!this.getItem().isEnchantable((ItemStack) (Object) this)) {
//            return false;
//        } else if (this.tag != null && this.tag.contains("Enchantments", 9)) {
//            return this.tag.getList("Enchantments", 10).size() <= 4;
//        }
//        return true;
//    }
}

