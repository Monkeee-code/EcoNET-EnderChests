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

        Rows rows;
        if (player.hasPermission("econet.enderchest.rows.6")) rows = Rows.SIX;
        else if (player.hasPermission("econet.enderchest.rows.5")) rows = Rows.FIVE;
        else if (player.hasPermission("econet.enderchest.rows.4")) rows = Rows.FOUR;
        else if (player.hasPermission("econet.enderchest.rows.3")) rows = Rows.THREE;
        else if (player.hasPermission("econet.enderchest.rows.2")) rows = Rows.TWO;
        else if (player.hasPermission("econet.enderchest.rows.1")) rows = Rows.ONE;
        else rows = Rows.THREE;

        Inventory inv = db.loadInventory(player);
        if (inv == null) {
            inv = Bukkit.createInventory(new EnderChestHolder(player), rows.getInt()*9, player.getName()+"'s Ender Chest");
        }
        player.openInventory(inv);

        return true;
    }
}
