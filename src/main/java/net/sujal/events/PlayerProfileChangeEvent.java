package net.sujal.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerProfileChangeEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final String oldProfileId;
    private String newProfileId;
    private boolean cancelled;

    public PlayerProfileChangeEvent(Player player, String oldProfileId, String newProfileId) {
        this.player = player;
        this.oldProfileId = oldProfileId;
        this.newProfileId = newProfileId;
    }

    public Player getPlayer() { return player; }
    public String getOldProfileId() { return oldProfileId; }
    public String getNewProfileId() { return newProfileId; }
    public void setNewProfileId(String newProfileId) { this.newProfileId = newProfileId; }

    @Override
    public boolean isCancelled() { return cancelled; }
    @Override
    public void setCancelled(boolean cancel) { this.cancelled = cancel; }
    @Override
    public HandlerList getHandlers() { return HANDLERS; }
    public static HandlerList getHandlerList() { return HANDLERS; }
}
