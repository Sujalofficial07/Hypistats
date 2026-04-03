package net.sujal.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.sujal.SkyblockCore;
import net.sujal.stats.StatType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StatsGui {
    
    public static void open(Player player, SkyblockCore plugin) {
        MenuBuilder menu = new MenuBuilder(54, Component.text("Your SkyBlock Profile", NamedTextColor.DARK_GRAY));
        UUID uuid = player.getUniqueId();
        
        // Background Glass
        ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta glassMeta = glass.getItemMeta();
        glassMeta.displayName(Component.text(" "));
        glass.setItemMeta(glassMeta);
        for (int i = 0; i < 54; i++) menu.getInventory().setItem(i, glass);

        // Combat Stats Item
        ItemStack combatItem = new ItemStack(Material.IRON_SWORD);
        ItemMeta combatMeta = combatItem.getItemMeta();
        combatMeta.displayName(Component.text("Combat Stats", NamedTextColor.RED));
        List<Component> combatLore = new ArrayList<>();
        combatLore.add(Component.text("Damage: " + plugin.getApi().getFinalStat(uuid, StatType.STRENGTH)));
        combatLore.add(Component.text("Crit Chance: " + plugin.getApi().getFinalStat(uuid, StatType.CRIT_CHANCE) + "%"));
        combatLore.add(Component.text("Crit Damage: " + plugin.getApi().getFinalStat(uuid, StatType.CRIT_DAMAGE) + "%"));
        combatLore.add(Component.text("Ferocity: " + plugin.getApi().getFinalStat(uuid, StatType.FEROCITY)));
        combatMeta.lore(combatLore);
        combatItem.setItemMeta(combatMeta);

        // Survival Stats Item
        ItemStack survivalItem = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta survMeta = survivalItem.getItemMeta();
        survMeta.displayName(Component.text("Survival Stats", NamedTextColor.GREEN));
        List<Component> survLore = new ArrayList<>();
        survLore.add(Component.text("Health: " + plugin.getApi().getFinalStat(uuid, StatType.MAX_HEALTH)));
        survLore.add(Component.text("Defense: " + plugin.getApi().getFinalStat(uuid, StatType.DEFENSE)));
        survLore.add(Component.text("True Defense: " + plugin.getApi().getFinalStat(uuid, StatType.TRUE_DEFENSE)));
        survLore.add(Component.text("Vitality: " + plugin.getApi().getFinalStat(uuid, StatType.VITALITY)));
        survMeta.lore(survLore);
        survivalItem.setItemMeta(survMeta);

        // Utility Stats Item
        ItemStack utilityItem = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta utilMeta = utilityItem.getItemMeta();
        utilMeta.displayName(Component.text("Utility & Skill Stats", NamedTextColor.AQUA));
        List<Component> utilLore = new ArrayList<>();
        utilLore.add(Component.text("Speed: " + plugin.getApi().getFinalStat(uuid, StatType.SPEED)));
        utilLore.add(Component.text("Intelligence: " + plugin.getApi().getFinalStat(uuid, StatType.INTELLIGENCE)));
        utilLore.add(Component.text("Magic Find: " + plugin.getApi().getFinalStat(uuid, StatType.MAGIC_FIND)));
        utilLore.add(Component.text("Mining Fortune: " + plugin.getApi().getFinalStat(uuid, StatType.MINING_FORTUNE)));
        utilMeta.lore(utilLore);
        utilityItem.setItemMeta(utilMeta);

        menu.getInventory().setItem(20, combatItem);
        menu.getInventory().setItem(22, survivalItem);
        menu.getInventory().setItem(24, utilityItem);

        player.openInventory(menu.getInventory());
    }
}
