package net.github.monkeee.ecoNETEnderChests.listeners;

import net.github.monkeee.ecoNETEnderChests.commands.EnderChestCommand;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnderChestOpenEvent implements Listener {

    @EventHandler
    public void onEnderChestOpen(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getClickedBlock() == null) return;
        if (e.getClickedBlock().getType() != Material.ENDER_CHEST) return;

        e.setCancelled(true);

        Player player = e.getPlayer();
        EnderChestCommand.openChestsFor(player, player);
    }
}
