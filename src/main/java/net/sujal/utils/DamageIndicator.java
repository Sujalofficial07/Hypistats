package net.sujal.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.sujal.SkyblockCore;
import org.bukkit.Location;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.text.DecimalFormat;

public class DamageIndicator {

    private static final DecimalFormat df = new DecimalFormat("#,###");

    public static void spawn(Location loc, double damage, boolean isCrit) {
        double offsetX = (Math.random() - 0.5) * 1.5;
        double offsetY = Math.random() * 0.5 + 1.0;
        double offsetZ = (Math.random() - 0.5) * 1.5;
        Location spawnLoc = loc.clone().add(offsetX, offsetY, offsetZ);

        spawnLoc.getWorld().spawn(spawnLoc, TextDisplay.class, display -> {
            String damageString = df.format((int) damage);
            Component text;

            if (isCrit) {
                // Hypixel Style Crit: ✨ 1,234 ✨
                text = Component.text("✧ ", NamedTextColor.WHITE)
                        .append(Component.text(damageString, NamedTextColor.RED)) // Yellow/Red is common for crits
                        .append(Component.text(" ✧", NamedTextColor.WHITE))
                        .decorate(TextDecoration.BOLD);
            } else {
                text = Component.text(damageString, NamedTextColor.GRAY);
            }

            display.text(text);
            display.setBillboard(TextDisplay.Billboard.CENTER);
            display.setDefaultBackground(false);
            display.setBackgroundColor(org.bukkit.Color.fromARGB(0, 0, 0, 0));
            display.setTransformation(new Transformation(
                    new Vector3f(),
                    new AxisAngle4f(),
                    new Vector3f(1.5f, 1.5f, 1.5f),
                    new AxisAngle4f()
            ));

            SkyblockCore.getInstance().getServer().getScheduler().runTaskLater(SkyblockCore.getInstance(), display::remove, 25L);
        });
    }
}
