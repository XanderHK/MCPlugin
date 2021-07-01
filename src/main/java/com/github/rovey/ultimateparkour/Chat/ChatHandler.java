package com.github.rovey.ultimateparkour.Chat;

import com.github.rovey.ultimateparkour.UltimateParkour;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatHandler
{
    public static void sendConsoleMessage(ChatColor color, String message)
    {
        UltimateParkour.getPlugin().getServer().getConsoleSender().sendMessage(color + ChatHandler.getPrefix() + message);
    }

    public static void sendPlayerMessage(Player p, ChatColor color, String message)
    {
        p.sendMessage(ChatColor.DARK_AQUA + getPrefix() + color + message);
    }

    private static String getPrefix()
    {
        String prefix = "[Parkour]";
        return prefix + " ";
    }
}
