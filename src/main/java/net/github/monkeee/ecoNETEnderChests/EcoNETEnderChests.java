package net.github.monkeee.ecoNETEnderChests;

import net.github.monkeee.ecoNETEnderChests.commands.EnderChestCommand;
import net.github.monkeee.ecoNETEnderChests.commands.EnderChestSeeCommand;
import net.github.monkeee.ecoNETEnderChests.listeners.EnderChestOpenEvent;
import net.github.monkeee.ecoNETEnderChests.listeners.InventoryClosingEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class EcoNETEnderChests extends JavaPlugin {

    public static Database database;
    public static EcoNETEnderChests instance;
    public Map<UUID, Inventory> openChests = new ConcurrentHashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        database = new Database(this);
        getLogger().info("Loading...");

        Objects.requireNonNull(getCommand("enderchest")).setExecutor(new EnderChestCommand());
        Objects.requireNonNull(getCommand("enderchestsee")).setExecutor(new EnderChestSeeCommand());
        Objects.requireNonNull(getCommand("enderchestsee")).setTabCompleter(new EnderChestSeeCommand());
        getServer().getPluginManager().registerEvents(new InventoryClosingEvent(), this);
        getServer().getPluginManager().registerEvents(new EnderChestOpenEvent(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Shutting Down...");
    }

    public static Database getDatabase() {
        return database;
    }

    public static EcoNETEnderChests getInstance() {
        return instance;
    }

    public void setOpenChests(UUID owner, Inventory inv) {
        openChests.put(owner, inv);
    }
    public Inventory getOpenChests(UUID owner) {
        return openChests.get(owner);
    }
    public void removeOpenChests(UUID owner) {
        openChests.remove(owner);
    }

}
