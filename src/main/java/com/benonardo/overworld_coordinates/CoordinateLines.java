package com.benonardo.overworld_coordinates;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Builds the extra debug line shown next to the vanilla F3 position readout.
 * <p>
 * Exactly one line is emitted:
 * <ul>
 *     <li>in the overworld the nether coordinates of the current spot, so you know where to aim
 *     your portal,</li>
 *     <li>everywhere else the camera position converted into overworld scale.</li>
 * </ul>
 * Only the horizontal axes are affected by a dimension's coordinate scale, the Y axis always maps
 * 1:1.
 */
public final class CoordinateLines {

    /** One nether block is eight overworld blocks wide. */
    public static final double NETHER_SCALE = 0.125D;

    private static final String FORMAT = "%s: %.3f / %.5f / %.3f";

    private CoordinateLines() {
    }

    public static List<String> build() {
        List<String> lines = new ArrayList<>(1);

        Minecraft client = Minecraft.getInstance();
        Level level = client.level;
        Entity camera = client.getCameraEntity();
        if (level == null || camera == null) {
            return lines;
        }

        double scale = level.dimensionType().coordinateScale();
        double x = camera.getX() * scale;
        double z = camera.getZ() * scale;
        // Y is never scaled
        double y = camera.getY();

        if (level.dimension() == Level.OVERWORLD) {
            lines.add(String.format(Locale.ROOT, FORMAT, "Nether", x * NETHER_SCALE, y, z * NETHER_SCALE));
        } else {
            lines.add(String.format(Locale.ROOT, FORMAT, "Overworld", x, y, z));
        }
        return lines;
    }
}
