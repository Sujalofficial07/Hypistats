package net.sujal.listeners;

import net.sujal.api.StatsAPI;
import net.sujal.stats.shared.StatType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class CombatListener implements Listener {

    private final StatsAPI statsAPI;

    public CombatListener(StatsAPI statsAPI) {
        this.statsAPI = statsAPI;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        // Custom damage handling based on Defense
        double incomingDamage = event.getDamage();
        double defense = statsAPI.getFinalStat(player.getUniqueId(), StatType.DEFENSE);
        double trueDefense = statsAPI.getFinalStat(player.getUniqueId(), StatType.TRUE_DEFENSE);

        // Damage Reduction Formula: Defense / (Defense + 100)
        double damageMultiplier = 1 - (defense / (defense + 100));
        
        // True defense logic (simplified for true damage if implemented)
        double finalDamage = incomingDamage * damageMultiplier;

        // Apply custom damage pool
        statsAPI.damage(player.getUniqueId(), finalDamage);
        
        // Prevent vanilla death if custom health > 0
        if (statsAPI.getHealth(player.getUniqueId()) > 0) {
            event.setDamage(0); // Handled internally
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;

        double baseDamage = event.getDamage(); // Weapon base damage
        double strength = statsAPI.getFinalStat(attacker.getUniqueId(), StatType.STRENGTH);
        double critChance = statsAPI.getFinalStat(attacker.getUniqueId(), StatType.CRIT_CHANCE);
        double critDamage = statsAPI.getFinalStat(attacker.getUniqueId(), StatType.CRIT_DAMAGE);

        // Hypixel-style damage calculation
        double finalDamage = baseDamage * (1 + (strength / 100));

        // Crit roll
        if (Math.random() * 100 <= critChance) {
            finalDamage = finalDamage * (1 + (critDamage / 100));
        }

        event.setDamage(finalDamage);
    }
    
    @EventHandler
    public void onVanillaRegen(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player player) {
            event.setCancelled(true); // Custom regen handles this
        }
    }
}
