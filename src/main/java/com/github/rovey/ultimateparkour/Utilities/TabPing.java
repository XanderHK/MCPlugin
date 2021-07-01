package com.github.rovey.ultimateparkour.Utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TabPing extends BukkitRunnable
{
    @Override
    public void run()
    {
        updateTabPing();
    }

    public void updateTabPing()
    {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p == null) return;
            p.setPlayerListName(
                    ChatColor.DARK_GRAY + "["
                            + ChatColor.GREEN + Helpers.getPing(p)
                            + ChatColor.GOLD + "ms"
                            + ChatColor.DARK_GRAY + "] "
                            + ChatColor.WHITE + p.getDisplayName()
            );
        }
    }
}
