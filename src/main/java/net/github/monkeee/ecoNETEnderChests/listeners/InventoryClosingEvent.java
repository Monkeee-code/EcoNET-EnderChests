package net.github.monkeee.ecoNETEnderChests.listeners;

import net.github.monkeee.ecoNETEnderChests.Database;
import net.github.monkeee.ecoNETEnderChests.EcoNETEnderChests;
import net.github.monkeee.ecoNETEnderChests.EnderChestHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.UUID;

public class InventoryClosingEvent implements Listener {

    @EventHandler
    public void onInvClose(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player player)) return;
        if (!(e.getInventory().getHolder() instanceof EnderChestHolder holder)) return;

        EcoNETEnderChests plugin = EcoNETEnderChests.getInstance();
        UUID ownerUUID = holder.getPlayer().getUniqueId();
        Database db = EcoNETEnderChests.getDatabase();

        long stillOpen = Bukkit.getOnlinePlayers().stream()
                .filter(p -> p != player)
                .filter(p -> p.getOpenInventory().getTopInventory().getHolder() instanceof EnderChestHolder h
                && h.getPlayer().getUniqueId().equals(ownerUUID)).count();

        if (stillOpen == 0) {
            db.saveInventory(holder.getPlayer(), e.getInventory());
            plugin.removeOpenChests(ownerUUID);
        }

    }
}
