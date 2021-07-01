package com.github.rovey.ultimateparkour.Commands;

import com.github.rovey.ultimateparkour.Chat.ChatHandler;
import com.github.rovey.ultimateparkour.Utilities.YmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor
{
    private static final String configName = "locations";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            FileConfiguration locations = YmlHandler.getConfigYml(configName);

            if (locations.isSet("spawn")) {
                p.teleport(YmlHandler.getLocation(p, "locations","spawn"));
                ChatHandler.sendPlayerMessage(p, ChatColor.YELLOW, "Sending you to spawn.");
            } else {
                ChatHandler.sendPlayerMessage(p, ChatColor.RED, "Spawn isn't defined (yet).");
            }

            return true;
        }

        return false;
    }
}
