package net.sujal.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.sujal.SkyblockCore;
import net.sujal.api.StatsAPI;
import net.sujal.stats.shared.StatType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DevStatCommand implements CommandExecutor {

    private final StatsAPI statsAPI;

    public DevStatCommand(StatsAPI statsAPI) {
        this.statsAPI = statsAPI;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!sender.hasPermission("skyblock.admin")) {
            sender.sendMessage(Component.text("No permission.", NamedTextColor.RED));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(Component.text("Usage: /stat <set/add/get/inspect/menu/reload> [player] [stat] [value]", NamedTextColor.RED));
            return true;
        }

        String action = args[0].toLowerCase();

        // Reload Command
        if (action.equals("reload")) {
            SkyblockCore.getInstance().getConfigManager().reloadConfig();
            sender.sendMessage(Component.text("Config aur Menus reload ho gaye!", NamedTextColor.GREEN));
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(Component.text("Usage requires a player name for this action.", NamedTextColor.RED));
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            sender.sendMessage(Component.text("Player not found.", NamedTextColor.RED));
            return true;
        }

        // Open GUI Menu Command
        if (action.equals("menu")) {
            net.sujal.gui.StatsMenu.open(target, target, statsAPI);
            sender.sendMessage(Component.text("Opened GUI for " + target.getName(), NamedTextColor.GREEN));
            return true;
        }

        // Inspect Stats Command
        if (action.equals("inspect")) {
            sender.sendMessage(Component.text("Stats for " + target.getName() + ":", NamedTextColor.GREEN));
            for (StatType type : StatType.values()) {
                double val = statsAPI.getFinalStat(target.getUniqueId(), type);
                if (val > 0) {
                    sender.sendMessage(Component.text(type.name() + ": " + val, NamedTextColor.YELLOW));
                }
            }
            return true;
        }

        if (args.length < 3) return false;
        
        StatType statType;
        try {
            statType = StatType.valueOf(args[2].toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage(Component.text("Invalid stat type.", NamedTextColor.RED));
            return true;
        }

        // Get Stat Command
        if (action.equals("get")) {
            sender.sendMessage(Component.text(statType.name() + " = " + statsAPI.getFinalStat(target.getUniqueId(), statType), NamedTextColor.GREEN));
            return true;
        }

        if (args.length < 4) return false;
        double value;
        try {
            value = Double.parseDouble(args[3]);
        } catch (NumberFormatException e) {
            sender.sendMessage(Component.text("Invalid number format.", NamedTextColor.RED));
            return true;
        }

        // Set & Add Stat Commands
        switch (action) {
            case "set":
                statsAPI.setStat(target.getUniqueId(), statType, value);
                sender.sendMessage(Component.text("Set " + statType.name() + " to " + value + " for " + target.getName(), NamedTextColor.GREEN));
                break;
            case "add":
                statsAPI.addStat(target.getUniqueId(), statType, value);
                sender.sendMessage(Component.text("Added " + value + " to " + statType.name() + " for " + target.getName(), NamedTextColor.GREEN));
                break;
            default:
                sender.sendMessage(Component.text("Unknown action.", NamedTextColor.RED));
                break;
        }
        return true;
    }
}
