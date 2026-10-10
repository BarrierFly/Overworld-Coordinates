package com.benonardo.overworld_coordinates.mixin;

import com.benonardo.overworld_coordinates.CoordinateLines;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.components.debug.DebugEntryPosition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

/**
 * Appends the extra coordinate lines to the vanilla F3 screen.
 * <p>
 * Since 1.21.10 the F3 screen is split into small {@code DebugScreenEntry} implementations that
 * publish their lines into a named group, so the position group is extended instead.
 * <p>
 * This variant targets 1.21.10, where the group key type is still called {@code ResourceLocation}.
 * The only difference to the newer variant is the fully qualified {@link At} target below, which
 * has to spell the group key type out.
 */
@Mixin(DebugEntryPosition.class)
public class DebugEntryPositionMixin {

    @ModifyExpressionValue(method = "display", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/debug/DebugScreenDisplayer;addToGroup(Lnet/minecraft/resources/ResourceLocation;Ljava/util/Collection;)V", ordinal = 0))
    private List<String> appendOverworldCoordinates(List<String> original) {
        List<String> lines = new ArrayList<>(original);
        lines.addAll(CoordinateLines.build());
        return lines;
    }

}
