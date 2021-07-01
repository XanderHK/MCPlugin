package com.github.rovey.ultimateparkour.Commands;

import com.github.rovey.ultimateparkour.Chat.ChatHandler;
import com.github.rovey.ultimateparkour.Commands.Parkour.*;
import com.github.rovey.ultimateparkour.Parkour.CheckpointLocationHandler;
import com.github.rovey.ultimateparkour.Utilities.Helpers;
import com.github.rovey.ultimateparkour.Utilities.Server.InventoryHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ParkourCommands implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;

            if (!Helpers.indexInBound(args, 0)) {
                ChatHandler.sendPlayerMessage(p, ChatColor.RED, "You should define a secondary argument");
                return true;
            }

            if (args[0].equalsIgnoreCase("exit") && CheckpointLocationHandler.validateHasStartedMap(p, true)) {
                new ExitHandler().exit(p);
                return true;
            }

            if (p.isOp()) {
                switch (args[0].toLowerCase()) {
                    case "edit":
                        new StartEditHandler().edit(p, args);
                        break;
                    case "stopedit":
                        new StopEditHandler().stopEdit(p);
                        break;
                    case "create":
                        new CreateMapHandler().createNewMap(p);
                        break;
                    case "remove":
                        new RemoveMapHandler().removeMap(p);
                        break;
                    case "set":
                        new HandleSetters().checkOption(p, args);
                        break;
                    case "showmaps":
                        Helpers.getMapNumbersInUse(p);
                        break;
                    case "help":
                        Helpers.showHelp(p);
                        break;
                    default:
                        break;
                }

                new InventoryHandler().createParkourInventory();
            }

            return true;
        }

        return false;
    }
}
