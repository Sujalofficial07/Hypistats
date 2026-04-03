package net.sujal.tasks;

import net.sujal.SkyblockCore;
import net.sujal.data.PlayerData;
import net.sujal.data.ProfileData;
import net.sujal.stats.StatType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RegenTask extends BukkitRunnable {
    private final SkyblockCore plugin;

    public RegenTask(SkyblockCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerData data = plugin.getApi().getPlayerData(player.getUniqueId());
            if (data == null) continue;

            ProfileData profile = data.getActiveProfile();
            
            // Health Regen Math
            double maxHealth = plugin.getApi().getFinalStat(player.getUniqueId(), StatType.MAX_HEALTH);
            double baseRegen = plugin.getApi().getFinalStat(player.getUniqueId(), StatType.HEALTH_REGEN);
            double vitality = plugin.getApi().getFinalStat(player.getUniqueId(), StatType.VITALITY);
            
            double healAmount = (maxHealth * 0.01) + baseRegen; 
            healAmount *= (1.0 + (vitality / 100.0)); // Vitality buffs healing

            if (player.getHealth() < maxHealth) {
                double newHealth = Math.min(maxHealth, player.getHealth() + healAmount);
                player.setHealth(newHealth);
                profile.setCurrentHealth(newHealth);
            }

            // Mana Regen Math
            double maxMana = plugin.getApi().getFinalStat(player.getUniqueId(), StatType.INTELLIGENCE);
            double manaRegen = plugin.getApi().getFinalStat(player.getUniqueId(), StatType.MANA_REGEN);
            
            double restoreAmount = (maxMana * 0.02) + manaRegen;
            if (profile.getCurrentMana() < maxMana) {
                profile.setCurrentMana(Math.min(maxMana, profile.getCurrentMana() + restoreAmount));
            }
            
            // Action bar text for Health and Mana
            player.sendActionBar(net.kyori.adventure.text.Component.text(
                "§c" + (int)player.getHealth() + "/" + (int)maxHealth + "❤   §b" + 
                (int)profile.getCurrentMana() + "/" + (int)maxMana + "✎ Mana"
            ));
        }
    }
}
