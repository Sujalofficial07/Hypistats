package net.sujal.listeners;

import net.sujal.SkyblockCore;
import net.sujal.gui.MenuBuilder;
import net.sujal.gui.StatsGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public class GuiListener implements Listener {
    private final SkyblockCore plugin;

    public GuiListener(SkyblockCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof MenuBuilder)) return;
        
        event.setCancelled(true); // Stop stealing items
        
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
        if (!(event.getWhoClicked() instanceof Player player)) return;

        ItemStack clicked = event.getCurrentItem();
        if (!clicked.hasItemMeta() || !clicked.getItemMeta().hasDisplayName()) return;

        String itemName = net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer.plainText().serialize(clicked.getItemMeta().displayName());

        // Handle Main Menu Navigation
        if (itemName.contains("Combat Stats")) {
            StatsGui.openCombatStats(player, plugin);
        } else if (itemName.contains("Gathering Stats")) {
            StatsGui.openGatheringStats(player, plugin);
        } else if (itemName.contains("Misc Stats")) {
            StatsGui.openMiscStats(player, plugin);
        } 
        // Handle Back Button
        else if (itemName.contains("Go Back")) {
            StatsGui.openMainMenu(player, plugin);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getInventory().getHolder() instanceof MenuBuilder) {
            event.setCancelled(true);
        }
    }
}
