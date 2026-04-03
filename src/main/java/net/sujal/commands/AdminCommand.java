package net.sujal.commands;

import net.sujal.SkyblockCore;
import net.sujal.stats.StatType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminCommand implements CommandExecutor {
    private final SkyblockCore plugin;

    public AdminCommand(SkyblockCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("skyblockcore.admin")) {
            sender.sendMessage("§cYou don't have permission.");
            return true;
        }

        if (args.length < 4) {
            sender.sendMessage("§cUsage: /sbadmin <set|add> <player> <stat> <value>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage("§cPlayer not found.");
            return true;
        }

        StatType type;
        try {
            type = StatType.valueOf(args[2].toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage("§cInvalid stat type.");
            return true;
        }

        double value;
        try {
            value = Double.parseDouble(args[3]);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cValue must be a number.");
            return true;
        }

        if (args[0].equalsIgnoreCase("set")) {
            plugin.getApi().setBaseStat(target.getUniqueId(), type, value);
            sender.sendMessage("§aSet " + target.getName() + "'s " + type.name() + " to " + value);
        } else if (args[0].equalsIgnoreCase("add")) {
            double current = plugin.getApi().getBaseStat(target.getUniqueId(), type);
            plugin.getApi().setBaseStat(target.getUniqueId(), type, current + value);
            sender.sendMessage("§aAdded " + value + " to " + target.getName() + "'s " + type.name());
        }

        return true;
    }
}
