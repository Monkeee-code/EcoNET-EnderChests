package net.github.monkeee.ecoNETEnderChests.listeners;

import net.github.monkeee.ecoNETEnderChests.Database;
import net.github.monkeee.ecoNETEnderChests.EcoNETEnderChests;
import net.github.monkeee.ecoNETEnderChests.EnderChestHolder;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryClosingEvent implements Listener {

    @EventHandler
    public void onInvClose(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player player)) return;
        if (!(e.getInventory().getHolder() instanceof EnderChestHolder holder)) return;

        Database db = EcoNETEnderChests.getDatabase();
        db.saveInventory(holder.getPlayer(), e.getInventory());
    }
}
