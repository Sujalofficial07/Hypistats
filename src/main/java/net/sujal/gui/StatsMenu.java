package net.sujal.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.sujal.api.StatsAPI;
import net.sujal.stats.shared.StatType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class StatsMenu {

    public static void open(Player player, StatsAPI statsAPI) {
        Inventory inv = Bukkit.createInventory(new MenuHolder(), 54, Component.text("SkyBlock Profile", NamedTextColor.DARK_GRAY));

        // Fill background with black glass panes
        ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta glassMeta = glass.getItemMeta();
        glassMeta.displayName(Component.text(" "));
        glass.setItemMeta(glassMeta);
        for (int i = 0; i < 54; i++) inv.setItem(i, glass);

        // Player Head
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setOwningPlayer(player);
        headMeta.displayName(Component.text("Your SkyBlock Profile", NamedTextColor.GREEN).decorate(TextDecoration.BOLD));
        head.setItemMeta(headMeta);
        inv.setItem(13, head);

        // Stats Items
        inv.setItem(20, createStatItem(Material.GOLDEN_APPLE, "Health", NamedTextColor.RED, statsAPI.getFinalStat(player.getUniqueId(), StatType.HEALTH)));
        inv.setItem(21, createStatItem(Material.IRON_CHESTPLATE, "Defense", NamedTextColor.GREEN, statsAPI.getFinalStat(player.getUniqueId(), StatType.DEFENSE)));
        inv.setItem(22, createStatItem(Material.BLAZE_POWDER, "Strength", NamedTextColor.RED, statsAPI.getFinalStat(player.getUniqueId(), StatType.STRENGTH)));
        inv.setItem(23, createStatItem(Material.SUGAR, "Speed", NamedTextColor.WHITE, statsAPI.getFinalStat(player.getUniqueId(), StatType.SPEED)));
        inv.setItem(24, createStatItem(Material.BONE, "Crit Chance", NamedTextColor.BLUE, statsAPI.getFinalStat(player.getUniqueId(), StatType.CRIT_CHANCE)));
        inv.setItem(29, createStatItem(Material.PAPER, "Crit Damage", NamedTextColor.BLUE, statsAPI.getFinalStat(player.getUniqueId(), StatType.CRIT_DAMAGE)));
        inv.setItem(30, createStatItem(Material.ENCHANTED_BOOK, "Intelligence", NamedTextColor.AQUA, statsAPI.getFinalStat(player.getUniqueId(), StatType.INTELLIGENCE)));
        inv.setItem(31, createStatItem(Material.GOLD_NUGGET, "Magic Find", NamedTextColor.AQUA, statsAPI.getFinalStat(player.getUniqueId(), StatType.MAGIC_FIND)));
        inv.setItem(32, createStatItem(Material.DIAMOND_PICKAXE, "Mining Speed", NamedTextColor.GOLD, statsAPI.getFinalStat(player.getUniqueId(), StatType.MINING_SPEED)));
        inv.setItem(33, createStatItem(Material.GOLDEN_HOE, "Farming Fortune", NamedTextColor.GOLD, statsAPI.getFinalStat(player.getUniqueId(), StatType.FARMING_FORTUNE)));

        player.openInventory(inv);
    }

    private static ItemStack createStatItem(Material mat, String name, NamedTextColor color, double value) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(name, color).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));
        
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(""));
        lore.add(Component.text("Base: ", NamedTextColor.GRAY).append(Component.text(value, color)).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text(""));
        meta.lore(lore);
        
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }
}
