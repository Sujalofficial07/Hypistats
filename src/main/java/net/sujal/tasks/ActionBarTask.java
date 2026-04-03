package net.sujal.tasks;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.sujal.api.StatsAPI;
import net.sujal.stats.shared.StatType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionBarTask extends BukkitRunnable {

    private final StatsAPI statsAPI;

    public ActionBarTask(StatsAPI statsAPI) {
        this.statsAPI = statsAPI;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            double currentHealth = statsAPI.getHealth(player.getUniqueId());
            double maxHealth = statsAPI.getMaxHealth(player.getUniqueId());
            double defense = statsAPI.getFinalStat(player.getUniqueId(), StatType.DEFENSE);
            double currentMana = statsAPI.getMana(player.getUniqueId());
            double maxMana = statsAPI.getFinalStat(player.getUniqueId(), StatType.INTELLIGENCE) + 100; // Base 100 Mana

            // Format: 100/100❤     50❈ Defense     100/100✎ Mana
            Component actionBarText = Component.text((int)currentHealth + "/" + (int)maxHealth + "❤", NamedTextColor.RED)
                    .append(Component.text("     "))
                    .append(Component.text((int)defense + "❈ Defense", NamedTextColor.GREEN))
                    .append(Component.text("     "))
                    .append(Component.text((int)currentMana + "/" + (int)maxMana + "✎ Mana", NamedTextColor.AQUA));

            player.sendActionBar(actionBarText);
        }
    }
}
