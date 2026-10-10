package com.benonardo.overworld_coordinates;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Builds the extra debug lines shown next to the vanilla F3 position readout.
 * <p>
 * The first line always reports the camera position converted into overworld scale.
 * While standing in the overworld a second line is emitted that reports the very
 * same spot converted into nether scale, which is what you have to type into a
 * portal to reach the corresponding nether coordinates.
 */
public final class CoordinateLines {

    /** One nether block is eight overworld blocks wide. */
    public static final double NETHER_SCALE = 0.125D;

    private static final String OVERWORLD_FORMAT = "Overworld: %.3f / %.3f";
    private static final String NETHER_FORMAT = "Nether: %.3f / %.3f";

    private CoordinateLines() {
    }

    public static List<String> build() {
        List<String> lines = new ArrayList<>(2);

        Minecraft client = Minecraft.getInstance();
        Level level = client.level;
        Entity camera = client.getCameraEntity();
        if (level == null || camera == null) {
            return lines;
        }

        double scale = level.dimensionType().coordinateScale();
        double x = camera.getX() * scale;
        double z = camera.getZ() * scale;

        lines.add(String.format(Locale.ROOT, OVERWORLD_FORMAT, x, z));
        if (level.dimension() == Level.OVERWORLD) {
            lines.add(String.format(Locale.ROOT, NETHER_FORMAT, x * NETHER_SCALE, z * NETHER_SCALE));
        }
        return lines;
    }
}
