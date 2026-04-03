package net.sujal.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
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

    // ==========================================
    // 1. MAIN MENU: "Your Equipment and Stats"
    // ==========================================
    public static void openMainMenu(Player player, SkyblockCore plugin) {
        MenuBuilder menu = new MenuBuilder(54, Component.text("Your Equipment and Stats", NamedTextColor.DARK_GRAY));
        UUID uuid = player.getUniqueId();

        // Filler Glass Pane (Background)
        ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta glassMeta = glass.getItemMeta();
        glassMeta.displayName(Component.text(" "));
        glass.setItemMeta(glassMeta);

        // Fill right side background like Hypixel
        for (int i = 0; i < 54; i++) {
            if (i % 9 > 4) menu.getInventory().setItem(i, glass); 
        }

        // ==========================================
        // CATEGORY ICONS
        // ==========================================

        // 1. Combat Stats (Iron Sword - Slot 23)
        ItemStack combatItem = new ItemStack(Material.IRON_SWORD);
        ItemMeta combatMeta = combatItem.getItemMeta();
        combatMeta.displayName(Component.text("Combat Stats", NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
        List<Component> combatLore = new ArrayList<>();
        combatLore.add(Component.text("Gives you a better chance at", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        combatLore.add(Component.text("fighting strong monsters.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        combatLore.add(Component.text(" "));
        combatLore.add(Component.text("❤ Health ", NamedTextColor.RED).append(Component.text(plugin.getApi().getFinalStat(uuid, StatType.MAX_HEALTH), NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));
        combatLore.add(Component.text("❈ Defense ", NamedTextColor.GREEN).append(Component.text(plugin.getApi().getFinalStat(uuid, StatType.DEFENSE), NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));
        combatLore.add(Component.text("❁ Strength ", NamedTextColor.RED).append(Component.text(plugin.getApi().getFinalStat(uuid, StatType.STRENGTH), NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));
        combatLore.add(Component.text(" "));
        combatLore.add(Component.text("Click for details!", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
        combatMeta.lore(combatLore);
        combatItem.setItemMeta(combatMeta);

        // 2. Gathering Stats (Diamond Pickaxe - Slot 24)
        ItemStack gatheringItem = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta gatherMeta = gatheringItem.getItemMeta();
        gatherMeta.displayName(Component.text("Gathering Stats", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        List<Component> gatherLore = new ArrayList<>();
        gatherLore.add(Component.text("Lets you collect and harvest", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        gatherLore.add(Component.text("better items, or more of them.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        gatherLore.add(Component.text(" "));
        gatherLore.add(Component.text("☘ Mining Fortune ", NamedTextColor.GOLD).append(Component.text(plugin.getApi().getFinalStat(uuid, StatType.MINING_FORTUNE), NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));
        gatherLore.add(Component.text("☘ Farming Fortune ", NamedTextColor.GOLD).append(Component.text(plugin.getApi().getFinalStat(uuid, StatType.FARMING_FORTUNE), NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));
        gatherLore.add(Component.text(" "));
        gatherLore.add(Component.text("Click for details!", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
        gatherMeta.lore(gatherLore);
        gatheringItem.setItemMeta(gatherMeta);

        // 3. Misc Stats (Compass/Clock - Slot 25)
        ItemStack miscItem = new ItemStack(Material.COMPASS);
        ItemMeta miscMeta = miscItem.getItemMeta();
        miscMeta.displayName(Component.text("Misc Stats", NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false));
        List<Component> miscLore = new ArrayList<>();
        miscLore.add(Component.text("Augments various aspects of your", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        miscLore.add(Component.text("gameplay!", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        miscLore.add(Component.text(" "));
        miscLore.add(Component.text("✦ Speed ", NamedTextColor.WHITE).append(Component.text(plugin.getApi().getFinalStat(uuid, StatType.SPEED), NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));
        miscLore.add(Component.text("✯ Magic Find ", NamedTextColor.AQUA).append(Component.text(plugin.getApi().getFinalStat(uuid, StatType.MAGIC_FIND), NamedTextColor.WHITE)).decoration(TextDecoration.ITALIC, false));
        miscLore.add(Component.text(" "));
        miscLore.add(Component.text("Click for details!", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
        miscMeta.lore(miscLore);
        miscItem.setItemMeta(miscMeta);

        // Close Button
        menu.getInventory().setItem(49, createCloseButton());

        menu.getInventory().setItem(23, combatItem);
        menu.getInventory().setItem(24, gatheringItem);
        menu.getInventory().setItem(25, miscItem);

        player.openInventory(menu.getInventory());
    }

    // ==========================================
    // 2. COMBAT STATS BREAKDOWN
    // ==========================================
    public static void openCombatMenu(Player player, SkyblockCore plugin) {
        MenuBuilder menu = new MenuBuilder(54, Component.text("Your Stats Breakdown", NamedTextColor.DARK_GRAY));
        UUID uuid = player.getUniqueId();

        menu.getInventory().setItem(10, createStatItem(Material.GOLDEN_APPLE, "❤ Health", plugin.getApi().getFinalStat(uuid, StatType.MAX_HEALTH), NamedTextColor.RED, "Your Health stat increases your maximum health."));
        menu.getInventory().setItem(11, createStatItem(Material.IRON_CHESTPLATE, "❈ Defense", plugin.getApi().getFinalStat(uuid, StatType.DEFENSE), NamedTextColor.GREEN, "Reduces the damage that you take from enemies."));
        menu.getInventory().setItem(12, createStatItem(Material.BLAZE_POWDER, "❁ Strength", plugin.getApi().getFinalStat(uuid, StatType.STRENGTH), NamedTextColor.RED, "Strength increases the damage you deal."));
        menu.getInventory().setItem(13, createStatItem(Material.ENCHANTED_BOOK, "✎ Intelligence", plugin.getApi().getFinalStat(uuid, StatType.INTELLIGENCE), NamedTextColor.AQUA, "Intelligence increases your maximum Mana."));
        menu.getInventory().setItem(14, createStatItem(Material.SPIDER_EYE, "☣ Crit Chance", plugin.getApi().getFinalStat(uuid, StatType.CRIT_CHANCE), NamedTextColor.BLUE, "Chance that you land a Critical Hit."));
        menu.getInventory().setItem(15, createStatItem(Material.ROTTEN_FLESH, "☠ Crit Damage", plugin.getApi().getFinalStat(uuid, StatType.CRIT_DAMAGE), NamedTextColor.BLUE, "Multiplies the damage when you land a Critical Hit."));
        
        menu.getInventory().setItem(19, createStatItem(Material.STONE_SWORD, "⚔ Bonus Attack Speed", plugin.getApi().getFinalStat(uuid, StatType.ATTACK_SPEED), NamedTextColor.YELLOW, "Decreases the time between hits."));
        menu.getInventory().setItem(20, createStatItem(Material.GHAST_TEAR, "❂ True Defense", plugin.getApi().getFinalStat(uuid, StatType.TRUE_DEFENSE), NamedTextColor.WHITE, "Reduces True Damage taken."));
        menu.getInventory().setItem(21, createStatItem(Material.QUARTZ, "⫽ Ferocity", plugin.getApi().getFinalStat(uuid, StatType.FEROCITY), NamedTextColor.RED, "Chance to double-strike enemies."));
        menu.getInventory().setItem(22, createStatItem(Material.RED_DYE, "❣ Vitality", plugin.getApi().getFinalStat(uuid, StatType.VITALITY), NamedTextColor.DARK_RED, "Increases incoming healing."));

        addNavButtons(menu);
        player.openInventory(menu.getInventory());
    }

    // ==========================================
    // 3. GATHERING STATS BREAKDOWN
    // ==========================================
    public static void openGatheringMenu(Player player, SkyblockCore plugin) {
        MenuBuilder menu = new MenuBuilder(54, Component.text("Your Stats Breakdown", NamedTextColor.DARK_GRAY));
        UUID uuid = player.getUniqueId();

        menu.getInventory().setItem(10, createStatItem(Material.DIAMOND_PICKAXE, "☘ Mining Fortune", plugin.getApi().getFinalStat(uuid, StatType.MINING_FORTUNE), NamedTextColor.GOLD, "Chance to get multiple drops from ores."));
        menu.getInventory().setItem(11, createStatItem(Material.GOLDEN_HOE, "☘ Farming Fortune", plugin.getApi().getFinalStat(uuid, StatType.FARMING_FORTUNE), NamedTextColor.GOLD, "Chance to get multiple drops from crops."));
        menu.getInventory().setItem(12, createStatItem(Material.IRON_AXE, "☘ Foraging Fortune", plugin.getApi().getFinalStat(uuid, StatType.FORAGING_FORTUNE), NamedTextColor.GOLD, "Chance to get multiple drops from logs."));

        addNavButtons(menu);
        player.openInventory(menu.getInventory());
    }

    // ==========================================
    // 4. MISC STATS BREAKDOWN
    // ==========================================
    public static void openMiscMenu(Player player, SkyblockCore plugin) {
        MenuBuilder menu = new MenuBuilder(54, Component.text("Your Stats Breakdown", NamedTextColor.DARK_GRAY));
        UUID uuid = player.getUniqueId();

        menu.getInventory().setItem(10, createStatItem(Material.SUGAR, "✦ Speed", plugin.getApi().getFinalStat(uuid, StatType.SPEED), NamedTextColor.WHITE, "Increases how fast you can walk."));
        menu.getInventory().setItem(11, createStatItem(Material.PRISMARINE_CRYSTALS, "α Sea Creature Chance", plugin.getApi().getFinalStat(uuid, StatType.SEA_CREATURE_CHANCE), NamedTextColor.DARK_AQUA, "Chance to catch Sea Creatures."));
        menu.getInventory().setItem(12, createStatItem(Material.BLAZE_POWDER, "✯ Magic Find", plugin.getApi().getFinalStat(uuid, StatType.MAGIC_FIND), NamedTextColor.AQUA, "Increases how many rare items you find."));
        menu.getInventory().setItem(13, createStatItem(Material.BONE, "♣ Pet Luck", plugin.getApi().getFinalStat(uuid, StatType.PET_LUCK), NamedTextColor.LIGHT_PURPLE, "Increases chance to find pets and craft higher tiers."));

        addNavButtons(menu);
        player.openInventory(menu.getInventory());
    }

    // ==========================================
    // HELPER METHODS
    // ==========================================
    private static void addNavButtons(MenuBuilder menu) {
        ItemStack backArrow = new ItemStack(Material.ARROW);
        ItemMeta arrowMeta = backArrow.getItemMeta();
        arrowMeta.displayName(Component.text("Go Back", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
        List<Component> arrowLore = new ArrayList<>();
        arrowLore.add(Component.text("To Your Equipment and Stats", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        arrowMeta.lore(arrowLore);
        backArrow.setItemMeta(arrowMeta);

        menu.getInventory().setItem(48, backArrow);
        menu.getInventory().setItem(49, createCloseButton());
    }

    private static ItemStack createCloseButton() {
        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta meta = barrier.getItemMeta();
        meta.displayName(Component.text("Close", NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
        barrier.setItemMeta(meta);
        return barrier;
    }

    private static ItemStack createStatItem(Material mat, String name, double value, NamedTextColor color, String desc) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        
        // Remove decimal if it's a whole number for cleaner look
        String valStr = (value % 1 == 0) ? String.valueOf((int) value) : String.format("%.1f", value);
        
        meta.displayName(Component.text(name + " " + valStr, color).decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(desc, NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
