package com.github.rovey.ultimateparkour.Commands;

import com.github.rovey.ultimateparkour.Chat.ChatHandler;
import com.github.rovey.ultimateparkour.Utilities.YmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor
{
    private static final String configName = "locations";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        if (commandSender instanceof Player && commandSender.isOp()) {
            Player p = (Player) commandSender;
            FileConfiguration locations = YmlHandler.getConfigYml(configName);
            FileConfiguration locWithSetSpawn = YmlHandler.locationToYml(p, locations, "spawn");
            YmlHandler.saveConfigYml(locWithSetSpawn, configName);
            ChatHandler.sendPlayerMessage(p, ChatColor.GREEN, "Spawn set to your location.");

            return true;
        }

        return false;
    }
}
