package net.sujal.listeners;

import net.sujal.api.StatsAPI;
import net.sujal.data.DataStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private final DataStorage dataStorage;
    private final StatsAPI statsAPI;

    public PlayerListener(Object plugin, DataStorage dataStorage, StatsAPI statsAPI) {
        this.dataStorage = dataStorage;
        this.statsAPI = statsAPI;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        // Ensure data is loaded
        dataStorage.loadPlayer(player.getUniqueId());

        // Update Stats and Visuals
        statsAPI.recalculateStats(player.getUniqueId());

        // Hypixel Style: Max Food and Saturation forever
        player.setFoodLevel(20);
        player.setSaturation(20f);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        dataStorage.savePlayer(player.getUniqueId());
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        // Yeh line pura hunger/food system band kar degi
        event.setCancelled(true);
        
        if (event.getEntity() instanceof Player player) {
            player.setFoodLevel(20);
            player.setSaturation(20f);
        }
    }
}
