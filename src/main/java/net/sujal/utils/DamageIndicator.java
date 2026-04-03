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
        // Hologram ko thoda random position pe spawn karna taaki overlap na ho
        double offsetX = (Math.random() - 0.5) * 1.5;
        double offsetY = Math.random() * 0.5 + 1.0;
        double offsetZ = (Math.random() - 0.5) * 1.5;
        Location spawnLoc = loc.clone().add(offsetX, offsetY, offsetZ);

        spawnLoc.getWorld().spawn(spawnLoc, TextDisplay.class, display -> {
            String damageString = df.format((int) damage);
            Component text;

            if (isCrit) {
                // Hypixel style colorful crit: ✧ 123 ✧
                text = Component.text("✧ " + damageString + " ✧")
                        .color(NamedTextColor.GOLD)
                        .decorate(TextDecoration.BOLD);
            } else {
                text = Component.text(damageString).color(NamedTextColor.GRAY);
            }

            display.text(text);
            display.setBillboard(TextDisplay.Billboard.CENTER);
            display.setDefaultBackground(false);
            display.setBackgroundColor(org.bukkit.Color.fromARGB(0, 0, 0, 0));
            display.setTransformation(new Transformation(
                    new Vector3f(),
                    new AxisAngle4f(),
                    new Vector3f(1.5f, 1.5f, 1.5f), // Scale up thoda
                    new AxisAngle4f()
            ));

            // Despawn after 1.5 seconds (30 ticks)
            SkyblockCore.getInstance().getServer().getScheduler().runTaskLater(SkyblockCore.getInstance(), display::remove, 30L);
        });
    }
}
