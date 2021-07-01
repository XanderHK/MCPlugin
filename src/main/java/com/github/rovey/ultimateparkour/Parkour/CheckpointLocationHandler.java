package com.github.rovey.ultimateparkour.Parkour;

import com.github.rovey.ultimateparkour.Chat.ChatHandler;
import com.github.rovey.ultimateparkour.Utilities.Parkour.CooldownHandler;
import com.github.rovey.ultimateparkour.Utilities.YmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CheckpointLocationHandler
{
    public static final String configName = "player_checkpoints";

    public static HashMap<UUID, Location> checkpointLocation = new HashMap<>();

    public static Location getCheckpointLocation(Player p)
    {
        return checkpointLocation.get(p.getUniqueId());
    }

    public static void setCheckpointLocation(Player p, Location loc)
    {
        checkpointLocation.put(p.getUniqueId(), loc);
    }

    public static void removeCheckpointLocation(Player p)
    {
        FileConfiguration ymlFile = YmlHandler.getConfigYml(configName);

        if(ymlFile.get(p.getUniqueId() + ".location") == null) return;

        ymlFile.set(p.getUniqueId().toString(), null);
        YmlHandler.saveConfigYml(ymlFile, configName);
        checkpointLocation.remove(p.getUniqueId());
    }

    public static boolean validateHasStartedMap(Player p, boolean sendMessage)
    {
        if (!checkpointLocation.containsKey(p.getUniqueId())) {

            if (sendMessage && CooldownHandler.checkCooldown(p)) {
                CooldownHandler.setCooldownToNow(p);
                ChatHandler.sendPlayerMessage(p, ChatColor.DARK_GRAY, "You should start a map first.");
            }

            return false;
        }

        return true;
    }
}
