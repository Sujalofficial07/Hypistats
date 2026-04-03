package net.sujal.api;

import net.sujal.SkyblockCore;
import net.sujal.data.PlayerData;
import net.sujal.data.ProfileData;
import net.sujal.stats.StatModifier;
import net.sujal.stats.StatSource;
import net.sujal.stats.StatType;

import java.util.UUID;

public class StatsAPI {
    private final SkyblockCore plugin;

    public StatsAPI(SkyblockCore plugin) {
        this.plugin = plugin;
    }

    public double getFinalStat(UUID uuid, StatType stat) {
        return plugin.getStatManager().getFinalStat(uuid, stat);
    }

    public double getBaseStat(UUID uuid, StatType stat) {
        PlayerData data = plugin.getStatManager().getPlayerData(uuid);
        if (data != null) {
            return data.getActiveProfile().getBaseStats().getOrDefault(stat, 0.0);
        }
        return 0.0;
    }

    public void setBaseStat(UUID uuid, StatType stat, double value) {
        PlayerData data = plugin.getStatManager().getPlayerData(uuid);
        if (data != null) {
            data.getActiveProfile().getBaseStats().put(stat, value);
            plugin.getStatManager().recalculate(uuid, stat);
        }
    }

    public void addModifier(UUID uuid, StatType stat, StatSource source, String id, double value, StatModifier.ModifierType type) {
        plugin.getStatManager().addModifier(uuid, stat, source, new StatModifier(id, value, type));
    }

    public void removeModifier(UUID uuid, StatType stat, StatSource source, String id) {
        plugin.getStatManager().removeModifier(uuid, stat, source, id);
    }

    public void recalculateStats(UUID uuid) {
        plugin.getStatManager().recalculateAll(uuid);
    }

    public PlayerData getPlayerData(UUID uuid) {
        return plugin.getStatManager().getPlayerData(uuid);
    }

    public ProfileData getCurrentProfile(UUID uuid) {
        PlayerData data = getPlayerData(uuid);
        return data != null ? data.getActiveProfile() : null;
    }
}
