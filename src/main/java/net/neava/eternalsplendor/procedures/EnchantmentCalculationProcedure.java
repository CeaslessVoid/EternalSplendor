package net.neava.eternalsplendor.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.neava.eternalsplendor.EternalSplendorMod;
import net.neava.eternalsplendor.enchantment.Enchantments;

import javax.swing.text.html.parser.Entity;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = EternalSplendorMod.MODID)
public class EnchantmentCalculationProcedure {

    public EnchantmentCalculationProcedure(){
    }

    @SubscribeEvent
    public static void onLivingEquipmentChance(LivingEquipmentChangeEvent event){

        LivingEntity entity = event.getEntity();

        if (entity.level().isClientSide()) return;

        if (entity instanceof Player player){

            int maxHealthBonus = 0;
            int speedBonus = 0;

            for (ItemStack armor : player.getArmorSlots()) {
                maxHealthBonus += EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MAX_HEALTH.get(), armor) * 2;

                speedBonus += EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SPEED.get(), armor) * 3;
            }


            // Max Health
            AttributeInstance maxHealthAttribute = player.getAttribute(Attributes.MAX_HEALTH);

            UUID healthUUID = UUID.fromString("91abcd15-7c22-4c5b-bf37-03c2b5dbd519");

            maxHealthAttribute.removeModifier(healthUUID);

            if (maxHealthBonus > 0) {
                AttributeModifier modifier = new AttributeModifier(
                        healthUUID,
                        "Max Health Enchantment Bonus",
                        maxHealthBonus,
                        AttributeModifier.Operation.ADDITION
                );
                maxHealthAttribute.addPermanentModifier(modifier);
            }

            if (player.getHealth() > player.getMaxHealth()){
                player.setHealth(player.getMaxHealth());
            }

            // Speed

            AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
            UUID speedUUID = UUID.fromString("a1b2c3d4-5678-1234-5678-abcdef123456");

            movementSpeed.removeModifier(speedUUID);

            if (speedBonus > 0) {

                if (speedBonus > 0) {
                    AttributeModifier speedModifier = new AttributeModifier(
                            speedUUID,
                            "Speed Enchantment Bonus",
                            0.01 * speedBonus,
                            AttributeModifier.Operation.ADDITION
                    );
                    movementSpeed.addPermanentModifier(speedModifier);
                }
            }

        }

    }
}
