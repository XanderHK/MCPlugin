package com.github.rovey.ultimateparkour.Parkour;

import com.github.rovey.ultimateparkour.Utilities.Helpers;
import com.github.rovey.ultimateparkour.Utilities.YmlHandler;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class MapLocationHandler
{
    public static HashMap<UUID, Integer> playingMap = new HashMap<>();

    public static Integer getPlayingMap(Player p)
    {
        return playingMap.get(p.getUniqueId());
    }

    public static void setPlayingMap(Player p, String map)
    {
        playingMap.put(p.getUniqueId(), Integer.parseInt(map));
    }

    public static void removePlayingMap(Player p)
    {
        playingMap.remove(p.getUniqueId());
    }

    public static boolean isPlayingMap(Player p)
    {
        return playingMap.containsKey(p.getUniqueId());
    }

    public static boolean isPlayingMapNumber(Player p, String map)
    {
        if (!playingMap.containsKey(p.getUniqueId())) return false;
        Integer playsMap = playingMap.get(p.getUniqueId());
        return playsMap == Integer.parseInt(map);
    }

    public static String getMapIfWalkingOnParkourBlock(Player p, String type)
    {
        FileConfiguration ymlFile = YmlHandler.getConfigYml(MapEditor.configName);

        for (String parkour : ymlFile.getKeys(false)) {
            Object locationObject = ymlFile.get(parkour + "." + type);

            if (!(locationObject instanceof Location)) continue;
            if (!Helpers.getCleanLocation((Location) locationObject)
                    .equals(Helpers.getCleanLocation(p.getLocation()))) continue;

            return parkour;
        }

        return null;
    }
}
