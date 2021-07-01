package com.github.rovey.ultimateparkour.Parkour;

import com.github.rovey.ultimateparkour.Chat.ChatHandler;
import com.github.rovey.ultimateparkour.Utilities.YmlHandler;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CreditHandler
{
    private static final String configName = "player_credit";
    public static final int multiplier = 3;

    public static void addCredit(Player p, Integer amount)
    {
        FileConfiguration ymlFile = YmlHandler.getConfigYml(configName);
        String playerId = p.getUniqueId().toString();
        if (!ymlFile.contains(playerId)) {
            ymlFile.set(playerId, amount);
        } else {
            ymlFile.set(playerId, ymlFile.getInt(playerId) + amount);
        }

        ChatHandler.sendPlayerMessage(
                p, ChatColor.GRAY,
                "You have been rewarded " + ChatColor.GOLD + ChatColor.BOLD + amount + ChatColor.RESET + ChatColor.GRAY + " credits for completing the map."
        );

        ChatHandler.sendPlayerMessage(
                p, ChatColor.GRAY,
                "You now have a total of " + ChatColor.GOLD + ChatColor.BOLD + ymlFile.getInt(playerId) + ChatColor.RESET + ChatColor.GRAY + " credits."
        );

        YmlHandler.saveConfigYml(ymlFile, configName);
    }

    public static int getCreditAmountFromLevel(String level)
    {
        return YmlHandler.getConfigYml(MapEditor.configName).getInt(level + ".difficulty");
    }
}
