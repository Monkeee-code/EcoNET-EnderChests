package net.github.monkeee.ecoNETEnderChests;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import net.github.monkeee.ecoNETEnderChests.commands.EnderChestCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.sql.*;
import java.util.UUID;

@SuppressWarnings("CallToPrintStackTrace")
public class Database {

    private final EcoNETEnderChests plugin;

    public Database(EcoNETEnderChests plugin) {
        this.plugin = plugin;
    }

    public Connection getPlayerConnection(UUID player) {
        try {
            File folder = new File(plugin.getDataFolder(), "InventoryData");
            //noinspection ResultOfMethodCallIgnored
            folder.mkdirs();

            File dbFile = new File(folder, player.toString()+".db");
            String url = "jdbc:sqlite:"+dbFile.getAbsolutePath();

            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to get the player connection: "+e.getMessage());
            return null;
        }
    }

    public void saveInventory(Player player, Inventory inv) {
        try (Connection conn = getPlayerConnection(player.getUniqueId())) {
            if (conn == null) return;

            conn.createStatement().execute("""
                CREATE TABLE IF NOT EXISTS inventory (
                    slot INTEGER PRIMARY KEY,
                    nbt TEXT,
                )""");

            String sql = "INSERT OR REPLACE INTO inventory (slot, nbt) VALUES (?, ?)";

            for (int slot = 0; slot < inv.getSize(); slot++) {
                ItemStack item = inv.getItem(slot);
                if (item == null) continue;

                ReadWriteNBT nbtItem = NBT.itemStackToNBT(item);

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, slot);
                    stmt.setString(2, nbtItem.toString());
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public Inventory loadInventory(Player player) {
        try (Connection conn = getPlayerConnection(player.getUniqueId())) {
            if (conn == null) return null;

            ResultSet rs = conn.createStatement().executeQuery("""
                SELECT slot, nbt FROM inventory;""");

            int rows = EnderChestCommand.getRows(player);
            Inventory inv = Bukkit.createInventory(new EnderChestHolder(player), rows*9, Component.text(player.getName()+"'s Ender Chest"));

            while (rs.next()) {
                int slot = rs.getInt("slot");
                if (slot >= rows*9) continue;

                ReadWriteNBT container = NBT.parseNBT(rs.getString("nbt"));
                inv.setItem(slot, NBT.itemStackFromNBT(container));
            }
            return inv;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
