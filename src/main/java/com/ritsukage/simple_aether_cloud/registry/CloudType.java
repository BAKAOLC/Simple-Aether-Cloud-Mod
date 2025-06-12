package com.ritsukage.simple_aether_cloud.registry;

import com.ritsukage.simple_aether_cloud.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

public enum CloudType {
    YELLOW("yellow_cloud", YellowCloud.class, MapColor.COLOR_YELLOW),
    BLUE("blue_cloud", BlueCloud.class, MapColor.COLOR_LIGHT_BLUE),
    HORIZONTAL_BLUE("horizontal_blue_cloud", HorizontalBlueCloud.class, MapColor.COLOR_LIGHT_BLUE),
    RANDOM_BLUE("blue_cloud_random", RandomBlueCloud.class, MapColor.COLOR_LIGHT_BLUE),
    RED("red_cloud", RedCloud.class, MapColor.COLOR_RED),
    GREEN("green_cloud", GreenCloud.class, MapColor.COLOR_GREEN),
    BLACK("black_cloud", BlackCloudBlock.class, MapColor.COLOR_BLACK),
    TOGGLEABLE_BLACK("black_cloud_toggleable", ToggleableBlackCloudBlock.class, MapColor.COLOR_BLACK),
    INVERTED_BLACK("black_cloud_inverted", InvertedBlackCloudBlock.class, MapColor.COLOR_BLACK),
    TIMED_BLACK("black_cloud_timed", TimedBlackCloudBlock.class, MapColor.COLOR_BLACK);

    private final String name;
    private final Class<? extends Block> blockClass;
    private final MapColor color;

    CloudType(String name, Class<? extends Block> blockClass, MapColor color) {
        this.name = name;
        this.blockClass = blockClass;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Class<? extends Block> getBlockClass() {
        return blockClass;
    }

    public MapColor getColor() {
        return color;
    }
}