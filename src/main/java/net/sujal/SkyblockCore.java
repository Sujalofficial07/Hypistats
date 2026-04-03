package net.sujal;

import net.sujal.api.StatsAPI;
import net.sujal.commands.AdminCommand;
import net.sujal.commands.StatsCommand;
import net.sujal.config.ConfigManager;
import net.sujal.data.DataStorage;
import net.sujal.listeners.CombatListener;
import net.sujal.listeners.GuiListener;
import net.sujal.listeners.MechanicsListener;
import net.sujal.listeners.PlayerListener;
import net.sujal.stats.AttributeSyncTask;
import net.sujal.stats.DamageCalculator;
import net.sujal.stats.StatManager;
import net.sujal.tasks.RegenTask;
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
        registerCommands();
        
        // Start Actionbar & Regen Task (Runs every 1 second / 20 ticks)
        new RegenTask(this).runTaskTimer(this, 20L, 20L);

        // Reload safe - load online players
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
        getServer().getPluginManager().registerEvents(new GuiListener(this), this);
        getServer().getPluginManager().registerEvents(new CombatListener(this), this);
        getServer().getPluginManager().registerEvents(new MechanicsListener(this), this);
        getServer().getPluginManager().registerEvents(new AttributeSyncTask(this), this);
    }

    private void registerCommands() {
        getCommand("stats").setExecutor(new StatsCommand(this));
        getCommand("sbadmin").setExecutor(new AdminCommand(this));
    }

    public ConfigManager getConfigManager() { return configManager; }
    public DataStorage getDataStorage() { return dataStorage; }
    public StatManager getStatManager() { return statManager; }
    public DamageCalculator getDamageCalculator() { return damageCalculator; }
    public StatsAPI getApi() { return api; }
}
