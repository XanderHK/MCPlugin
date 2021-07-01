package com.github.rovey.ultimateparkour.Commands.Parkour;

import com.github.rovey.ultimateparkour.Chat.ChatHandler;
import com.github.rovey.ultimateparkour.Parkour.CheckpointLocationHandler;
import com.github.rovey.ultimateparkour.Parkour.MapLocationHandler;
import com.github.rovey.ultimateparkour.Utilities.Parkour.CooldownHandler;
import com.github.rovey.ultimateparkour.Utilities.YmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ExitHandler
{
    public void exit(Player p)
    {
        if (!p.isOp()) {
            p.teleport(YmlHandler.getLocation(p, "locations", "spawn"));
        }

        CooldownHandler.setCooldownToNow(p);
        CheckpointLocationHandler.removeCheckpointLocation(p);
        MapLocationHandler.removePlayingMap(p);
        ChatHandler.sendPlayerMessage(p, ChatColor.GREEN, "You have exited the map.");
    }
}
