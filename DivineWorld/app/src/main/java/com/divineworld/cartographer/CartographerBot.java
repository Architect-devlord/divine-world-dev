package com.divineworld.cartographer;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.bukkit.Material;

/**
 * CartographerBot builds a wall of item frames, each displaying a unique map.
 * Used for tribe map libraries and exploration records.
 */
public class CartographerBot {

    /**
     * Builds a wall of maps at the given base location.
     * @param base The bottom-left corner of the wall.
     * @param size The width/height of the wall (max 6).
     */
    public static void buildMapWall(Location base, int size) {
        World world = base.getWorld();
        int width = Math.min(size, 6); // 6x6 max
        int height = width;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Location frameLoc = base.clone().add(x, y, 0);
                Block blockBehind = frameLoc.clone().add(0, 0, -1).getBlock();
                blockBehind.setType(Material.OAK_PLANKS);

                ItemFrame frame = world.spawn(frameLoc, ItemFrame.class);

                // Create a new map for each frame
                MapView mapView = Bukkit.createMap(world);
                mapView.setScale(MapView.Scale.NORMAL);

                ItemStack map = new ItemStack(Material.FILLED_MAP);
                MapMeta meta = (MapMeta) map.getItemMeta();
                meta.setMapView(mapView);
                map.setItemMeta(meta);

                frame.setItem(map);
            }
        }
    }
}