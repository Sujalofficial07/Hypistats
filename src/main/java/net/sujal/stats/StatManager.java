package net.sujal.stats;

import net.sujal.SkyblockCore;
import net.sujal.data.PlayerData;
import net.sujal.events.PlayerStatChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StatManager {
    private final SkyblockCore plugin;
    private final Map<UUID, PlayerData> activeData = new ConcurrentHashMap<>();
    
    // Cache map structure: UUID -> StatType -> Source -> List<Modifier>
    private final Map<UUID, Map<StatType, Map<StatSource, List<StatModifier>>>> modifiers = new ConcurrentHashMap<>();
    private final Map<UUID, Map<StatType, Double>> finalStatsCache = new ConcurrentHashMap<>();

    public StatManager(SkyblockCore plugin) {
        this.plugin = plugin;
    }

    public void loadPlayer(Player player) {
        PlayerData data = plugin.getDataStorage().loadData(player.getUniqueId(), player.getName());
        activeData.put(player.getUniqueId(), data);
        recalculateAll(player.getUniqueId());
    }

    public void savePlayer(UUID uuid) {
        PlayerData data = activeData.get(uuid);
        if (data != null) {
            plugin.getDataStorage().saveData(data);
            activeData.remove(uuid);
            modifiers.remove(uuid);
            finalStatsCache.remove(uuid);
        }
    }

    public PlayerData getPlayerData(UUID uuid) {
        return activeData.get(uuid);
    }

    public void addModifier(UUID uuid, StatType type, StatSource source, StatModifier modifier) {
        modifiers.computeIfAbsent(uuid, k -> new EnumMap<>(StatType.class))
                 .computeIfAbsent(type, k -> new EnumMap<>(StatSource.class))
                 .computeIfAbsent(source, k -> new ArrayList<>())
                 .add(modifier);
        recalculate(uuid, type);
    }

    public void removeModifier(UUID uuid, StatType type, StatSource source, String modifierId) {
        Map<StatSource, List<StatModifier>> sourceMap = modifiers.getOrDefault(uuid, Collections.emptyMap()).get(type);
        if (sourceMap != null && sourceMap.containsKey(source)) {
            sourceMap.get(source).removeIf(mod -> mod.id().equals(modifierId));
            recalculate(uuid, type);
        }
    }

    public List<StatModifier> getModifiers(UUID uuid, StatType type, StatSource source) {
        return modifiers.getOrDefault(uuid, Collections.emptyMap())
                        .getOrDefault(type, Collections.emptyMap())
                        .getOrDefault(source, new ArrayList<>());
    }

    public double getFinalStat(UUID uuid, StatType type) {
        return finalStatsCache.getOrDefault(uuid, Collections.emptyMap()).getOrDefault(type, 0.0);
    }

    public void recalculate(UUID uuid, StatType type) {
        Player player = Bukkit.getPlayer(uuid);
        PlayerData data = activeData.get(uuid);
        if (player == null || data == null) return;

        double oldVal = getFinalStat(uuid, type);
        double newVal = StatCalculator.calculateFinalStat(player, type, data, this);

        PlayerStatChangeEvent event = new PlayerStatChangeEvent(player, type, oldVal, newVal);
        Bukkit.getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            finalStatsCache.computeIfAbsent(uuid, k -> new EnumMap<>(StatType.class)).put(type, event.getNewValue());
        }
    }

    public void recalculateAll(UUID uuid) {
        for (StatType type : StatType.values()) {
            recalculate(uuid, type);
        }
    }
}
