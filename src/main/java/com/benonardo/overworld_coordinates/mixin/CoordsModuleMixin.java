package com.benonardo.overworld_coordinates.mixin;

import com.benonardo.overworld_coordinates.CoordinateLines;
import me.cominixo.betterf3.modules.BaseModule;
import me.cominixo.betterf3.modules.CoordsModule;
import me.cominixo.betterf3.utils.DebugLine;
import me.cominixo.betterf3.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Pseudo
@Mixin(value = CoordsModule.class, remap = false)
public abstract class CoordsModuleMixin extends BaseModule {

    /** Index of the line added by {@link #registerOverworldCoordinates}. */
    private static final int OVERWORLD_LINE = 1;
    /** Index of the line added by {@link #registerOverworldCoordinates}, right after the overworld one. */
    private static final int NETHER_LINE = 2;

    @Shadow public TextColor colorX;
    @Shadow public TextColor colorY;
    @Shadow public TextColor colorZ;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0, shift = At.Shift.AFTER))
    private void registerOverworldCoordinates(CallbackInfo ci) {
        this.lines.add(new DebugLine("overworld_coords", "format.betterf3.coords", true));
        this.lines.add(new DebugLine("nether_coords", "owc.format.coords2d", true));
    }

    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lme/cominixo/betterf3/utils/DebugLine;value(Ljava/lang/Object;)V", ordinal = 0, shift = At.Shift.AFTER))
    private void appendOverworldCoordinates(Minecraft client, CallbackInfo ci) {
        Level level = client.level;
        Entity camera = client.getCameraEntity();
        if (level == null || camera == null) {
            this.lines.get(NETHER_LINE).active = false;
            return;
        }

        double scale = level.dimensionType().coordinateScale();
        double x = camera.getX() * scale;
        double y = camera.getY() * scale;
        double z = camera.getZ() * scale;

        this.lines.get(OVERWORLD_LINE).value(Arrays.asList(
                Utils.styledText(format(x), this.colorX),
                Utils.styledText(format(y, "%.5f"), this.colorY),
                Utils.styledText(format(z), this.colorZ)));

        DebugLine nether = this.lines.get(NETHER_LINE);
        if (level.dimension() == Level.OVERWORLD) {
            nether.value(List.of(
                    Utils.styledText(format(x * CoordinateLines.NETHER_SCALE), this.colorX),
                    Utils.styledText(format(z * CoordinateLines.NETHER_SCALE), this.colorZ)));
            nether.active = true;
        } else {
            // only meaningful while standing in the overworld
            nether.active = false;
        }
    }

    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;"), slice = @Slice(from = @At(value = "INVOKE", target = "Lme/cominixo/betterf3/utils/DebugLine;value(Ljava/lang/Object;)V", ordinal = 0)))
    private int shiftForOverworldCoordinates(int i) {
        return i + 2;
    }

    private static String format(double value) {
        return format(value, "%.3f");
    }

    private static String format(double value, String pattern) {
        return String.format(Locale.ROOT, pattern, value);
    }

}
