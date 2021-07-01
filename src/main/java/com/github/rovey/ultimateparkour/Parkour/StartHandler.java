package com.github.rovey.ultimateparkour.Parkour;

import com.github.rovey.ultimateparkour.Chat.ChatHandler;
import com.github.rovey.ultimateparkour.Utilities.Parkour.CooldownHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class StartHandler implements Listener
{
    @EventHandler
    public void playerStartedLevel(PlayerMoveEvent e)
    {
        Player p = e.getPlayer();

        if (MapLocationHandler.isPlayingMap(p)) return;

        String map = MapLocationHandler.getMapIfWalkingOnParkourBlock(p, "start");

        if (map == null) return;

        if (CooldownHandler.checkCooldown(p)) {
            CooldownHandler.setCooldownToNow(p);
            MapLocationHandler.setPlayingMap(p, map);
            CheckpointLocationHandler.setCheckpointLocation(p, p.getLocation());
            ChatHandler.sendPlayerMessage(p, ChatColor.GREEN, "You have started this map. Good luck & have fun!");
        }
    }
}
