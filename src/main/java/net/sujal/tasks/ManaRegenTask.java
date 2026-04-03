package net.sujal.tasks;

import net.sujal.api.StatsAPI;
import net.sujal.stats.shared.StatType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ManaRegenTask extends BukkitRunnable {

    private final StatsAPI statsAPI;

    public ManaRegenTask(StatsAPI statsAPI) {
        this.statsAPI = statsAPI;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            double maxMana = statsAPI.getFinalStat(player.getUniqueId(), StatType.INTELLIGENCE) + 100;
            double currentMana = statsAPI.getMana(player.getUniqueId());

            if (currentMana < maxMana) {
                // Base Hypixel formula: 2% of Max Mana per second
                double regenBase = maxMana * 0.02;
                double manaRegenStat = statsAPI.getFinalStat(player.getUniqueId(), StatType.MANA_REGEN);
                
                // Final calculation with Mana Regen stat modifier
                double finalRegen = regenBase * (1 + (manaRegenStat / 100));
                
                statsAPI.setMana(player.getUniqueId(), currentMana + finalRegen);
            }
        }
    }
}
