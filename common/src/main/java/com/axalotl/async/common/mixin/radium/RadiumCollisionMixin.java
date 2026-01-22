package com.axalotl.async.common.mixin.radium;

import com.axalotl.async.common.ParallelProcessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Mixin to make Radium's collision detection thread-safe for Async's parallel entity ticking.
 * Uses chunk-based locking to prevent race conditions while still allowing parallelism
 * for entities in different chunks.
 */
@Mixin(value = Entity.class, priority = 1600)
public abstract class RadiumCollisionMixin {

    @Shadow
    protected abstract Vec3 collide(Vec3 movement);

    /**
     * Wrap the collision adjustment method with chunk-based locking.
     * This ensures thread-safety when Radium's optimized collision code runs in parallel.
     */
    @Redirect(
            method = "move",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;collide(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;"
            )
    )
    private Vec3 wrapCollideWithLock(Entity entity, Vec3 movement) {
        // If on main thread, no locking needed
        if (!ParallelProcessor.isServerExecutionThread()) {
            return this.collide(movement);
        }

        // Get chunk-based lock for this entity's position
        int chunkX = entity.getBlockX() >> 4;
        int chunkZ = entity.getBlockZ() >> 4;
        ReentrantLock lock = ParallelProcessor.getChunkLock(chunkX, chunkZ);

        lock.lock();
        try {
            return this.collide(movement);
        } finally {
            lock.unlock();
        }
    }
}
