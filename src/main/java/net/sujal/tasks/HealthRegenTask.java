package net.sujal.tasks;

import net.sujal.api.StatsAPI;
import net.sujal.stats.shared.StatType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class HealthRegenTask extends BukkitRunnable {

    private final StatsAPI statsAPI;

    public HealthRegenTask(StatsAPI statsAPI) {
        this.statsAPI = statsAPI;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            double maxHealth = statsAPI.getMaxHealth(player.getUniqueId());
            double currentHealth = statsAPI.getHealth(player.getUniqueId());

            if (currentHealth < maxHealth) {
                // Base regen (e.g. 1% of max health + Health Regen stat) * Vitality multiplier
                double regenBase = (maxHealth * 0.01) + (statsAPI.getFinalStat(player.getUniqueId(), StatType.HEALTH_REGEN) / 100);
                double vitality = statsAPI.getFinalStat(player.getUniqueId(), StatType.VITALITY);
                
                double finalRegen = regenBase * (1 + (vitality / 100));
                
                statsAPI.heal(player.getUniqueId(), finalRegen);
            }
        }
    }
}
