package net.sujal.data;

import net.sujal.stats.shared.StatType;

import java.util.HashMap;
import java.util.Map;

public class ProfileData {
    private final String profileName;
    private final Map<StatType, Double> baseStats = new HashMap<>();
    private final Map<StatType, Double> currentPools = new HashMap<>();

    public ProfileData(String profileName) {
        this.profileName = profileName;
        // Initialize defaults
        for (StatType type : StatType.values()) {
            baseStats.put(type, type.getDefaultValue());
            if (type.isPool()) {
                currentPools.put(type, type.getDefaultValue());
            }
        }
    }

    public String getProfileName() { return profileName; }

    public double getBaseStat(StatType type) {
        return baseStats.getOrDefault(type, type.getDefaultValue());
    }

    public void setBaseStat(StatType type, double value) {
        baseStats.put(type, value);
    }

    public double getCurrentPool(StatType type) {
        return currentPools.getOrDefault(type, 0.0);
    }

    public void setCurrentPool(StatType type, double value) {
        currentPools.put(type, value);
    }
}
