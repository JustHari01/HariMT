package com.axalotl.async.common.mixin.entity;

import com.axalotl.async.common.parallelised.ConcurrentCollections;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import java.util.UUID;

/**
 * Makes AttributeInstance modifier collections thread-safe for async entity ticking.
 * Prevents ConcurrentModificationException when modifiers are added/removed during async ticks.
 */
@Mixin(AttributeInstance.class)
public class AttributeInstanceMixin {

    @Shadow
    private final Map<AttributeModifier.Operation, Map<UUID, AttributeModifier>> modifiersByOperation = ConcurrentCollections.newHashMap();

    @Shadow
    private final Map<UUID, AttributeModifier> modifierById = ConcurrentCollections.newHashMap();

    @Shadow
    private final Map<UUID, AttributeModifier> permanentModifiers = ConcurrentCollections.newHashMap();
}
