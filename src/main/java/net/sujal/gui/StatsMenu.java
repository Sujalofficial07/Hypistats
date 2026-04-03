package net.sujal.gui;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.sujal.SkyblockCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class StatsMenu {

    private static final LegacyComponentSerializer SERIALIZER = LegacyComponentSerializer.builder().character('&').build();

    public static void open(Player viewer, Player target, net.sujal.api.StatsAPI statsAPI) {
        FileConfiguration config = SkyblockCore.getInstance().getConfigManager().getMenusConfig();
        
        if (config == null || !config.contains("stats-menu")) {
            viewer.sendMessage(Component.text("Error: menus.yml configuration missing!"));
            return;
        }

        // Parse Title with PAPI
        String rawTitle = config.getString("stats-menu.title", "&8Profile");
        String parsedTitle = PlaceholderAPI.setPlaceholders(target, rawTitle);
        int size = config.getInt("stats-menu.size", 54);

        Inventory inv = Bukkit.createInventory(new MenuHolder(), size, SERIALIZER.deserialize(parsedTitle));
        ConfigurationSection itemsSection = config.getConfigurationSection("stats-menu.items");

        if (itemsSection != null) {
            for (String key : itemsSection.getKeys(false)) {
                ConfigurationSection itemConfig = itemsSection.getConfigurationSection(key);
                if (itemConfig == null) continue;

                // Build Item
                Material mat = Material.matchMaterial(itemConfig.getString("material", "STONE"));
                if (mat == null) mat = Material.STONE;
                
                ItemStack item = new ItemStack(mat);
                ItemMeta meta = item.getItemMeta();

                // Format Name
                String rawName = itemConfig.getString("name", "");
                if (!rawName.isEmpty()) {
                    String parsedName = PlaceholderAPI.setPlaceholders(target, rawName);
                    meta.displayName(SERIALIZER.deserialize(parsedName).decoration(TextDecoration.ITALIC, false));
                }

                // Format Lore
                List<String> rawLore = itemConfig.getStringList("lore");
                List<Component> finalLore = new ArrayList<>();
                for (String line : rawLore) {
                    String parsedLine = PlaceholderAPI.setPlaceholders(target, line);
                    finalLore.add(SERIALIZER.deserialize(parsedLine).decoration(TextDecoration.ITALIC, false));
                }
                meta.lore(finalLore);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);

                // Handle Player Heads
                if (mat == Material.PLAYER_HEAD && meta instanceof SkullMeta skullMeta) {
                    skullMeta.setOwningPlayer(target);
                }

                item.setItemMeta(meta);

                // Set to Slot(s)
                if (itemConfig.contains("slots")) {
                    List<Integer> slots = itemConfig.getIntegerList("slots");
                    for (int slot : slots) {
                        if (slot >= 0 && slot < size) inv.setItem(slot, item);
                    }
                } else if (itemConfig.contains("slot")) {
                    int slot = itemConfig.getInt("slot");
                    if (slot >= 0 && slot < size) inv.setItem(slot, item);
                }
            }
        }

        viewer.openInventory(inv);
    }
}
