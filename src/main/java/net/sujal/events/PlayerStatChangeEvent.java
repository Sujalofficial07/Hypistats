package net.sujal.events;

import net.sujal.stats.shared.StatType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlayerStatChangeEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    
    private final UUID playerUuid;
    private final StatType statType;
    private final double oldValue;
    private double newValue;
    private boolean isCancelled;

    public PlayerStatChangeEvent(UUID playerUuid, StatType statType, double oldValue, double newValue) {
        this.playerUuid = playerUuid;
        this.statType = statType;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.isCancelled = false;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public StatType getStatType() {
        return statType;
    }

    public double getOldValue() {
        return oldValue;
    }

    public double getNewValue() {
        return newValue;
    }

    public void setNewValue(double newValue) {
        this.newValue = newValue;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
