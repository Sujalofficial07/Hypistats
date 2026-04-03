package net.sujal.stats;

import net.sujal.SkyblockCore;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import net.sujal.events.PlayerStatChangeEvent;

public class AttributeSyncTask implements Listener {
    private final SkyblockCore plugin;

    public AttributeSyncTask(SkyblockCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onStatChange(PlayerStatChangeEvent event) {
        Player player = event.getPlayer();
        StatType stat = event.getStat();
        double value = event.getNewValue();

        switch (stat) {
            case MAX_HEALTH:
                AttributeInstance healthAttr = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if (healthAttr != null) healthAttr.setBaseValue(Math.max(1, value)); // Minimum 1 HP
                break;
            case SPEED:
                // Base speed is 100. Minecraft default walk speed is 0.2f
                float speed = (float) (value / 100.0) * 0.2f;
                player.setWalkSpeed(Math.min(1.0f, speed)); // Cap at 1.0f (Bukkit limit)
                break;
            case SWING_RANGE:
                AttributeInstance reachAttr = player.getAttribute(Attribute.PLAYER_ENTITY_INTERACTION_RANGE);
                if (reachAttr != null) reachAttr.setBaseValue(value);
                break;
            case ATTACK_SPEED:
                AttributeInstance atkSpeedAttr = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
                // 100 Attack speed = vanilla default (4.0). 200 = double.
                if (atkSpeedAttr != null) atkSpeedAttr.setBaseValue(4.0 * (value / 100.0));
                break;
        }
    }
}
