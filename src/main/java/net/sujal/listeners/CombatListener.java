package net.sujal.listeners;

import net.sujal.SkyblockCore;
import net.sujal.api.StatsAPI;
import net.sujal.stats.shared.StatType;
import net.sujal.utils.DamageIndicator;
import org.bukkit.Sound;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CombatListener implements Listener {

    private final StatsAPI statsAPI;
    private final Set<UUID> activeFerocity = new HashSet<>();

    public CombatListener(StatsAPI statsAPI) {
        this.statsAPI = statsAPI;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof LivingEntity target)) return;

        Player attacker = null;

        if (event.getDamager() instanceof Player) {
            attacker = (Player) event.getDamager();
        } else if (event.getDamager() instanceof AbstractArrow arrow && arrow.getShooter() instanceof Player shooter) {
            attacker = shooter;
        }

        if (attacker == null) return;

        // Prevent Ferocity loop
        if (activeFerocity.contains(attacker.getUniqueId())) return;

        double baseDamage = event.getDamage();
        double strength = statsAPI.getFinalStat(attacker.getUniqueId(), StatType.STRENGTH);
        double critChance = statsAPI.getFinalStat(attacker.getUniqueId(), StatType.CRIT_CHANCE);
        double critDamage = statsAPI.getFinalStat(attacker.getUniqueId(), StatType.CRIT_DAMAGE);
        double ferocity = statsAPI.getFinalStat(attacker.getUniqueId(), StatType.FEROCITY);

        // Calculate Final Damage
        double finalDamage = baseDamage * (1 + (strength / 100));
        boolean isCrit = false;
        
        if (Math.random() * 100 <= critChance) {
            finalDamage = finalDamage * (1 + (critDamage / 100));
            isCrit = true;
        }

        event.setDamage(finalDamage);
        DamageIndicator.spawn(target.getLocation(), finalDamage, isCrit);

        // --- FEROCITY LOGIC ---
        if (ferocity > 0) {
            int extraStrikes = (int) (ferocity / 100);
            double extraChance = ferocity % 100;
            
            if (Math.random() * 100 < extraChance) {
                extraStrikes++;
            }

            if (extraStrikes > 0) {
                triggerFerocity(attacker, target, finalDamage, extraStrikes);
            }
        }
    }

    private void triggerFerocity(Player attacker, LivingEntity target, double damage, int strikes) {
        for (int i = 1; i <= strikes; i++) {
            Bukkit.getScheduler().runTaskLater(SkyblockCore.getInstance(), () -> {
                if (target.isDead() || !target.isValid()) return;
                
                // Add to set to prevent recursive ferocity
                activeFerocity.add(attacker.getUniqueId());
                
                // Deal Damage bypassing I-Frames (NoDamageTicks)
                target.setNoDamageTicks(0);
                target.damage(damage, attacker);
                
                // Play Ferocity Sound
                attacker.playSound(attacker.getLocation(), Sound.ENTITY_IRON_GOLEM_HURT, 1f, 1.5f);
                
                // Spawn Hologram for Ferocity Hit (Not counted as crit visually in base ferocity, but can be styled)
                DamageIndicator.spawn(target.getLocation(), damage, false);
                
                activeFerocity.remove(attacker.getUniqueId());
            }, i * 5L); // 0.25 seconds delay per strike (5 ticks)
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerTakeDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getCause() == EntityDamageEvent.DamageCause.CUSTOM) return;

        double incomingDamage = event.getDamage();
        double defense = statsAPI.getFinalStat(player.getUniqueId(), StatType.DEFENSE);

        double damageMultiplier = 1 - (defense / (defense + 100));
        double finalDamage = incomingDamage * damageMultiplier;

        statsAPI.damage(player.getUniqueId(), finalDamage);
        
        if (statsAPI.getHealth(player.getUniqueId()) > 0) {
            event.setDamage(0);
        }
    }

    @EventHandler
    public void onVanillaRegen(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }
}
