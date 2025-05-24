package com.ritsukage.simple_aether_cloud.event;

import com.ritsukage.simple_aether_cloud.block.GreenCloud;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GreenCloudHandler {
    private static final int ATTRACTION_CHECK_INTERVAL = 20;
    private static final Map<ServerLevel, Integer> tickCounters = new ConcurrentHashMap<>();

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (event.getLevel() instanceof ServerLevel level) {
            int tickCounter = tickCounters.computeIfAbsent(level, k -> 0);
            tickCounter++;

            if (tickCounter >= ATTRACTION_CHECK_INTERVAL) {
                tickCounter = 0;
                GreenCloud.processAllClouds(level);
            }

            tickCounters.put(level, tickCounter);
        }
    }
}