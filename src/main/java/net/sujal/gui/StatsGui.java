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

    // Helper method to format numbers like 1208.1
    private static String fmt(double value) {
        return String.format("%.1f", value);
    }

    // Main Menu: Equipment and Stats
    public static void openMainMenu(Player player, SkyblockCore plugin) {
        MenuBuilder menu = new MenuBuilder(54, Component.text("Your Equipment and Stats", NamedTextColor.DARK_GRAY));
        fillBackground(menu);
        UUID uuid = player.getUniqueId();

        // Combat Stats Icon
        ItemStack combatItem = createIcon(Material.IRON_SWORD, "§cCombat Stats",
                "§7Gives you a better chance at", "§7fighting strong monsters.", "",
                "§7Health: §c" + fmt(plugin.getApi().getFinalStat(uuid, StatType.MAX_HEALTH)),
                "§7Defense: §a" + fmt(plugin.getApi().getFinalStat(uuid, StatType.DEFENSE)),
                "§7Strength: §c" + fmt(plugin.getApi().getFinalStat(uuid, StatType.STRENGTH)),
                "", "§eClick for details!");
        
        // Gathering Stats Icon
        ItemStack gatheringItem = createIcon(Material.STONE_PICKAXE, "§aGathering Stats",
                "§7Lets you collect and harvest", "§7better items, or more of them.", "",
                "§7Mining Fortune: §6" + fmt(plugin.getApi().getFinalStat(uuid, StatType.MINING_FORTUNE)),
                "§7Farming Fortune: §6" + fmt(plugin.getApi().getFinalStat(uuid, StatType.FARMING_FORTUNE)),
                "§7Foraging Fortune: §6" + fmt(plugin.getApi().getFinalStat(uuid, StatType.FORAGING_FORTUNE)),
                "", "§eClick for details!");

        // Misc Stats Icon
        ItemStack miscItem = createIcon(Material.COMPASS, "§bMisc Stats",
                "§7Augments various aspects of your", "§7gameplay!", "",
                "§7Speed: §f" + fmt(plugin.getApi().getFinalStat(uuid, StatType.SPEED)),
                "§7Magic Find: §b" + fmt(plugin.getApi().getFinalStat(uuid, StatType.MAGIC_FIND)),
                "§7Pet Luck: §d" + fmt(plugin.getApi().getFinalStat(uuid, StatType.PET_LUCK)),
                "", "§eClick for details!");

        menu.getInventory().setItem(24, combatItem);
        menu.getInventory().setItem(25, gatheringItem);
        menu.getInventory().setItem(26, miscItem);

        player.openInventory(menu.getInventory());
    }

    // Sub-Menu: Combat Breakdown
    public static void openCombatStats(Player player, SkyblockCore plugin) {
        MenuBuilder menu = new MenuBuilder(54, Component.text("Your Stats Breakdown", NamedTextColor.DARK_GRAY));
        fillBackground(menu);
        UUID uuid = player.getUniqueId();

        // Exact icons matching the video
        menu.getInventory().setItem(10, createIcon(Material.GOLDEN_APPLE, "§c❤ Health §f" + fmt(plugin.getApi().getFinalStat(uuid, StatType.MAX_HEALTH)), "§7Increases your maximum health."));
        menu.getInventory().setItem(11, createIcon(Material.IRON_CHESTPLATE, "§a❈ Defense §f" + fmt(plugin.getApi().getFinalStat(uuid, StatType.DEFENSE)), "§7Reduces the damage that you", "§7take from enemies."));
        menu.getInventory().setItem(12, createIcon(Material.BLAZE_POWDER, "§c❁ Strength §f" + fmt(plugin.getApi().getFinalStat(uuid, StatType.STRENGTH)), "§7Increases the damage you deal."));
        menu.getInventory().setItem(13, createIcon(Material.BOOK, "§9☣ Crit Chance §f" + fmt(plugin.getApi().getFinalStat(uuid, StatType.CRIT_CHANCE)) + "%", "§7Chance to land a Critical Hit."));
        menu.getInventory().setItem(14, createIcon(Material.DIAMOND_SWORD, "§9☠ Crit Damage §f" + fmt(plugin.getApi().getFinalStat(uuid, StatType.CRIT_DAMAGE)) + "%", "§7Multiplies damage on a Critical Hit."));
        menu.getInventory().setItem(15, createIcon(Material.GOLDEN_AXE, "§e⚔ Bonus Attack Speed §f" + fmt(plugin.getApi().getFinalStat(uuid, StatType.ATTACK_SPEED)), "§7Decreases time between hits."));
        menu.getInventory().setItem(16, createIcon(Material.BEACON, "§c๑ Ability Damage §f" + fmt(plugin.getApi().getFinalStat(uuid, StatType.ABILITY_DAMAGE)) + "%", "§7Increases damage of spells."));
        
        menu.getInventory().setItem(19, createIcon(Material.WHITE_DYE, "§f❂ True Defense §f" + fmt(plugin.getApi().getFinalStat(uuid, StatType.TRUE_DEFENSE)), "§7Reduces True Damage taken."));
        menu.getInventory().setItem(20, createIcon(Material.RED_DYE, "§c⫽ Ferocity §f" + fmt(plugin.getApi().getFinalStat(uuid, StatType.FEROCITY)), "§7Chance to double-strike enemies."));
        menu.getInventory().setItem(21, createIcon(Material.GLISTERING_MELON_SLICE, "§4♨ Vitality §f" + fmt(plugin.getApi().getFinalStat(uuid, StatType.VITALITY)), "§7Increases incoming healing."));
        menu.getInventory().setItem(22, createIcon(Material.FISHING_ROD, "§e↹ Swing Range §f" + fmt(plugin.getApi().getFinalStat(uuid, StatType.SWING_RANGE)), "§7Increases your melee hit range."));

        addBackButton(menu);
        player.openInventory(menu.getInventory());
    }

    // Sub-Menu: Gathering Breakdown
    public static void openGatheringStats(Player player, SkyblockCore plugin) {
        MenuBuilder menu = new MenuBuilder(54, Component.text("Your Stats Breakdown", NamedTextColor.DARK_GRAY));
        fillBackground(menu);
        UUID uuid = player.getUniqueId();

        menu.getInventory().setItem(10, createIcon(Material.DIAMOND_PICKAXE, "§6☘ Mining Fortune §f" + fmt(plugin.getApi().getFinalStat(uuid, StatType.MINING_FORTUNE)), "§7Chance to get multiple drops from ores."));
        menu.getInventory().setItem(11, createIcon(Material.GOLDEN_HOE, "§6☘ Farming Fortune §f" + fmt(plugin.getApi().getFinalStat(uuid, StatType.FARMING_FORTUNE)), "§7Chance to get multiple drops from crops."));
        menu.getInventory().setItem(12, createIcon(Material.OAK_SAPLING, "§6☘ Foraging Fortune §f" + fmt(plugin.getApi().getFinalStat(uuid, StatType.FORAGING_FORTUNE)), "§7Chance to get multiple drops from wood."));

        addBackButton(menu);
        player.openInventory(menu.getInventory());
    }

    // Sub-Menu: Misc Breakdown
    public static void openMiscStats(Player player, SkyblockCore plugin) {
        MenuBuilder menu = new MenuBuilder(54, Component.text("Your Stats Breakdown", NamedTextColor.DARK_GRAY));
        fillBackground(menu);
        UUID uuid = player.getUniqueId();

        menu.getInventory().setItem(10, createIcon(Material.SUGAR, "§f✦ Speed §f" + fmt(plugin.getApi().getFinalStat(uuid, StatType.SPEED)), "§7Increases how fast you walk."));
        menu.getInventory().setItem(11, createIcon(Material.PRISMARINE_CRYSTALS, "§b✯ Magic Find §f" + fmt(plugin.getApi().getFinalStat(uuid, StatType.MAGIC_FIND)), "§7Increases how many rare items you find."));
        menu.getInventory().setItem(12, createIcon(Material.BONE, "§d♣ Pet Luck §f" + fmt(plugin.getApi().getFinalStat(uuid, StatType.PET_LUCK)), "§7Increases chance to craft/find rare pets."));

        addBackButton(menu);
        player.openInventory(menu.getInventory());
    }

    // --- Utility Methods ---
    private static void fillBackground(MenuBuilder menu) {
        ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta glassMeta = glass.getItemMeta();
        glassMeta.displayName(Component.text(" "));
        glass.setItemMeta(glassMeta);
        for (int i = 0; i < 54; i++) menu.getInventory().setItem(i, glass);
    }

    private static void addBackButton(MenuBuilder menu) {
        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.displayName(Component.text("§aGo Back"));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("§7To Your Equipment and Stats"));
        backMeta.lore(lore);
        back.setItemMeta(backMeta);
        menu.getInventory().setItem(49, back);
    }

    private static ItemStack createIcon(Material mat, String name, String... loreLines) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(name));
        List<Component> lore = new ArrayList<>();
        for (String line : loreLines) {
            lore.add(Component.text(line));
        }
        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
