package net.sujal.listeners;

import net.sujal.SkyblockCore;
import net.sujal.stats.StatType;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.inventory.ItemStack;

public class MechanicsListener implements Listener {
    private final SkyblockCore plugin;

    public MechanicsListener(SkyblockCore plugin) {
        this.plugin = plugin;
    }

    // VITALITY MECHANIC
    @EventHandler
    public void onHeal(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player player) {
            double vitality = plugin.getApi().getFinalStat(player.getUniqueId(), StatType.VITALITY);
            if (vitality > 0) {
                event.setAmount(event.getAmount() * (1.0 + (vitality / 100.0)));
            }
        }
    }

    // WISDOM MECHANIC (XP BOOST)
    @EventHandler
    public void onXpGain(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();
        // Global combat wisdom for standard vanilla orbs
        double combatWisdom = plugin.getApi().getFinalStat(player.getUniqueId(), StatType.COMBAT_WISDOM);
        if (combatWisdom > 0) {
            event.setAmount((int) (event.getAmount() * (1.0 + (combatWisdom / 100.0))));
        }
    }

    // FORTUNE MECHANIC (Mining Double drops)
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        double miningFortune = plugin.getApi().getFinalStat(player.getUniqueId(), StatType.MINING_FORTUNE);
        
        if (miningFortune > 0 && Math.random() * 100 < miningFortune) {
            for (ItemStack drop : event.getBlock().getDrops(player.getInventory().getItemInMainHand())) {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), drop);
            }
        }
    }

    // FEROCITY MECHANIC (Extra hits)
    @EventHandler
    public void onFerocity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player attacker && event.getEntity() instanceof LivingEntity victim) {
            // Check if this hit was already a ferocity hit to prevent infinite loops
            if (event.getCause() == EntityDamageByEntityEvent.DamageCause.CUSTOM) return;

            double ferocity = plugin.getApi().getFinalStat(attacker.getUniqueId(), StatType.FEROCITY);
            if (ferocity <= 0) return;

            int extraHits = (int) (ferocity / 100);
            double chanceForAnother = ferocity % 100;

            if (Math.random() * 100 < chanceForAnother) {
                extraHits++;
            }

            final int hits = extraHits;
            final double damage = event.getFinalDamage();

            if (hits > 0) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    if (victim.isDead() || !victim.isValid()) return;
                    for (int i = 0; i < hits; i++) {
                        victim.damage(damage, attacker);
                        attacker.sendMessage("§c§lFerocity Strike!");
                    }
                }, 10L); // Strike after 0.5 seconds
            }
        }
    }
}
