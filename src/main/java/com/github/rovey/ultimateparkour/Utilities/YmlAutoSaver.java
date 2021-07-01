package com.github.rovey.ultimateparkour.Utilities;

import com.github.rovey.ultimateparkour.Chat.ChatHandler;
import com.github.rovey.ultimateparkour.Parkour.CheckpointLocationHandler;
import com.github.rovey.ultimateparkour.Parkour.MapLocationHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class YmlAutoSaver extends BukkitRunnable
{
    public void saveCheckpoints()
    {
        String configName = "player_checkpoints";
        HashMap<UUID, Location> allCheckpoints = CheckpointLocationHandler.checkpointLocation;
        FileConfiguration ymlFile = YmlHandler.getConfigYml(configName);

        for (Map.Entry<UUID, Location> entry : allCheckpoints.entrySet()) {
            Player p = Bukkit.getPlayer(entry.getKey());
            if (p == null) continue;
            Integer parkourNr = MapLocationHandler.getPlayingMap(p);
            ymlFile.set(entry.getKey().toString() + ".location", entry.getValue());
            ymlFile.set(entry.getKey().toString() + ".parkour", parkourNr);
        }

        YmlHandler.saveConfigYml(ymlFile, configName);
        ChatHandler.sendConsoleMessage(ChatColor.GREEN, "Saved player checkpoints.");
    }

    @Override
    public void run()
    {
        saveCheckpoints();
    }
}
