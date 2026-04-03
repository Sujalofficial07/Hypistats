package net.sujal.data;

import net.sujal.stats.StatType;
import java.util.EnumMap;
import java.util.Map;

public class ProfileData {
    private final String profileId;
    private final Map<StatType, Double> baseStats;
    private double currentHealth;
    private double currentMana;

    public ProfileData(String profileId) {
        this.profileId = profileId;
        this.baseStats = new EnumMap<>(StatType.class);
    }

    public String getProfileId() { return profileId; }
    public Map<StatType, Double> getBaseStats() { return baseStats; }
    
    public double getCurrentHealth() { return currentHealth; }
    public void setCurrentHealth(double currentHealth) { this.currentHealth = currentHealth; }
    
    public double getCurrentMana() { return currentMana; }
    public void setCurrentMana(double currentMana) { this.currentMana = currentMana; }
}
