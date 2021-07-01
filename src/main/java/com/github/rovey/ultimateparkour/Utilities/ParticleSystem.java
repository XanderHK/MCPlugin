package com.github.rovey.ultimateparkour.Utilities;

import com.github.rovey.ultimateparkour.Parkour.MapEditor;
import com.github.rovey.ultimateparkour.UltimateParkour;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleSystem extends BukkitRunnable
{
    private float angle = 0f;

    @Override
    public void run()
    {
        generateFinishParticles();
    }

    public void generateFinishParticles()
    {
        FileConfiguration config = YmlHandler.getConfigYml(MapEditor.configName);

        for (String key : config.getKeys(false)) {
            Location start = config.getLocation(key + ".start");
            Location finish = config.getLocation(key + ".end");

            if (start != null) spawnParticle(start, Particle.DRIPPING_OBSIDIAN_TEAR);
            if (finish != null) spawnParticle(finish, Particle.VILLAGER_HAPPY);
        }

        angle += 0.25;
    }

    private void spawnParticle(Location loc, Particle particle)
    {
        float radius = .35f;
        float offset = .5f;
        double x = (radius * Math.sin(angle));
        double z = (radius * Math.cos(angle));

        World world = loc.getWorld();
        assert world != null;

        World getWorld = UltimateParkour.getPlugin().getServer().getWorld(world.getName());
        assert getWorld != null;

        getWorld.spawnParticle(
                particle,
                loc.getBlockX() + offset + x,
                loc.getBlockY() + 0.25,
                loc.getBlockZ() + offset + z,
                1
        );
    }
}
