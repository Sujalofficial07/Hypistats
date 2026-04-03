package net.sujal.data;

import net.sujal.SkyblockCore;
import net.sujal.stats.StatType;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class DataStorage {
    private final SkyblockCore plugin;
    private final File dataFolder;

    public DataStorage(SkyblockCore plugin) {
        this.plugin = plugin;
        this.dataFolder = new File(plugin.getDataFolder(), "players");
        if (!dataFolder.exists()) dataFolder.mkdirs();
    }

    public PlayerData loadData(UUID uuid, String name) {
        File file = new File(dataFolder, uuid.toString() + ".yml");
        PlayerData data = new PlayerData(uuid, name);
        
        if (!file.exists()) {
            ProfileData active = data.getActiveProfile();
            for (StatType type : StatType.values()) {
                active.getBaseStats().put(type, plugin.getConfigManager().getDefaultStat(type));
            }
            active.setCurrentHealth(plugin.getConfigManager().getDefaultStat(StatType.MAX_HEALTH));
            active.setCurrentMana(plugin.getConfigManager().getDefaultStat(StatType.INTELLIGENCE));
            return data;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        data.setActiveProfileId(config.getString("activeProfile", "default"));
        
        if (config.contains("profiles")) {
            for (String profileId : config.getConfigurationSection("profiles").getKeys(false)) {
                ProfileData profile = new ProfileData(profileId);
                profile.setCurrentHealth(config.getDouble("profiles." + profileId + ".health"));
                profile.setCurrentMana(config.getDouble("profiles." + profileId + ".mana"));
                
                if (config.contains("profiles." + profileId + ".stats")) {
                    for (String statStr : config.getConfigurationSection("profiles." + profileId + ".stats").getKeys(false)) {
                        try {
                            StatType type = StatType.valueOf(statStr);
                            profile.getBaseStats().put(type, config.getDouble("profiles." + profileId + ".stats." + statStr));
                        } catch (IllegalArgumentException ignored) {}
                    }
                }
                data.getProfiles().put(profileId, profile);
            }
        }
        return data;
    }

    public void saveData(PlayerData data) {
        File file = new File(dataFolder, data.getUuid().toString() + ".yml");
        YamlConfiguration config = new YamlConfiguration();
        
        config.set("uuid", data.getUuid().toString());
        config.set("name", data.getPlayerName());
        config.set("activeProfile", data.getActiveProfileId());
        
        for (ProfileData profile : data.getProfiles().values()) {
            String path = "profiles." + profile.getProfileId() + ".";
            config.set(path + "health", profile.getCurrentHealth());
            config.set(path + "mana", profile.getCurrentMana());
            for (var entry : profile.getBaseStats().entrySet()) {
                config.set(path + "stats." + entry.getKey().name(), entry.getValue());
            }
        }
        
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save data for " + data.getUuid());
        }
    }
}
