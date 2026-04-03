package net.sujal.api.impl;

import net.sujal.api.StatsAPI;
import net.sujal.data.DataStorage;
import net.sujal.data.PlayerData;
import net.sujal.data.ProfileData;
import net.sujal.events.PlayerStatChangeEvent;
import net.sujal.stats.shared.StatType;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import java.util.UUID;

public class StatsAPIImpl implements StatsAPI {

    private final DataStorage dataStorage;

    public StatsAPIImpl(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    @Override
    public double getStat(UUID uuid, StatType stat) {
        return getFinalStat(uuid, stat);
    }

    @Override
    public double getBaseStat(UUID uuid, StatType stat) {
        ProfileData profile = getCurrentProfile(uuid);
        return profile != null ? profile.getBaseStat(stat) : stat.getDefaultValue();
    }

    @Override
    public double getFinalStat(UUID uuid, StatType stat) {
        return getBaseStat(uuid, stat);
    }

    @Override
    public void setStat(UUID uuid, StatType stat, double value) {
        ProfileData profile = getCurrentProfile(uuid);
        if (profile == null) return;
        
        double oldValue = profile.getBaseStat(stat);
        
        PlayerStatChangeEvent event = new PlayerStatChangeEvent(uuid, stat, oldValue, value);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;

        profile.setBaseStat(stat, event.getNewValue());
        recalculateStats(uuid);
    }

    @Override
    public void addStat(UUID uuid, StatType stat, double value) {
        setStat(uuid, stat, getBaseStat(uuid, stat) + value);
    }

    @Override
    public void removeStat(UUID uuid, StatType stat, double value) {
        setStat(uuid, stat, Math.max(0, getBaseStat(uuid, stat) - value));
    }

        @Override
    public void recalculateStats(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null || !player.isOnline()) return;

        // SYNC SPEED: Cap at 400 (Configurable limit concept)
        double speed = getFinalStat(uuid, StatType.SPEED);
        // Ensure speed doesn't exceed 400 (4.0x normal speed). Bukkit Max Walk Speed is 1.0f
        double clampedSpeed = Math.min(speed, 400.0);
        float walkSpeed = (float) Math.max(0.01, (clampedSpeed / 100.0) * 0.2f);
        player.setWalkSpeed(walkSpeed);

        // SYNC HEALTH: Hypixel Style (Max 20 Visual Hearts = 40.0 Bukkit Health)
        double maxCustomHealth = getFinalStat(uuid, StatType.HEALTH);
        double currentCustomHealth = getHealth(uuid);
        
        if (currentCustomHealth > maxCustomHealth) {
            setHealth(uuid, maxCustomHealth);
            currentCustomHealth = maxCustomHealth;
        }

        double visualMaxHealth = 40.0;
        org.bukkit.attribute.AttributeInstance maxHealthAttr = player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH);
        if (maxHealthAttr != null) {
            maxHealthAttr.setBaseValue(visualMaxHealth);
        }
        player.setHealthScale(visualMaxHealth);
        player.setHealthScaled(true);
        
        double pct = currentCustomHealth / maxCustomHealth;
        double bukkitHealth = visualMaxHealth * pct;
        
        bukkitHealth = Math.max(0.1, Math.min(bukkitHealth, visualMaxHealth));
        player.setHealth(bukkitHealth);
    }

    @Override
    public PlayerData getPlayerData(UUID uuid) {
        return dataStorage.getPlayerData(uuid);
    }

    @Override
    public ProfileData getCurrentProfile(UUID uuid) {
        PlayerData data = getPlayerData(uuid);
        return data != null ? data.getActiveProfile() : null;
    }

    @Override
    public double getHealth(UUID uuid) {
        ProfileData profile = getCurrentProfile(uuid);
        return profile != null ? profile.getCurrentPool(StatType.HEALTH) : 0;
    }

    @Override
    public void setHealth(UUID uuid, double amount) {
        ProfileData profile = getCurrentProfile(uuid);
        if (profile == null) return;
        
        double maxHealth = getFinalStat(uuid, StatType.HEALTH);
        double clamped = Math.max(0, Math.min(amount, maxHealth));
        profile.setCurrentPool(StatType.HEALTH, clamped);
        recalculateStats(uuid);
    }

    @Override
    public double getMaxHealth(UUID uuid) {
        return getFinalStat(uuid, StatType.HEALTH);
    }

    @Override
    public void damage(UUID uuid, double amount) {
        setHealth(uuid, getHealth(uuid) - amount);
    }

    @Override
    public void heal(UUID uuid, double amount) {
        setHealth(uuid, getHealth(uuid) + amount);
    }

    @Override
    public double getMana(UUID uuid) {
        ProfileData profile = getCurrentProfile(uuid);
        return profile != null ? profile.getCurrentPool(StatType.MANA) : 0;
    }

    @Override
    public void setMana(UUID uuid, double amount) {
        ProfileData profile = getCurrentProfile(uuid);
        if (profile == null) return;
        double maxMana = getFinalStat(uuid, StatType.INTELLIGENCE) + 100;
        profile.setCurrentPool(StatType.MANA, Math.max(0, Math.min(amount, maxMana)));
    }
}
