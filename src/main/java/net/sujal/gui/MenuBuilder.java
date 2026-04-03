package net.sujal.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class MenuBuilder implements InventoryHolder {
    private final Inventory inventory;

    public MenuBuilder(int size, Component title) {
        this.inventory = Bukkit.createInventory(this, size, title);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
