package net.sujal.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.sujal.api.StatsAPI;
import net.sujal.stats.shared.StatType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StatsExpansion extends PlaceholderExpansion {

    private final StatsAPI statsAPI;

    public StatsExpansion(StatsAPI statsAPI) {
        this.statsAPI = statsAPI;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "skyblock"; // Placeholder prefix: %skyblock_...%
    }

    @Override
    public @NotNull String getAuthor() {
        return "Sujal";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true; 
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) return "";

        // Custom specific placeholders
        if (params.equalsIgnoreCase("health")) {
            return String.valueOf((int) statsAPI.getHealth(player.getUniqueId()));
        }
        if (params.equalsIgnoreCase("max_health")) {
            return String.valueOf((int) statsAPI.getMaxHealth(player.getUniqueId()));
        }
        if (params.equalsIgnoreCase("mana")) {
            return String.valueOf((int) statsAPI.getMana(player.getUniqueId()));
        }

        // Auto-support for ALL stats in StatType Enum
        // Example: %skyblock_strength%, %skyblock_crit_damage%
        try {
            StatType type = StatType.valueOf(params.toUpperCase());
            return String.valueOf((int) statsAPI.getFinalStat(player.getUniqueId(), type));
        } catch (IllegalArgumentException e) {
            return null; // Invalid placeholder
        }
    }
}
