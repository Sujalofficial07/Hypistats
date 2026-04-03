package net.sujal.listeners;

import net.sujal.api.StatsAPI;
import net.sujal.stats.shared.StatType;
import net.sujal.utils.DamageIndicator;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.LivingEntity;
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

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof LivingEntity target)) return;

        Player attacker = null;
        boolean isBow = false;

        if (event.getDamager() instanceof Player) {
            attacker = (Player) event.getDamager();
        } else if (event.getDamager() instanceof AbstractArrow arrow && arrow.getShooter() instanceof Player shooter) {
            attacker = shooter;
            isBow = true;
        }

        if (attacker == null) return;

        double baseDamage = event.getDamage(); // Weapon ya Bow ka base damage
        double strength = statsAPI.getFinalStat(attacker.getUniqueId(), StatType.STRENGTH);
        double critChance = statsAPI.getFinalStat(attacker.getUniqueId(), StatType.CRIT_CHANCE);
        double critDamage = statsAPI.getFinalStat(attacker.getUniqueId(), StatType.CRIT_DAMAGE);

        // Hypixel Damage Formula: Base * (1 + Strength/100)
        double finalDamage = baseDamage * (1 + (strength / 100));

        boolean isCrit = false;
        // Crit calculate karna (Max 100%)
        if (Math.random() * 100 <= critChance) {
            finalDamage = finalDamage * (1 + (critDamage / 100));
            isCrit = true;
        }

        event.setDamage(finalDamage);

        // Hologram Spawn karna
        DamageIndicator.spawn(target.getLocation(), finalDamage, isCrit);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerTakeDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        
        // Agar khud ka plugin damage kar raha hai toh ignore
        if (event.getCause() == EntityDamageEvent.DamageCause.CUSTOM) return;

        double incomingDamage = event.getDamage();
        double defense = statsAPI.getFinalStat(player.getUniqueId(), StatType.DEFENSE);

        // Defense Reduction Formula
        double damageMultiplier = 1 - (defense / (defense + 100));
        double finalDamage = incomingDamage * damageMultiplier;

        statsAPI.damage(player.getUniqueId(), finalDamage);
        
        if (statsAPI.getHealth(player.getUniqueId()) > 0) {
            event.setDamage(0); // Vanilla damage cancel, kyunki custom health reduce ho chuki hai
        }
    }

    @EventHandler
    public void onVanillaRegen(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true); // Vanilla regen disabled
        }
    }
}
