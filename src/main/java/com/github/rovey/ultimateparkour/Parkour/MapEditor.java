package com.github.rovey.ultimateparkour.Parkour;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MapEditor
{
    private static final HashMap<UUID, Integer> editingMap = new HashMap<>();
    public static final String configName = "parkours";
    public static final ChatColor editorColor = ChatColor.AQUA;

    public static Integer getEditingMap(Player p)
    {
        return editingMap.get(p.getUniqueId());
    }

    public static void setEditingMap(Player p, Integer map)
    {
        editingMap.put(p.getUniqueId(), map);
    }

    public static void removeEditingMap(Player p)
    {
        editingMap.remove(p.getUniqueId());
    }

    public static boolean isPlayerEditingMap(Player p)
    {
        return editingMap.containsKey(p.getUniqueId());
    }

    public static List<Object> getParkourData(Player p)
    {
        Integer parkourNr = getEditingMap(p);
        Location playerLocation = p.getLocation();

        return Arrays.asList(parkourNr, playerLocation);
    }
}
