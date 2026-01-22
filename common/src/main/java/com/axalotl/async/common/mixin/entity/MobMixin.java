package com.axalotl.async.common.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

/**
 * MobMixin with synchronized blocks for equipment operations.
 * 
 * IMPORTANT: The static lock is necessary because multiple mobs can try to
 * pick up the same ItemEntity simultaneously. Without synchronization,
 * both mobs may pass the isRemoved() check and try to process the item.
 */
@Mixin(Mob.class)
public class MobMixin {

    @Unique
    private static final Object async$lock = new Object();

    @WrapMethod(method = "equipItemIfPossible")
    private ItemStack tryEquip(ItemStack stack, Operation<ItemStack> original) {
        synchronized (async$lock) {
            return original.call(stack);
        }
    }

    @WrapMethod(method = "pickUpItem")
    private void pickUpItem(ItemEntity itemEntity, Operation<Void> original) {
        synchronized (async$lock) {
            // Check if item was already picked up by another mob
            if (itemEntity != null && !itemEntity.isRemoved()) {
                original.call(itemEntity);
            }
        }
    }

    @WrapMethod(method = "setItemSlot")
    private void equipStack(EquipmentSlot slot, ItemStack stack, Operation<Void> original) {
        synchronized (async$lock) {
            original.call(slot, stack);
        }
    }

    @WrapMethod(method = "setItemSlotAndDropWhenKilled")
    private void equipLootStack(EquipmentSlot slot, ItemStack stack, Operation<Void> original) {
        synchronized (async$lock) {
            original.call(slot, stack);
        }
    }
}