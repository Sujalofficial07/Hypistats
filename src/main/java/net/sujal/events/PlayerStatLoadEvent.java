package net.sujal.events;

import net.sujal.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerStatLoadEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final PlayerData playerData;

    public PlayerStatLoadEvent(Player player, PlayerData playerData) {
        this.player = player;
        this.playerData = playerData;
    }

    public Player getPlayer() { return player; }
    public PlayerData getPlayerData() { return playerData; }
    
    @Override
    public HandlerList getHandlers() { return HANDLERS; }
    public static HandlerList getHandlerList() { return HANDLERS; }
}
