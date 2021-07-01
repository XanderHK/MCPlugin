package com.github.rovey.ultimateparkour.Commands.Parkour;

import com.github.rovey.ultimateparkour.Chat.ChatHandler;
import com.github.rovey.ultimateparkour.Parkour.MapEditor;
import com.github.rovey.ultimateparkour.Utilities.Helpers;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StartEditHandler
{
    public void edit(Player p, String[] args)
    {
        if (!Helpers.indexInBound(args, 1)) {
            ChatHandler.sendPlayerMessage(p, ChatColor.RED, "You should define a map number");
            return;
        }

        if (!Helpers.getMapNumbers().contains(args[1])) {
            ChatHandler.sendPlayerMessage(p, ChatColor.RED, "This map number doesn't exist yet.");
            return;
        }

        if (!Helpers.isInt(args[1])) {
            ChatHandler.sendPlayerMessage(p, ChatColor.RED, "Please enter an integer as map number.");
            return;
        }

        MapEditor.setEditingMap(p, Integer.parseInt(args[1]));
        ChatHandler.sendPlayerMessage(p, MapEditor.editorColor, "You are now editing map " + args[1]);
    }
}
