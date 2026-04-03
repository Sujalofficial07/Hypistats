package net.sujal.data;

import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

public class PlayerData {
    private final UUID uuid;
    private String activeProfile;
    private final Map<String, ProfileData> profiles = new HashMap<>();

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.activeProfile = "default";
        profiles.put("default", new ProfileData("default"));
    }

    public UUID getUuid() { return uuid; }
    
    public String getActiveProfileName() { return activeProfile; }
    
    public void setActiveProfile(String name) {
        if(!profiles.containsKey(name)) {
            profiles.put(name, new ProfileData(name));
        }
        this.activeProfile = name;
    }

    public ProfileData getActiveProfile() {
        return profiles.get(activeProfile);
    }

    public ProfileData getProfile(String name) {
        return profiles.get(name);
    }
}
