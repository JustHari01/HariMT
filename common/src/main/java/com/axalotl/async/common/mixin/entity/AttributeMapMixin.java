package com.axalotl.async.common.mixin.entity;

import com.axalotl.async.common.parallelised.ConcurrentCollections;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Makes AttributeMap collections thread-safe for async entity ticking.
 * Prevents ConcurrentModificationException when multiple entities update attributes simultaneously.
 */
@Mixin(AttributeMap.class)
public class AttributeMapMixin {

    @Shadow
    private final Set<AttributeInstance> dirtyAttributes = ConcurrentHashMap.newKeySet();

    @Shadow
    private final Map<Attribute, AttributeInstance> attributes = ConcurrentCollections.newHashMap();
}
