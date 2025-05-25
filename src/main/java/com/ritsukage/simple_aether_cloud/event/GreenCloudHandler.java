package com.ritsukage.simple_aether_cloud.event;

import com.ritsukage.simple_aether_cloud.block.GreenCloudBlockEntity;
import com.ritsukage.simple_aether_cloud.config.CloudConfig;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

public class GreenCloudHandler {
    private static int tickCounter = 0;

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        Level level = event.getLevel();
        if (level.isClientSide())
            return;

        tickCounter++;
        if (tickCounter >= CloudConfig.GREEN_CLOUD_MOVE_INTERVAL.get()) {
            tickCounter = 0;
            GreenCloudBlockEntity.processMovements(level);
        }
    }
}