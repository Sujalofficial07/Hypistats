package net.sujal.listeners;

import net.sujal.SkyblockCore;
import net.sujal.events.PlayerStatLoadEvent;
import net.sujal.events.PlayerStatSaveEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private final SkyblockCore plugin;

    public PlayerListener(SkyblockCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getStatManager().loadPlayer(player);
        
        PlayerStatLoadEvent loadEvent = new PlayerStatLoadEvent(player, plugin.getStatManager().getPlayerData(player.getUniqueId()));
        Bukkit.getPluginManager().callEvent(loadEvent);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerStatSaveEvent saveEvent = new PlayerStatSaveEvent(player, plugin.getStatManager().getPlayerData(player.getUniqueId()));
        Bukkit.getPluginManager().callEvent(saveEvent);
        
        plugin.getStatManager().savePlayer(player.getUniqueId());
    }
}
