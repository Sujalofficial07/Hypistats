package net.sujal.events;

import net.sujal.stats.StatType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerStatChangeEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final StatType stat;
    private final double oldValue;
    private double newValue;
    private boolean cancelled;

    public PlayerStatChangeEvent(Player player, StatType stat, double oldValue, double newValue) {
        this.player = player;
        this.stat = stat;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public Player getPlayer() { return player; }
    public StatType getStat() { return stat; }
    public double getOldValue() { return oldValue; }
    public double getNewValue() { return newValue; }
    public void setNewValue(double newValue) { this.newValue = newValue; }

    @Override
    public boolean isCancelled() { return cancelled; }
    @Override
    public void setCancelled(boolean cancel) { this.cancelled = cancel; }
    @Override
    public HandlerList getHandlers() { return HANDLERS; }
    public static HandlerList getHandlerList() { return HANDLERS; }
}
