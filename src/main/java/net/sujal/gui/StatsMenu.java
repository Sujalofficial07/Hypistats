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

    // viewer = Jo menu dekh raha hai, target = Jiske stats dekhne hain
    public static void open(Player viewer, Player target, StatsAPI statsAPI) {
        String title = viewer.equals(target) ? "Your SkyBlock Profile" : target.getName() + "'s Profile";
        Inventory inv = Bukkit.createInventory(new MenuHolder(), 54, Component.text(title, NamedTextColor.DARK_GRAY));

        // Background Glass
        ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta glassMeta = glass.getItemMeta();
        glassMeta.displayName(Component.text(" "));
        glass.setItemMeta(glassMeta);
        for (int i = 0; i < 54; i++) inv.setItem(i, glass);

        // Target Player Head
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setOwningPlayer(target);
        headMeta.displayName(Component.text(target.getName() + "'s Stats", NamedTextColor.GREEN).decorate(TextDecoration.BOLD));
        head.setItemMeta(headMeta);
        inv.setItem(13, head);

        // 1. SURVIVAL STATS (Golden Apple)
        inv.setItem(20, createCategoryItem(Material.GOLDEN_APPLE, "Survival Stats", NamedTextColor.RED,
                "Health", statsAPI.getFinalStat(target.getUniqueId(), StatType.HEALTH),
                "Defense", statsAPI.getFinalStat(target.getUniqueId(), StatType.DEFENSE),
                "Health Regen", statsAPI.getFinalStat(target.getUniqueId(), StatType.HEALTH_REGEN),
                "Vitality", statsAPI.getFinalStat(target.getUniqueId(), StatType.VITALITY),
                "True Defense", statsAPI.getFinalStat(target.getUniqueId(), StatType.TRUE_DEFENSE)));

        // 2. COMBAT STATS (Iron Sword)
        inv.setItem(21, createCategoryItem(Material.IRON_SWORD, "Combat Stats", NamedTextColor.RED,
                "Strength", statsAPI.getFinalStat(target.getUniqueId(), StatType.STRENGTH),
                "Crit Chance", statsAPI.getFinalStat(target.getUniqueId(), StatType.CRIT_CHANCE),
                "Crit Damage", statsAPI.getFinalStat(target.getUniqueId(), StatType.CRIT_DAMAGE),
                "Ferocity", statsAPI.getFinalStat(target.getUniqueId(), StatType.FEROCITY),
                "Bonus Attack Speed", statsAPI.getFinalStat(target.getUniqueId(), StatType.BONUS_ATTACK_SPEED)));

        // 3. MAGIC STATS (Blaze Powder)
        inv.setItem(22, createCategoryItem(Material.BLAZE_POWDER, "Magic Stats", NamedTextColor.AQUA,
                "Intelligence", statsAPI.getFinalStat(target.getUniqueId(), StatType.INTELLIGENCE),
                "Ability Damage", statsAPI.getFinalStat(target.getUniqueId(), StatType.ABILITY_DAMAGE),
                "Mana Regen", statsAPI.getFinalStat(target.getUniqueId(), StatType.MANA_REGEN)));

        // 4. UTILITY STATS (Feather)
        inv.setItem(23, createCategoryItem(Material.FEATHER, "Utility Stats", NamedTextColor.WHITE,
                "Speed", statsAPI.getFinalStat(target.getUniqueId(), StatType.SPEED),
                "Magic Find", statsAPI.getFinalStat(target.getUniqueId(), StatType.MAGIC_FIND),
                "Pet Luck", statsAPI.getFinalStat(target.getUniqueId(), StatType.PET_LUCK),
                "Sea Creature Chance", statsAPI.getFinalStat(target.getUniqueId(), StatType.SEA_CREATURE_CHANCE)));

        // 5. GATHERING STATS (Diamond Pickaxe)
        inv.setItem(24, createCategoryItem(Material.DIAMOND_PICKAXE, "Gathering Stats", NamedTextColor.GOLD,
                "Mining Speed", statsAPI.getFinalStat(target.getUniqueId(), StatType.MINING_SPEED),
                "Mining Fortune", statsAPI.getFinalStat(target.getUniqueId(), StatType.MINING_FORTUNE),
                "Farming Fortune", statsAPI.getFinalStat(target.getUniqueId(), StatType.FARMING_FORTUNE),
                "Foraging Fortune", statsAPI.getFinalStat(target.getUniqueId(), StatType.FORAGING_FORTUNE),
                "Pristine", statsAPI.getFinalStat(target.getUniqueId(), StatType.PRISTINE)));

        // Close Button
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.displayName(Component.text("Close", NamedTextColor.RED).decorate(TextDecoration.BOLD));
        close.setItemMeta(closeMeta);
        inv.setItem(49, close);

        viewer.openInventory(inv);
    }

    private static ItemStack createCategoryItem(Material mat, String title, NamedTextColor titleColor, Object... stats) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(title, titleColor).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));
        
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(""));
        
        for (int i = 0; i < stats.length; i += 2) {
            String statName = (String) stats[i];
            double statValue = (double) stats[i + 1];
            
            // Format lore: ✤ Strength: 100
            lore.add(Component.text("✤ " + statName + ": ", NamedTextColor.GRAY)
                    .append(Component.text(statValue, NamedTextColor.WHITE))
                    .decoration(TextDecoration.ITALIC, false));
        }
        
        lore.add(Component.text(""));
        meta.lore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }
}
