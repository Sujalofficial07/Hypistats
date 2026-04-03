package net.sujal.commands;

import net.sujal.SkyblockCore;
import net.sujal.gui.StatsGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StatsCommand implements CommandExecutor {
    private final SkyblockCore plugin;

    public StatsCommand(SkyblockCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            StatsGui.openMainMenu(player, plugin);
            return true;
        }
        sender.sendMessage("Only players can use this command.");
        return true;
    }
}
