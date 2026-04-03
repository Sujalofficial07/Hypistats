package net.sujal.listeners;

import net.sujal.SkyblockCore;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CombatListener implements Listener {
    private final SkyblockCore plugin;

    public CombatListener(SkyblockCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player attacker && event.getEntity() instanceof LivingEntity victim) {
            double baseDamage = event.getDamage(); // Default bukkit damage as base
            double finalDamage = plugin.getDamageCalculator().calculateDamage(attacker, victim, baseDamage);
            event.setDamage(finalDamage);
        }
    }
}
