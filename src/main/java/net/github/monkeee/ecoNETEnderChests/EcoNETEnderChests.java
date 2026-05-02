package net.github.monkeee.ecoNETEnderChests;

import net.github.monkeee.ecoNETEnderChests.commands.EnderChestCommand;
import net.github.monkeee.ecoNETEnderChests.listeners.InventoryClosingEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class EcoNETEnderChests extends JavaPlugin {

    public static Database database;

    @Override
    public void onEnable() {
        database = new Database(this);
        getLogger().info("Loading...");

        Objects.requireNonNull(getCommand("enderchest")).setExecutor(new EnderChestCommand());
        getServer().getPluginManager().registerEvents(new InventoryClosingEvent(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Shutting Down...");
    }

    public static Database getDatabase() {
        return database;
    }
}
