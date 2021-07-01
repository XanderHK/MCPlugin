package com.github.rovey.ultimateparkour.Utilities.Server;

import com.github.rovey.ultimateparkour.Chat.ChatHandler;
import com.github.rovey.ultimateparkour.UltimateParkour;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerVisibilityHandler
{
    public static void hidePlayers(Player p)
    {
        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            p.hidePlayer(UltimateParkour.getPlugin(), onlinePlayers);
        }

        InventoryHandler.setPlayerHiddenItem(p);
        ChatHandler.sendPlayerMessage(p, ChatColor.DARK_GREEN, "Hiding all players");
    }

    public static void showPlayers(Player p)
    {
        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            p.showPlayer(UltimateParkour.getPlugin(), onlinePlayers);
        }

        InventoryHandler.setPlayerShownItem(p);
        ChatHandler.sendPlayerMessage(p, ChatColor.GREEN, "Showing all players");
    }
}
