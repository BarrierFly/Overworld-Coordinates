package com.benonardo.overworld_coordinates.mixin;

import com.benonardo.overworld_coordinates.CoordinateLines;
import net.minecraft.client.gui.components.debug.DebugEntryPosition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Appends the extra coordinate lines to the vanilla F3 screen.
 * <p>
 * Since 1.21.10 the F3 screen is split into small {@code DebugScreenEntry} implementations that
 * publish their lines into a named group, so the position group is extended instead.
 * <p>
 * This variant targets 1.21.11 and newer, where {@code ResourceLocation} has been renamed to
 * {@code Identifier}. The only difference to the 1.21.10 variant is the fully qualified
 * {@link At} target below, which has to spell the group key type out.
 */
@Mixin(DebugEntryPosition.class)
public class DebugEntryPositionMixin {

    /**
     * Replaces the lines the entry publishes with the same lines plus ours.
     * <p>
     * {@code addToGroup} returns void, so the line list has to be swapped out as an argument
     * rather than by modifying the result of the call.
     */
    @ModifyArg(method = "display", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/debug/DebugScreenDisplayer;addToGroup(Lnet/minecraft/resources/Identifier;Ljava/util/Collection;)V", ordinal = 0), index = 1)
    private Collection<String> appendOverworldCoordinates(Collection<String> original) {
        List<String> lines = new ArrayList<>(original);
        lines.addAll(CoordinateLines.build());
        return lines;
    }

}
