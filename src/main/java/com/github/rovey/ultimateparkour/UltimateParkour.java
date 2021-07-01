package com.github.rovey.ultimateparkour;

import com.github.rovey.ultimateparkour.Chat.ChatFormatter;
import com.github.rovey.ultimateparkour.Chat.ChatHandler;
import com.github.rovey.ultimateparkour.Commands.ParkourCommands;
import com.github.rovey.ultimateparkour.Commands.SetSpawnCommand;
import com.github.rovey.ultimateparkour.Commands.SpawnCommand;
import com.github.rovey.ultimateparkour.Parkour.*;
import com.github.rovey.ultimateparkour.Utilities.ParticleSystem;
import com.github.rovey.ultimateparkour.Utilities.Server.CommonEvents;
import com.github.rovey.ultimateparkour.Utilities.Server.InventoryHandler;
import com.github.rovey.ultimateparkour.Utilities.Server.PlayerInteract;
import com.github.rovey.ultimateparkour.Utilities.TabPing;
import com.github.rovey.ultimateparkour.Utilities.YmlAutoSaver;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class UltimateParkour extends JavaPlugin
{
    private static Plugin plugin;

    @Override
    public void onEnable()
    {
        plugin = this;

        registerEvents(this,
                new CheckpointHandler(),
                new InventoryHandler(),
                new PlayerInteract(),
                new FinishHandler(),
                new ChatFormatter(),
                new ResetHandler(),
                new StartHandler(),
                new CommonEvents()
        );

        this.getCommand("setspawn").setExecutor(new SetSpawnCommand());
        this.getCommand("parkour").setExecutor(new ParkourCommands());
        this.getCommand("spawn").setExecutor(new SpawnCommand());

        int saveCheckpointsTimeout = 300 * 20;
        int updatePingTimeout = 10 * 20;
        int spawnParticlesTimeout = 10;
        new YmlAutoSaver().runTaskTimer(plugin, saveCheckpointsTimeout, saveCheckpointsTimeout);
        new TabPing().runTaskTimer(plugin, updatePingTimeout, updatePingTimeout);
        new ParticleSystem().runTaskTimer(plugin, spawnParticlesTimeout, spawnParticlesTimeout);
        ChatHandler.sendConsoleMessage(ChatColor.GREEN, "Plugin has been enabled.");
    }

    @Override
    public void onDisable()
    {
        new YmlAutoSaver().saveCheckpoints();
        ChatHandler.sendConsoleMessage(ChatColor.RED, "Plugin has been disabled.");
        plugin = null;
    }

    public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners)
    {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

    public static Plugin getPlugin()
    {
        return plugin;
    }
}
