package com.github.rovey.ultimateparkour.Commands.Parkour;

import com.github.rovey.ultimateparkour.Chat.ChatHandler;
import com.github.rovey.ultimateparkour.Parkour.MapEditor;
import com.github.rovey.ultimateparkour.Utilities.Helpers;
import org.bukkit.entity.Player;

public class CreateMapHandler
{
    public void createNewMap(Player p)
    {
        int newMapKey = this.getNewMapKey();

        MapEditor.setEditingMap(p, newMapKey);
        ChatHandler.sendPlayerMessage(p, MapEditor.editorColor, "Map created, you are now editing " + newMapKey);
    }

    private Integer getNewMapKey()
    {
        int newMapKey = Helpers.getMapNumbers()
                .stream()
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);

        return ++newMapKey;
    }
}
