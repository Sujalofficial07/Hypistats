package net.sujal.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

// Yeh class sirf GUI ko identify karne ke liye hai taaki usme click cancel ho sake
public class MenuHolder implements InventoryHolder {
    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}
