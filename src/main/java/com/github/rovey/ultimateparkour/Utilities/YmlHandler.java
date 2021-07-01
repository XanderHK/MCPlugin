package com.github.rovey.ultimateparkour.Utilities;

import com.github.rovey.ultimateparkour.UltimateParkour;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

public class YmlHandler
{

    private static HashMap<String, FileConfiguration> ymlFiles = new HashMap<>();

    public static FileConfiguration getConfigYml(String configName)
    {
        if (ymlFiles.containsKey(configName)) return ymlFiles.get(configName);
        File customYml = YmlHandler.getYmlFile(configName);
        FileConfiguration configYml = YamlConfiguration.loadConfiguration(customYml);
        ymlFiles.put(configName, configYml);
        return configYml;
    }

    public static void saveConfigYml(FileConfiguration configYml, String configName)
    {
        try {
            configYml.save(YmlHandler.getYmlFile(configName));
            ymlFiles.put(configName, configYml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File getYmlFile(String configName)
    {
        File ymlFile = new File(UltimateParkour.getPlugin().getDataFolder(), configName + ".yml");

        if (!ymlFile.exists()) {

            try {
                if (!Files.exists(UltimateParkour.getPlugin().getDataFolder().toPath())) {
                    ymlFile.getParentFile().mkdirs();
                }

                ymlFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ymlFile;
    }

    public static FileConfiguration locationToYml(Player p, FileConfiguration ymlFile, String path)
    {
        Location location = p.getLocation();
        ymlFile.set(path, location);

        return ymlFile;
    }

    public static Location getLocation(Player p, String configName, String path)
    {
        FileConfiguration ymlFile = YmlHandler.getConfigYml(configName);
        Object locationObject = ymlFile.get(path);

        return (Location) locationObject;
    }
}
