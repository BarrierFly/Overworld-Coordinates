package com.benonardo.overworld_coordinates;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class OWCMixinPlugin implements IMixinConfigPlugin {

    /**
     * The vanilla F3 screen mixin and the BetterF3 mixin are mutually exclusive: BetterF3 replaces the
     * vanilla debug screen entirely, so only one of them has to be applied.
     */
    private static final String VANILLA_F3_MIXIN_PREFIX = "com.benonardo.overworld_coordinates.mixin.Debug";

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return !FabricLoader.getInstance().isModLoaded("betterf3") || !mixinClassName.startsWith(VANILLA_F3_MIXIN_PREFIX);
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
