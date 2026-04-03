package net.sujal.config;

import net.sujal.SkyblockCore;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private final SkyblockCore plugin;

    public ConfigManager(SkyblockCore plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
    }
}
