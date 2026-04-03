package net.sujal.listeners;

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
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
        if (event.getInventory().getHolder() instanceof MenuBuilder) {
            event.setCancelled(true); // Stop players from stealing items!

            // IMPORTANT ANTI-THEFT: "item cursor se hat kar instant replace hojaye"
            // The fact that we cancelled the event means the item should not have moved.
            // If another plugin or lag makes it move, this should force it back.
            // However, with `setCancelled(true)`, the core issue is preventing the item from being picked up.
            // If the user *could* pick it up (e.g., another plugin makes it move but cancelled means it returns), that's harder.
            // But this setup ensures it can't be stolen. It stays in the slot.

            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();
            
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            String title = PlainTextComponentSerializer.plainText().serialize(event.getView().title());

            // MAIN MENU LOGIC
            if (title.equals("Your Equipment and Stats")) {
                if (clickedItem.getType() == Material.IRON_SWORD) {
                    StatsGui.openCombatMenu(player, plugin);
                } 
                else if (clickedItem.getType() == Material.DIAMOND_PICKAXE) {
                    StatsGui.openGatheringMenu(player, plugin);
                } 
                else if (clickedItem.getType() == Material.COMPASS) {
                    StatsGui.openMiscMenu(player, plugin);
                }
                else if (clickedItem.getType() == Material.BARRIER && event.getRawSlot() == 49) {
                    player.closeInventory();
                }
                // Handle Placeholder slot clicks (video showed nothing happens)
            }
            
            // SUB-MENU LOGIC ("Your Stats Breakdown")
            else if (title.equals("Your Stats Breakdown")) {
                // "Go Back" Arrow (Slot 48) -> Back to Main Menu
                if (event.getRawSlot() == 48 && clickedItem.getType() == Material.ARROW) {
                    StatsGui.openMainMenu(player, plugin);
                }
                // "Close" Barrier (Slot 49) -> Close Inventory
                else if (event.getRawSlot() == 49 && clickedItem.getType() == Material.BARRIER) {
                    player.closeInventory();
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getInventory().getHolder() instanceof MenuBuilder) {
            event.setCancelled(true);
        }
    }
}
