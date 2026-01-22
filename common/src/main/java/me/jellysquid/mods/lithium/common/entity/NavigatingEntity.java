package me.jellysquid.mods.lithium.common.entity;

import net.minecraft.world.entity.ai.navigation.PathNavigation;

public interface NavigatingEntity {
    PathNavigation getRegisteredNavigation();
}
