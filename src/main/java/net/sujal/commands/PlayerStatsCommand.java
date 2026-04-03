package net.sujal.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.sujal.api.StatsAPI;
import net.sujal.gui.StatsMenu;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerStatsCommand implements CommandExecutor {

    private final StatsAPI statsAPI;

    public PlayerStatsCommand(StatsAPI statsAPI) {
        this.statsAPI = statsAPI;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Yeh command sirf players in-game use kar sakte hain!", NamedTextColor.RED));
            return true;
        }

        // Agar /stats likha hai bina kisi naam ke
        if (args.length == 0) {
            StatsMenu.open(player, player, statsAPI);
            return true;
        }

        // Agar /stats <player> likha hai
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            player.sendMessage(Component.text("Player nahi mila ya offline hai!", NamedTextColor.RED));
            return true;
        }

        StatsMenu.open(player, target, statsAPI);
        return true;
    }
}
