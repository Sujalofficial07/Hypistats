package net.sujal.stats;

import net.sujal.data.PlayerData;
import org.bukkit.entity.Player;
import java.util.List;

public class StatCalculator {
    public static double calculateFinalStat(Player player, StatType type, PlayerData data, StatManager manager) {
        double base = data.getActiveProfile().getBaseStats().getOrDefault(type, 0.0);
        double flatBonus = 0.0;
        double percentBonus = 0.0;

        for (StatSource source : StatSource.values()) {
            if (source == StatSource.BASE) continue;
            List<StatModifier> mods = manager.getModifiers(player.getUniqueId(), type, source);
            for (StatModifier mod : mods) {
                if (mod.type() == StatModifier.ModifierType.FLAT) {
                    flatBonus += mod.value();
                } else if (mod.type() == StatModifier.ModifierType.PERCENT) {
                    percentBonus += mod.value();
                }
            }
        }
        return (base + flatBonus) * (1.0 + (percentBonus / 100.0));
    }
}
