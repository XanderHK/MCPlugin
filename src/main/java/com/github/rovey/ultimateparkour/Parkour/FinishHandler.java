package com.github.rovey.ultimateparkour.Parkour;

import com.github.rovey.ultimateparkour.Chat.ChatHandler;
import com.github.rovey.ultimateparkour.Utilities.Parkour.CooldownHandler;
import com.github.rovey.ultimateparkour.Utilities.RandomColorHandler;
import com.github.rovey.ultimateparkour.Utilities.YmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.meta.FireworkMeta;

public class FinishHandler implements Listener
{
    @EventHandler
    public void playerFinishedLevel(PlayerMoveEvent e)
    {
        Player p = e.getPlayer();
        String map = MapLocationHandler.getMapIfWalkingOnParkourBlock(p, "end");

        if (map == null) return;

        if (CheckpointLocationHandler.validateHasStartedMap(p, true) && CooldownHandler.checkCooldown(p)) {
            if (!MapLocationHandler.isPlayingMapNumber(p, map)) {
                CooldownHandler.setCooldownToNow(p);
                ChatHandler.sendPlayerMessage(p, ChatColor.YELLOW, "Cheeky cheeky! You can't finish this map as you are playing another.");
                return;
            }

            int amount = CreditHandler.getCreditAmountFromLevel(map) * CreditHandler.multiplier;
            String coloredText = RandomColorHandler.getRandomlyColoredString("Congratulations!") + ChatColor.GREEN + " You've finished this map.";

            CooldownHandler.setCooldownToNow(p);
            MapLocationHandler.removePlayingMap(p);
            CheckpointLocationHandler.removeCheckpointLocation(p);
            ChatHandler.sendPlayerMessage(p, ChatColor.GREEN, coloredText);
            CreditHandler.addCredit(p, amount);

            p.teleport(YmlHandler.getLocation(p, "locations", "spawn"));
            this.shootFireWork(p);
        }
    }

    public void shootFireWork(Player p)
    {
        Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        FireworkEffect effect = FireworkEffect.builder()
                .flicker(true)
                .withColor(Color.WHITE)
                .withFade(Color.BLUE)
                .with(FireworkEffect.Type.BALL_LARGE)
                .trail(true)
                .build();
        fwm.addEffect(effect);
        fwm.setPower(1);
        fw.setFireworkMeta(fwm);
    }
}
