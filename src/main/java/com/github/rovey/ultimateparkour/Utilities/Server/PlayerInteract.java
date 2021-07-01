package com.github.rovey.ultimateparkour.Utilities.Server;

import com.github.rovey.ultimateparkour.Chat.ChatHandler;
import com.github.rovey.ultimateparkour.Utilities.Parkour.CooldownHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener
{
    @EventHandler
    public void onPlayerBlockInteraction(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();
        Action action = e.getAction();

        this.handleItemEvents(e, p, action);
        this.handleBlockEvents(e, p);
    }

    private void handleItemEvents(PlayerInteractEvent e, Player p, Action action)
    {
        if (this.playerClickedToggleItem(e, Material.BOOK)) {
            InventoryHandler.openInventory(p);
        }

        if (this.playerClickedToggleItem(e, Material.GLOWSTONE_DUST) && CooldownHandler.checkCooldown(p)) {
            CooldownHandler.setCooldownToNow(p);
            PlayerVisibilityHandler.hidePlayers(p);
            CommonEvents.hasPreferredHidden.add(p);
        }

        if (this.playerClickedToggleItem(e, Material.REDSTONE) && CooldownHandler.checkCooldown(p)) {
            CooldownHandler.setCooldownToNow(p);
            PlayerVisibilityHandler.showPlayers(p);
            CommonEvents.hasPreferredHidden.remove(p);
        }
    }

    private void handleBlockEvents(PlayerInteractEvent e, Player p)
    {
        if (p.isOp()) return;

        Block block = e.getClickedBlock();

        if (block == null) return;

        if (block.getType() == Material.DISPENSER) {
            e.setCancelled(true);

            if (CooldownHandler.checkCooldown(p)) {
                CooldownHandler.setCooldownToNow(p);
                ChatHandler.sendPlayerMessage(e.getPlayer(), ChatColor.DARK_GRAY, "You are not permitted to interact with this block.");
            }
        }
    }

    private boolean playerClickedToggleItem(PlayerInteractEvent e, Material material)
    {
        if (!e.hasItem() || e.getItem() == null) return false;
        return e.getItem().getType() == material;
    }
}