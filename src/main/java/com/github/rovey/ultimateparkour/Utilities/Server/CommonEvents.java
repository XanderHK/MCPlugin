package com.github.rovey.ultimateparkour.Utilities.Server;

import com.github.rovey.ultimateparkour.Chat.ChatHandler;
import com.github.rovey.ultimateparkour.Parkour.CheckpointLocationHandler;
import com.github.rovey.ultimateparkour.Parkour.MapLocationHandler;
import com.github.rovey.ultimateparkour.UltimateParkour;
import com.github.rovey.ultimateparkour.Utilities.Parkour.CooldownHandler;
import com.github.rovey.ultimateparkour.Utilities.YmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.ArrayList;

public class CommonEvents implements Listener
{
    public static ArrayList<Player> hasPreferredHidden = new ArrayList<Player>();

    @EventHandler
    public void teleportToLastCheckpoint(PlayerSpawnLocationEvent e)
    {
        Player p = e.getPlayer();
        FileConfiguration ymlFile = YmlHandler.getConfigYml(CheckpointLocationHandler.configName);
        Location lastCheckpoint = (Location) ymlFile.get(p.getUniqueId() + ".location");
        Integer mapNr = (Integer) ymlFile.get(p.getUniqueId() + ".parkour");

        if(lastCheckpoint == null || mapNr == null) return;

        MapLocationHandler.setPlayingMap(p, mapNr.toString());
        CheckpointLocationHandler.setCheckpointLocation(p, lastCheckpoint);
        e.setSpawnLocation(lastCheckpoint);
        ChatHandler.sendPlayerMessage(p, ChatColor.GREEN, "You've been teleported to your latest checkpoint.");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();

        CooldownHandler.setCooldownToNow(p);
        InventoryHandler.setPlayerInventory(p);

        if (!p.hasPlayedBefore()) {
            p.teleport(YmlHandler.getLocation(p, "locations", "spawn"));
        }

        if (!p.isOp() && p.getGameMode() != GameMode.ADVENTURE) {
            p.setGameMode(GameMode.ADVENTURE);
        }

        for (Player pph : hasPreferredHidden) pph.hidePlayer(UltimateParkour.getPlugin(), p);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e)
    {
        if (e.toWeatherState()) {
            e.setCancelled(true);
            ChatHandler.sendConsoleMessage(ChatColor.GREEN, "Stopped weather change.");
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)
    {
        if (!e.getPlayer().isOp()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e)
    {
        if (!e.getPlayer().isOp()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void stopVineGrowth(BlockGrowEvent e)
    {
        if (e.getNewState().getType() != Material.VINE) return;
        e.setCancelled(true);
    }

    @EventHandler
    public static void onItemDrop(PlayerDropItemEvent e)
    {
        if (!e.getPlayer().isOp()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e)
    {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            if (!p.isOp()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDispenserFireFirework(BlockDispenseEvent e)
    {
        final Block block = e.getBlock();

        if (block.getType() == Material.DISPENSER && e.getItem().getType() == Material.FIREWORK_ROCKET) {
            InventoryHolder dispenser = (InventoryHolder) block.getState();
            ItemStack rocket = e.getItem();
            ItemMeta rocketMeta = e.getItem().getItemMeta();
            rocket.setItemMeta(rocketMeta);
            dispenser.getInventory().addItem(new ItemStack(rocket));
        }
    }

    @EventHandler
    public void onHungerDecrement(FoodLevelChangeEvent e)
    {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            if (p.getFoodLevel() < 20) {
                p.setFoodLevel(20);
            }

            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHealthDecrement(EntityDamageEvent e)
    {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            if (p.getHealth() < 20) {
                p.setHealth(20);
            }

            e.setCancelled(true);
        }
    }
}
