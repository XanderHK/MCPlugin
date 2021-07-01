package com.github.rovey.ultimateparkour.Utilities.Parkour;

import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

public class CooldownHandler
{
    private static final HashMap<UUID, Long> playerLastTouchedCheckpoint = new HashMap<>();

    public static Long getCooldown(Player p)
    {
        return playerLastTouchedCheckpoint.get(p.getUniqueId());
    }

    public static void setCooldown(Player p, Long timestamp)
    {
        playerLastTouchedCheckpoint.put(p.getUniqueId(), timestamp);
    }

    public static void setCooldownToNow(Player p)
    {
        long timestamp = Instant.now().getEpochSecond();
        setCooldown(p, timestamp);
    }

    public static boolean checkCooldown(Player p)
    {
        long timestamp = Instant.now().getEpochSecond();
        int cooldownTime = 3;
        return (getCooldown(p) + cooldownTime) <= timestamp;
    }
}
