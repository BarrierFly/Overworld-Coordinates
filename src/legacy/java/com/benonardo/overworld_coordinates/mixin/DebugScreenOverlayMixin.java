package com.benonardo.overworld_coordinates.mixin;

import com.benonardo.overworld_coordinates.CoordinateLines;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * Appends the extra coordinate lines to the vanilla F3 screen.
 * <p>
 * Vanilla still builds the left hand debug text inside a single method up to 1.21.1.
 */
@Mixin(DebugScreenOverlay.class)
public class DebugScreenOverlayMixin {

    @Inject(method = "getGameInformation", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0, shift = At.Shift.AFTER), slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=XYZ: %.3f / %.5f / %.3f")))
    private void appendOverworldCoordinates(CallbackInfoReturnable<List<String>> cir, @Local List<String> list) {
        list.addAll(CoordinateLines.build());
    }

}
