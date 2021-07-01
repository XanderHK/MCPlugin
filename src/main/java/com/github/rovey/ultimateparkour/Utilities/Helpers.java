package com.github.rovey.ultimateparkour.Utilities;

import com.github.rovey.ultimateparkour.Chat.ChatHandler;
import com.github.rovey.ultimateparkour.Parkour.MapEditor;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Helpers
{
    public static boolean isInt(String s)
    {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    public static boolean indexInBound(String[] data, int index)
    {
        return data != null && index >= 0 && index < data.length;
    }

    public static Location getCleanLocation(Location l)
    {
        return new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ(), 0, 0);
    }

    public static void getMapNumbersInUse(Player p)
    {
        String mapNumbers = getMapNumbers().toString().replace("[", "").replace("]", "");
        ChatHandler.sendPlayerMessage(p, MapEditor.editorColor, "Map numbers currently in use: " + mapNumbers);
    }

    public static List<String> getMapNumbers()
    {
        FileConfiguration parkourList = YmlHandler.getConfigYml(MapEditor.configName);
        List<String> keys = new ArrayList<>(parkourList.getKeys(false));
        keys.sort(Comparator.comparingInt(Integer::valueOf));
        return keys;
    }

    public static void showHelp(Player p)
    {
        ChatHandler.sendPlayerMessage(p, MapEditor.editorColor, "Available commands: help, edit, stopedit, exit, set name, set start, set end, set tp (or teleport), set dif (or difficulty), create, remove, showmaps");
    }

    public static int getPing(Player player)
    {
        try {
            Method handle = player.getClass().getMethod("getHandle");
            Object nmsHandle = handle.invoke(player);
            Field pingField = nmsHandle.getClass().getField("ping");
            return pingField.getInt(nmsHandle);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
            ChatHandler.sendConsoleMessage(ChatColor.RED, "Exception while trying to get player ping. " + e.getMessage());
        }

        return -1;
    }
}
