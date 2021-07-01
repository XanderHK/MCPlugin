package com.github.rovey.ultimateparkour.Utilities.Server;

import com.github.rovey.ultimateparkour.Chat.ChatHandler;
import com.github.rovey.ultimateparkour.Parkour.MapEditor;
import com.github.rovey.ultimateparkour.Utilities.Helpers;
import com.github.rovey.ultimateparkour.Utilities.Parkour.CooldownHandler;
import com.github.rovey.ultimateparkour.Utilities.YmlHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class InventoryHandler implements Listener
{
    private static Inventory inv = null;

    private static final ItemStack parkourBook = new ItemStack(Material.BOOK);
    private static final ItemStack playerToggleHide = new ItemStack(Material.GLOWSTONE_DUST);
    private static final ItemStack playerToggleShow = new ItemStack(Material.REDSTONE);

    private static final String parkourBookName = "Parkour Roadmap";
    private static final String playerToggleHideName = "Hide Players";
    private static final String playerToggleShowName = "Show Players";

    public InventoryHandler()
    {
        this.createParkourInventory();
    }

    public static void openInventory(final HumanEntity ent)
    {
        if (inv != null) ent.openInventory(inv);
    }

    public static void setPlayerInventory(Player p)
    {
        Inventory playerInv = p.getInventory();

        setParkourBookMeta();
        setPlayerToggleHideMeta();

        playerInv.clear();
        playerInv.setItem(0, parkourBook);
        playerInv.setItem(8, playerToggleHide);
    }

    private static void setParkourBookMeta()
    {
        setItemMeta(parkourBook, ChatColor.GOLD, parkourBookName);
    }

    private static void setPlayerToggleHideMeta()
    {
        setItemMeta(playerToggleHide, ChatColor.RED, playerToggleHideName);
    }

    private static void setPlayerToggleShowMeta()
    {
        setItemMeta(playerToggleShow, ChatColor.GREEN, playerToggleShowName);
    }

    private static void setItemMeta(ItemStack item, ChatColor color, String displayName)
    {
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(color + displayName);
        item.setItemMeta(meta);
    }

    public static void setPlayerHiddenItem(Player p)
    {
        setPlayerToggleShowMeta();
        p.getInventory().setItem(8, playerToggleShow);
    }

    public static void setPlayerShownItem(Player p)
    {
        setPlayerToggleHideMeta();
        p.getInventory().setItem(8, playerToggleHide);
    }

    public void createParkourInventory()
    {
        FileConfiguration parkourList = YmlHandler.getConfigYml(MapEditor.configName);
        double parkourAmount = parkourList.getKeys(false).size();
        double inventoryRowAmount = Math.ceil(parkourAmount / 9) * 9;
        inv = Bukkit.createInventory(null, (int) Math.max(inventoryRowAmount, 9), "Parkour Roadmap");
        initializeItems(parkourList);
    }

    public void initializeItems(FileConfiguration parkourList)
    {
        for (String parkour : parkourList.getKeys(false)) {
            String parkourName = parkourList.getString(parkour + ".name");
            int parkourDifficulty = parkourList.getInt(parkour + ".difficulty");

            inv.addItem(createGuiItem(
                    "" + parkour,
                    createLoreName("Name", (parkourName != null ? parkourName : "---"), ChatColor.AQUA),
                    createLoreName("Difficulty", "" + parkourDifficulty, ChatColor.GOLD)
            ));
        }
    }

    private String createLoreName(String key, String value, ChatColor color)
    {
        return ChatColor.DARK_GRAY + key + ": " + color + value;
    }

    protected ItemStack createGuiItem(final String name, final String... lore)
    {
        final ItemStack item = new ItemStack(Material.FILLED_MAP, 1);
        final ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);

        return item;
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e)
    {
        final Player p = (Player) e.getWhoClicked();
        final ItemStack clickedItem = e.getCurrentItem();

        e.setCancelled(!p.isOp());
        if (e.getInventory() == inv) e.setCancelled(true);
        if (clickedItem == null || clickedItem.getType() == Material.AIR || clickedItem.getType() != Material.FILLED_MAP)
            return;
        if (clickedItem.getItemMeta() == null) return;
        if (!CooldownHandler.checkCooldown(p)) {
            ChatHandler.sendPlayerMessage(p, ChatColor.DARK_GRAY, "Please wait a moment before doing an action again.");
            return;
        }

        String itemName = clickedItem.getItemMeta().getDisplayName();
        Object teleportObject = YmlHandler.getConfigYml(MapEditor.configName).get(itemName + ".teleport");

        if (teleportObject == null) {
            sendNoLocationMessage(p);
            return;
        }

        if (!(teleportObject instanceof Location)) {
            sendNoLocationMessage(p);
            return;
        }

        p.teleport((Location) teleportObject);
        ChatHandler.sendPlayerMessage(p, ChatColor.BLUE, "You have been teleported to parkour number: " + itemName);
    }

    public void sendNoLocationMessage(Player p)
    {
        ChatHandler.sendPlayerMessage(p, ChatColor.RED, "This parkour doesn't have a valid teleport location yet. Please contact a moderator to fix this.");
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e)
    {
        Player p = (Player) e.getWhoClicked();
        e.setCancelled(!p.isOp());
    }
}
