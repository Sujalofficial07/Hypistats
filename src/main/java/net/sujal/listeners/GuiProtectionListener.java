package net.sujal.listeners;

import net.sujal.gui.MenuHolder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.InventoryHolder;

public class GuiProtectionListener implements Listener {

        @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        
        if (holder instanceof MenuHolder) {
            event.setCancelled(true); // Steal karna mana hai
            
            // Agar player ne "Close" (Barrier) par click kiya
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() == org.bukkit.Material.BARRIER) {
                event.getWhoClicked().closeInventory();
            }
        }
    }
    
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof MenuHolder) {
            event.setCancelled(true);
        }
    }
}
