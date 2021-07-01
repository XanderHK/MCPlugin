package com.github.rovey.ultimateparkour.Parkour;

import com.github.rovey.ultimateparkour.Chat.ChatHandler;
import com.github.rovey.ultimateparkour.Utilities.Parkour.CooldownHandler;
import com.github.rovey.ultimateparkour.Utilities.Parkour.PlayerLocationHandler;
import com.github.rovey.ultimateparkour.Utilities.YmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ResetHandler implements Listener
{
    @EventHandler
    public void playerResetToCheckpoint(PlayerMoveEvent e)
    {
        Player p = e.getPlayer();
        Material blockType = PlayerLocationHandler.getBlockUnderneathPlayer(p, 0);

        if (blockType == Material.WATER) {

            if (!CheckpointLocationHandler.validateHasStartedMap(p, false)) {

                if (!p.isOp()) {
                    p.teleport(YmlHandler.getLocation(p, "locations", "spawn"));
                }

                return;
            }

            CooldownHandler.setCooldownToNow(p);
            p.teleport(CheckpointLocationHandler.getCheckpointLocation(p));
            ChatHandler.sendPlayerMessage(p, ChatColor.GOLD, "You have been returned to your last checkpoint.");
        }
    }
}
