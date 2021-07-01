package com.github.rovey.ultimateparkour.Chat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatFormatter implements Listener
{
    @EventHandler
    public void chatFormat(AsyncPlayerChatEvent e)
    {
        Player p = e.getPlayer();

        if (p.isOp()) {
            e.setFormat(ChatColor.DARK_GRAY + "["
                    + ChatColor.AQUA + "MOD"
                    + ChatColor.DARK_GRAY + "] "
                    + ChatColor.WHITE + p.getDisplayName()
                    + ChatColor.YELLOW + " > "
                    + ChatColor.GRAY + e.getMessage()
            );
        } else {
            e.setFormat(ChatColor.DARK_GRAY + "["
                    + ChatColor.DARK_GREEN + "MEMBER"
                    + ChatColor.DARK_GRAY + "] "
                    + ChatColor.WHITE + p.getDisplayName()
                    + ChatColor.YELLOW + " > "
                    + ChatColor.GRAY + e.getMessage()
            );
        }
    }

}
