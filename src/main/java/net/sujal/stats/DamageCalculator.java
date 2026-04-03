package net.sujal.stats;

import net.sujal.SkyblockCore;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.UUID; // <-- YEH LINE MISSING THI
import java.util.concurrent.ThreadLocalRandom;

public class DamageCalculator {
    private final SkyblockCore plugin;

    public DamageCalculator(SkyblockCore plugin) {
        this.plugin = plugin;
    }

    public double calculateDamage(Player attacker, LivingEntity victim, double baseWeaponDamage) {
        UUID uuid = attacker.getUniqueId();
        StatManager stats = plugin.getStatManager();
        
        double strength = stats.getFinalStat(uuid, StatType.STRENGTH);
        double critChance = stats.getFinalStat(uuid, StatType.CRIT_CHANCE);
        double critDamage = stats.getFinalStat(uuid, StatType.CRIT_DAMAGE);
        
        double baseMultiplier = plugin.getConfig().getDouble("settings.damage-multiplier", 5.0);
        double damage = (baseWeaponDamage + baseMultiplier) * (1 + (strength / 100.0));
        
        boolean isCrit = ThreadLocalRandom.current().nextDouble(100.0) <= critChance;
        if (isCrit) {
            damage *= (1 + (critDamage / 100.0));
        }

        // Apply victim defense if victim is a player
        if (victim instanceof Player pVictim) {
            double defense = stats.getFinalStat(pVictim.getUniqueId(), StatType.DEFENSE);
            double trueDefense = stats.getFinalStat(pVictim.getUniqueId(), StatType.TRUE_DEFENSE);
            
            // Standard skyblock armor formula
            double damageReduction = defense / (defense + 100.0); 
            damage *= (1.0 - damageReduction);
            
            if (trueDefense > 0) {
                double trueReduction = trueDefense / (trueDefense + 100.0);
                damage *= (1.0 - trueReduction);
            }
        }
        
        return damage;
    }
}
