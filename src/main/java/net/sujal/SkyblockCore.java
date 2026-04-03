package net.sujal;

import net.sujal.api.StatsAPI;
import net.sujal.commands.DevStatCommand;
import net.sujal.config.ConfigManager;
import net.sujal.data.DataStorage;
import net.sujal.listeners.CombatListener;
import net.sujal.listeners.GuiProtectionListener;
import net.sujal.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkyblockCore extends JavaPlugin {

    private static SkyblockCore instance;
    private ConfigManager configManager;
    private DataStorage dataStorage;
    private StatsAPI statsAPI;

    @Override
    public void onEnable() {
        instance = this;
        
        // Initialize Config
        saveDefaultConfig();
        configManager = new ConfigManager(this);

        // Initialize Data Storage
        dataStorage = new DataStorage(this);

        // Initialize API
        statsAPI = new net.sujal.api.impl.StatsAPIImpl(dataStorage);

        // Register Listeners
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this, dataStorage, statsAPI), this);
        Bukkit.getPluginManager().registerEvents(new CombatListener(statsAPI), this);
        Bukkit.getPluginManager().registerEvents(new GuiProtectionListener(), this);

        // Register Commands
        getCommand("stat").setExecutor(new DevStatCommand(statsAPI));

        getLogger().info("SkyblockCore enabled successfully!");
    }

    @Override
    public void onDisable() {
        dataStorage.saveAllOnlinePlayers();
        getLogger().info("SkyblockCore disabled successfully!");
    }

    public static SkyblockCore getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public DataStorage getDataStorage() {
        return dataStorage;
    }

    public StatsAPI getStatsAPI() {
        return statsAPI;
    }
}
