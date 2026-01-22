package com.axalotl.async.common.mixin.c2me;

import com.bawnorton.mixinsquared.api.MixinCanceller;

import java.util.List;

public class AsyncModMixinCanceller implements MixinCanceller {
    private boolean LITHIUM = false;
    private boolean VMP = false;

    @Override
    public boolean shouldCancel(List<String> targetClassNames, String mixinClassName) {
        if ((mixinClassName.contains("lithium") || mixinClassName.contains("radium")) && !mixinClassName.contains("async")) {
            LITHIUM = true;
        }
        if (mixinClassName.contains("vmp") && !mixinClassName.contains("async")) {
            VMP = true;
        }
        
        // C2ME Conflicts
        if (mixinClassName.equals("com.ishland.c2me.fixes.general.threading_issues.mixin.asynccatchers.MixinThreadedAnvilChunkStorage") ||
            mixinClassName.equals("com.ishland.c2me.fixes.worldgen.threading_issues.mixin.threading_detections.random_instances.MixinWorld") ||
            mixinClassName.equals("com.ishland.c2me.opts.chunk_access.mixin.async_chunk_request.MixinServerChunkManager")) {
             return true;
        }

        // Async Mixins - only apply if the target mod is present
        if (mixinClassName.endsWith("RadiumServerChunkCacheMixin") ||
            mixinClassName.endsWith("RadiumServerLevelMixin") ||
            mixinClassName.endsWith("RadiumCollisionMixin")) {
            return !LITHIUM;
        }
        if (mixinClassName.endsWith("VMPChunkMapMixin")) {
            return !VMP;
        }
        
        return mixinClassName.endsWith("com.cupboard.mixin.ServerAddEntityMixin");
    }
}

