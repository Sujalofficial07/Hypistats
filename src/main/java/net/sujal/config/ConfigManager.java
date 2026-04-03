package net.sujal.config;

import net.sujal.SkyblockCore;
import net.sujal.stats.StatType;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.EnumMap;
import java.util.Map;

public class ConfigManager {
    private final SkyblockCore plugin;
    private final Map<StatType, Double> defaultStats = new EnumMap<>(StatType.class);

    public ConfigManager(SkyblockCore plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        loadDefaults();
    }

    public void loadDefaults() {
        FileConfiguration config = plugin.getConfig();
        for (StatType type : StatType.values()) {
            double val = config.getDouble("defaults." + type.name(), 0.0);
            defaultStats.put(type, val);
        }
    }

    public double getDefaultStat(StatType type) {
        return defaultStats.getOrDefault(type, 0.0);
    }
}
