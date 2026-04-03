package net.sujal.config;

import net.sujal.SkyblockCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private final SkyblockCore plugin;
    private File menusFile;
    private FileConfiguration menusConfig;

    public ConfigManager(SkyblockCore plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        createMenusConfig();
    }

    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        createMenusConfig(); // Reload menus too
    }

    private void createMenusConfig() {
        menusFile = new File(plugin.getDataFolder(), "menus.yml");
        if (!menusFile.exists()) {
            menusFile.getParentFile().mkdirs();
            plugin.saveResource("menus.yml", false);
        }
        menusConfig = YamlConfiguration.loadConfiguration(menusFile);
    }

    public FileConfiguration getMenusConfig() {
        return menusConfig;
    }
}
