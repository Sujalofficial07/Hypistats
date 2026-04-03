package net.sujal.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.InventoryHolder;

public class GuiProtectionListener implements Listener {

    // Isme hum future custom GUI menus ko protect kar rahe hain
    // Taki koi item steal ya shift-click exploits na kar sake

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        // Assuming future GUI menus implement a custom interface, e.g., 'CustomGUI'
        // if (holder instanceof CustomGUI) {
        //     event.setCancelled(true);
        // }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        // if (holder instanceof CustomGUI) {
        //     event.setCancelled(true);
        // }
    }
}
