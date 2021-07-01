package com.github.rovey.ultimateparkour.Commands.Parkour;

import com.github.rovey.ultimateparkour.Chat.ChatHandler;
import com.github.rovey.ultimateparkour.Parkour.MapEditor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StopEditHandler
{
    public void stopEdit(Player p)
    {
        if (!MapEditor.isPlayerEditingMap(p)) {
            ChatHandler.sendPlayerMessage(p, ChatColor.RED, "You need to edit a map first.");
            return;
        }

        MapEditor.removeEditingMap(p);
        ChatHandler.sendPlayerMessage(p, MapEditor.editorColor, "Stopped editing map.");
    }
}
