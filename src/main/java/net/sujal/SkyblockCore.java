package net.sujal;

import net.sujal.api.StatsAPI;
import net.sujal.config.ConfigManager;
import net.sujal.data.DataStorage;
import net.sujal.listeners.CombatListener;
import net.sujal.listeners.GuiListener;
import net.sujal.listeners.PlayerListener;
import net.sujal.stats.DamageCalculator;
import net.sujal.stats.StatManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyblockCore extends JavaPlugin {
    private ConfigManager configManager;
    private DataStorage dataStorage;
    private StatManager statManager;
    private DamageCalculator damageCalculator;
    private StatsAPI api;

    @Override
    public void onEnable() {
        getLogger().info("Starting SkyblockCore - Initializing systems...");

        this.configManager = new ConfigManager(this);
        this.dataStorage = new DataStorage(this);
        this.statManager = new StatManager(this);
        this.damageCalculator = new DamageCalculator(this);
        this.api = new StatsAPI(this);

        registerListeners();
        
        // Reload safe - load online players if plugin is reloaded
        for (Player player : Bukkit.getOnlinePlayers()) {
            statManager.loadPlayer(player);
        }

        getLogger().info("SkyblockCore loaded successfully!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Saving player data before shutdown...");
        for (Player player : Bukkit.getOnlinePlayers()) {
            statManager.savePlayer(player.getUniqueId());
        }
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new GuiListener(), this);
        getServer().getPluginManager().registerEvents(new CombatListener(this), this);
    }

    public ConfigManager getConfigManager() { return configManager; }
    public DataStorage getDataStorage() { return dataStorage; }
    public StatManager getStatManager() { return statManager; }
    public DamageCalculator getDamageCalculator() { return damageCalculator; }
    public StatsAPI getApi() { return api; }
}
