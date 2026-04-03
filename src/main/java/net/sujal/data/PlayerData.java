package net.sujal.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData {
    private final UUID uuid;
    private final String playerName;
    private final Map<String, ProfileData> profiles;
    private String activeProfileId;

    public PlayerData(UUID uuid, String playerName) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.profiles = new HashMap<>();
        this.activeProfileId = "default";
        this.profiles.put("default", new ProfileData("default"));
    }

    public UUID getUuid() { return uuid; }
    public String getPlayerName() { return playerName; }
    public Map<String, ProfileData> getProfiles() { return profiles; }
    
    public String getActiveProfileId() { return activeProfileId; }
    public void setActiveProfileId(String activeProfileId) { this.activeProfileId = activeProfileId; }
    
    public ProfileData getActiveProfile() {
        return profiles.get(activeProfileId);
    }
}
