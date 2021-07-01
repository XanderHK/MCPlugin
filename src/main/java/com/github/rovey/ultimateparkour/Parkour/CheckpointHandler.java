package com.github.rovey.ultimateparkour.Parkour;

import com.github.rovey.ultimateparkour.Chat.ChatHandler;
import com.github.rovey.ultimateparkour.Utilities.Helpers;
import com.github.rovey.ultimateparkour.Utilities.Parkour.CooldownHandler;
import com.github.rovey.ultimateparkour.Utilities.Parkour.PlayerLocationHandler;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class CheckpointHandler implements Listener
{
    @EventHandler
    public void onWalkOverCheckpointBlock(PlayerMoveEvent e)
    {
        Player p = e.getPlayer();
        Material blockType = PlayerLocationHandler.getBlockUnderneathPlayer(p, 1);

        if (blockType == Material.GOLD_BLOCK
                && CheckpointLocationHandler.validateHasStartedMap(p, true)
                && CooldownHandler.checkCooldown(p)
        ) {
            if (checkHasThisCheckpoint(p, CheckpointLocationHandler.getCheckpointLocation(p))) return;

            CooldownHandler.setCooldownToNow(e.getPlayer());
            CheckpointLocationHandler.setCheckpointLocation(p, p.getLocation());
            ChatHandler.sendPlayerMessage(p, ChatColor.GOLD, "You have reached a checkpoint!");
        }
    }

    private boolean checkHasThisCheckpoint(Player p, Location checkpointLocation)
    {
        Location playerLoc = Helpers.getCleanLocation(p.getLocation());
        Location checkpointLoc = Helpers.getCleanLocation(checkpointLocation);

        return playerLoc.equals(checkpointLoc);
    }
}
