package com.github.rovey.ultimateparkour.Utilities;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomColorHandler
{
    private static final List<ChatColor> colors;

    static {
        colors = new ArrayList<>(Arrays.asList(ChatColor.values()));
        colors.remove(ChatColor.MAGIC);
    }

    public static ChatColor getRandomColor()
    {
        int randomInt = new Random().nextInt(colors.size());
        return colors.get(randomInt);
    }

    public static String getRandomlyColoredString(String text)
    {
        String[] parts = text.split("");
        StringBuilder buffer = new StringBuilder();

        for (String character : parts) {
            ChatColor randomColour = getRandomColor();
            buffer.append(randomColour).append(character);
        }

        return buffer.toString();
    }
}