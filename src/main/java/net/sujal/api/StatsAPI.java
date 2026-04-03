package net.sujal.api;

import net.sujal.data.PlayerData;
import net.sujal.data.ProfileData;
import net.sujal.stats.shared.StatType;

import java.util.UUID;

public interface StatsAPI {
    double getStat(UUID uuid, StatType stat);
    double getBaseStat(UUID uuid, StatType stat);
    double getFinalStat(UUID uuid, StatType stat);
    
    void setStat(UUID uuid, StatType stat, double value);
    void addStat(UUID uuid, StatType stat, double value);
    void removeStat(UUID uuid, StatType stat, double value);
    
    void recalculateStats(UUID uuid);
    
    PlayerData getPlayerData(UUID uuid);
    ProfileData getCurrentProfile(UUID uuid);

    // Convenience Methods
    double getHealth(UUID uuid);
    void setHealth(UUID uuid, double amount);
    double getMaxHealth(UUID uuid);
    void damage(UUID uuid, double amount);
    void heal(UUID uuid, double amount);
    
    double getMana(UUID uuid);
    void setMana(UUID uuid, double amount);
}
