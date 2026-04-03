package net.sujal.data;

import net.sujal.SkyblockCore;
import net.sujal.stats.shared.StatType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataStorage {

    private final SkyblockCore plugin;
    private final File playersFolder;
    private final Map<UUID, PlayerData> cache = new HashMap<>();

    public DataStorage(SkyblockCore plugin) {
        this.plugin = plugin;
        this.playersFolder = new File(plugin.getDataFolder(), "players");
        if (!playersFolder.exists()) {
            playersFolder.mkdirs();
        }
    }

    public void loadPlayer(UUID uuid) {
        File file = new File(playersFolder, uuid.toString() + ".yml");
        PlayerData data = new PlayerData(uuid);
        
        if (file.exists()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            ProfileData profile = data.getActiveProfile();
            
            for (StatType type : StatType.values()) {
                if (config.contains("stats." + type.name())) {
                    profile.setBaseStat(type, config.getDouble("stats." + type.name()));
                }
                if (type.isPool() && config.contains("pools." + type.name())) {
                    profile.setCurrentPool(type, config.getDouble("pools." + type.name()));
                }
            }
        }
        
        cache.put(uuid, data);
    }

    public void savePlayer(UUID uuid) {
        PlayerData data = cache.get(uuid);
        if (data == null) return;

        File file = new File(playersFolder, uuid.toString() + ".yml");
        YamlConfiguration config = new YamlConfiguration();
        ProfileData profile = data.getActiveProfile();

        for (StatType type : StatType.values()) {
            config.set("stats." + type.name(), profile.getBaseStat(type));
            if (type.isPool()) {
                config.set("pools." + type.name(), profile.getCurrentPool(type));
            }
        }

        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Bhai, player data save nahi ho paya for UUID: " + uuid);
            e.printStackTrace();
        }
        
        // Agar player offline hai toh cache clear kar do
        if (Bukkit.getPlayer(uuid) == null) {
            cache.remove(uuid);
        }
    }

    public void saveAllOnlinePlayers() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            savePlayer(p.getUniqueId());
        }
    }

    public PlayerData getPlayerData(UUID uuid) {
        return cache.get(uuid);
    }
}
