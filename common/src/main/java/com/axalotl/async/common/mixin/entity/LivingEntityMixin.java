package com.axalotl.async.common.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Enhanced LivingEntityMixin with thread-safe effect management.
 * Uses ConcurrentHashMap for thread-safe effect storage.
 * Removed synchronized blocks to prevent deadlock with Radium.
 */
@Mixin(value = LivingEntity.class, priority = 1001)
public abstract class LivingEntityMixin extends Entity {

    @Shadow
    @Final
    @Mutable
    private Map<MobEffect, MobEffectInstance> activeEffects;

    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(EntityType<?> entityType, Level level, CallbackInfo ci) {
        // ConcurrentHashMap provides thread-safety without blocking
        this.activeEffects = new ConcurrentHashMap<>();
    }

    // Null-safety wrapper for tickEffects - check if MobEffectInstance is null
    // before ticking
    @WrapOperation(method = "tickEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffectInstance;tick(Lnet/minecraft/world/entity/LivingEntity;Ljava/lang/Runnable;)Z"))
    private boolean tickEffectsNullCheck(MobEffectInstance instance, LivingEntity entity, Runnable runnable,
            Operation<Boolean> original) {
        if (instance != null) {
            return original.call(instance, entity, runnable);
        } else {
            return false;
        }
    }

    // Cancel onEffectRemoved if effectInstance is null to prevent NPE
    @Inject(method = "onEffectRemoved", at = @At("HEAD"), cancellable = true)
    private void onEffectRemoved(MobEffectInstance effectInstance, CallbackInfo ci) {
        if (effectInstance == null) {
            ci.cancel();
        }
    }

    // Null-safety check for onClimbable
    @Inject(method = "onClimbable", at = @At("HEAD"), cancellable = true)
    private void onClimbable(CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = this.getFeetBlockState();
        if (blockState == null)
            cir.setReturnValue(false);
    }
}
