package com.github.rovey.ultimateparkour.Commands.Parkour;

import com.github.rovey.ultimateparkour.Chat.ChatHandler;
import com.github.rovey.ultimateparkour.Parkour.CheckpointLocationHandler;
import com.github.rovey.ultimateparkour.Parkour.MapEditor;
import com.github.rovey.ultimateparkour.Parkour.MapLocationHandler;
import com.github.rovey.ultimateparkour.Utilities.Parkour.CooldownHandler;
import com.github.rovey.ultimateparkour.Utilities.YmlHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class RemoveMapHandler
{
    public void removeMap(Player p)
    {
        if (!MapEditor.isPlayerEditingMap(p)) {
            ChatHandler.sendPlayerMessage(p, ChatColor.RED, "You need to edit a map first.");
            return;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (MapLocationHandler.isPlayingMap(player)) {
                this.removePlayerFromMap(p);
            }
        }

        this.removeMapFromYml(p);
    }

    private void removePlayerFromMap(Player p)
    {
        CooldownHandler.setCooldownToNow(p);
        MapLocationHandler.removePlayingMap(p);
        CheckpointLocationHandler.removeCheckpointLocation(p);
        p.teleport(YmlHandler.getLocation(p, "locations", "spawn"));
        ChatHandler.sendPlayerMessage(p, ChatColor.RED, "You have been teleported to spawn since the map you were playing on got removed.");
    }

    private void removeMapFromYml(Player p)
    {
        ChatHandler.sendPlayerMessage(p, ChatColor.RED, "Players currently playing this map are teleported to spawn.");
        FileConfiguration ymlFile = YmlHandler.getConfigYml(MapEditor.configName);
        List<Object> data = MapEditor.getParkourData(p);
        ymlFile.set(data.get(0).toString(), null);
        YmlHandler.saveConfigYml(ymlFile, MapEditor.configName);
        ChatHandler.sendPlayerMessage(p, MapEditor.editorColor, "Map successfully deleted!");
    }
}
