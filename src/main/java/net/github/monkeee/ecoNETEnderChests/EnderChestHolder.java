package net.github.monkeee.ecoNETEnderChests;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class EnderChestHolder implements InventoryHolder {

    private final Player player;

    public EnderChestHolder(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public @NotNull Inventory getInventory() {
        throw new UnsupportedOperationException();
    }
}
