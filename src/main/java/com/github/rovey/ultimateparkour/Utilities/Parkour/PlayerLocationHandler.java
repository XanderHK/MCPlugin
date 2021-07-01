package com.github.rovey.ultimateparkour.Utilities.Parkour;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerLocationHandler
{
    public static HashMap<String, Integer> getBlockPosition(Player p, int yOffset)
    {
        Location pLoc = p.getLocation();
        HashMap<String, Integer> locMap = new HashMap<String, Integer>();
        locMap.put("x", pLoc.getBlockX());
        locMap.put("y", pLoc.getBlockY() - yOffset);
        locMap.put("z", pLoc.getBlockZ());

        return locMap;
    }


    public static Material getBlockUnderneathPlayer(Player p, int yOffset)
    {
        HashMap<String, Integer> blockPosition = getBlockPosition(p, yOffset);
        int x = blockPosition.get("x");
        int y = blockPosition.get("y");
        int z = blockPosition.get("z");

        return p.getWorld().getBlockAt(x, y, z).getType();
    }
}