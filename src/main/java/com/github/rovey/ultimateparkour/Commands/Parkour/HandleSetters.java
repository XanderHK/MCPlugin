package com.github.rovey.ultimateparkour.Commands.Parkour;

import com.github.rovey.ultimateparkour.Chat.ChatHandler;
import com.github.rovey.ultimateparkour.Parkour.MapEditor;
import com.github.rovey.ultimateparkour.Utilities.Helpers;
import com.github.rovey.ultimateparkour.Utilities.YmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class HandleSetters
{
    public void checkOption(Player p, String[] args)
    {
        if (!MapEditor.isPlayerEditingMap(p)) {
            ChatHandler.sendPlayerMessage(p, ChatColor.RED, "You need to edit a map first.");
        }

        if (checkArgument(args, 0)) {
            ChatHandler.sendPlayerMessage(p, ChatColor.RED, "You should define a set command.");
            return;
        }

        switch (args[1]) {
            case "name":
                setName(p, args);
                break;
            case "dif":
            case "difficulty":
                setDifficulty(p, args);
                break;
            case "tp":
            case "teleport":
                setLocation(p, args, "teleport");
                break;
            case "start":
                setLocation(p, args, "start");
                break;
            case "end":
                setLocation(p, args, "end");
                break;
            default:
                ChatHandler.sendPlayerMessage(p, ChatColor.RED, "Your set command couldn't be found.");
        }
    }

    private void setName(Player p, String[] args)
    {
        if (checkArgument(args, 2)) {
            ChatHandler.sendPlayerMessage(p, ChatColor.RED, "You should define a map name.");
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 2; i < args.length; i++) builder.append(args[i]).append(" ");
        String itemName = builder.toString();

        List<Object> data = MapEditor.getParkourData(p);
        setYmlData(data, "name", itemName);
        ChatHandler.sendPlayerMessage(p, MapEditor.editorColor, "Set map name to: " + itemName);
    }

    private void setDifficulty(Player p, String[] args)
    {
        if (checkArgument(args, 2)) {
            ChatHandler.sendPlayerMessage(p, ChatColor.RED, "You should define a map difficulty.");
            return;
        }

        List<Object> data = MapEditor.getParkourData(p);
        setYmlData(data, "difficulty", Integer.parseInt(args[2]));
        ChatHandler.sendPlayerMessage(p, MapEditor.editorColor, "Map difficulty set to " + args[2]);
    }

    private void setLocation(Player p, String[] args, String type)
    {
        List<Object> data = MapEditor.getParkourData(p);
        setYmlData(data, type, p.getLocation());
        ChatHandler.sendPlayerMessage(p, MapEditor.editorColor, "Set " + type + " location!");
    }

    private <T> void setYmlData(List<Object> data, String path, T dataToSet)
    {
        FileConfiguration ymlFile = YmlHandler.getConfigYml(MapEditor.configName);
        ymlFile.set(data.get(0) + "." + path, dataToSet);
        YmlHandler.saveConfigYml(ymlFile, MapEditor.configName);
    }

    private boolean checkArgument(String[] args, int index)
    {
        return !Helpers.indexInBound(args, index);
    }
}
