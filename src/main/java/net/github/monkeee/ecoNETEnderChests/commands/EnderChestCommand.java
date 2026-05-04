package net.github.monkeee.ecoNETEnderChests.commands;

import net.github.monkeee.ecoNETEnderChests.Database;
import net.github.monkeee.ecoNETEnderChests.EcoNETEnderChests;
import net.github.monkeee.ecoNETEnderChests.EnderChestHolder;
import net.github.monkeee.ecoNETEnderChests.Rows;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class EnderChestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can execute this command!");
            return true;
        }
        if (!player.hasPermission("econet.enderchest.ec")) {
            player.sendMessage(Component.text("You do not have the permission, to use this command!", NamedTextColor.RED));
            return true;
        }
        Database db = EcoNETEnderChests.getDatabase();

        return true;
    }

    public static void openChestsFor(Player viewer, Player owner) {
        EcoNETEnderChests plugin = EcoNETEnderChests.getInstance();
        Database db = EcoNETEnderChests.getDatabase();

        Inventory inv = plugin.getOpenChests(owner.getUniqueId());
        if (inv == null) {
            inv = db.loadInventory(owner);
            if (inv == null) {
                inv = Bukkit.createInventory(new EnderChestHolder(owner), getRows(owner)*9, Component.text(owner.getName()+"'s Ender Chest"));
            }
            plugin.setOpenChests(owner.getUniqueId(), inv);
        }
        viewer.openInventory(inv);
    }

    @NotNull
    public static Integer getRows(Player player) {
        if (player.hasPermission("econet.enderchest.rows.6")) return 6;
        else if (player.hasPermission("econet.enderchest.rows.5")) return 5;
        else if (player.hasPermission("econet.enderchest.rows.4")) return 4;
        else if (player.hasPermission("econet.enderchest.rows.3")) return 3;
        else if (player.hasPermission("econet.enderchest.rows.2")) return 2;
        else if (player.hasPermission("econet.enderchest.rows.1")) return 1;
        else return 3;
    }
}
