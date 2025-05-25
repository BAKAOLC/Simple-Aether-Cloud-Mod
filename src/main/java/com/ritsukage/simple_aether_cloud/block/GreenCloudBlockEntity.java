package com.ritsukage.simple_aether_cloud.block;

import com.ritsukage.simple_aether_cloud.config.CloudConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.phys.AABB;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GreenCloudBlockEntity extends BlockEntity {
    private static final Map<ResourceKey<Level>, Set<GreenCloudBlockEntity>> CLOUD_REGISTRY = new ConcurrentHashMap<>();

    public GreenCloudBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GREEN_CLOUD.get(), pos, state);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (level != null && !level.isClientSide) {
            CLOUD_REGISTRY.computeIfAbsent(level.dimension(), k -> ConcurrentHashMap.newKeySet()).add(this);
        }
    }

    @Override
    public void setRemoved() {
        if (level != null && !level.isClientSide) {
            CLOUD_REGISTRY.getOrDefault(level.dimension(), Collections.emptySet()).remove(this);
        }
        super.setRemoved();
    }

    private static Set<GreenCloudBlockEntity> getClouds(Level level) {
        return CLOUD_REGISTRY.getOrDefault(level.dimension(), Collections.emptySet());
    }

    public void tick() {
    }

    private static boolean isInAnyCloudRange(Animal animal, Level level) {
        double minDistance = CloudConfig.GREEN_CLOUD_MIN_DISTANCE.get();
        return getClouds(level).stream().anyMatch(cloud -> animal.distanceToSqr(
                cloud.getBlockPos().getX() + 0.5,
                cloud.getBlockPos().getY() + 0.5,
                cloud.getBlockPos().getZ() + 0.5) <= minDistance * minDistance);
    }

    public static void processMovements(Level level) {
        if (level.isClientSide) {
            return;
        }

        Set<GreenCloudBlockEntity> clouds = getClouds(level);
        if (clouds.isEmpty()) {
            return;
        }

        Set<Animal> animalsToProcess = new HashSet<>();
        double maxRange = CloudConfig.GREEN_CLOUD_ATTRACTION_RANGE.get();

        clouds.forEach(cloud -> {
            AABB searchBox = new AABB(
                    cloud.getBlockPos().getX() - maxRange,
                    cloud.getBlockPos().getY() - maxRange,
                    cloud.getBlockPos().getZ() - maxRange,
                    cloud.getBlockPos().getX() + maxRange + 1,
                    cloud.getBlockPos().getY() + maxRange + 1,
                    cloud.getBlockPos().getZ() + maxRange + 1);

            level.getEntitiesOfClass(Animal.class, searchBox, animal -> true)
                    .forEach(animalsToProcess::add);
        });

        for (Animal animal : animalsToProcess) {
            if (!animal.isAlive()) {
                continue;
            }

            if (isInAnyCloudRange(animal, level)) {
                continue;
            }

            GreenCloudBlockEntity nearestCloud = null;
            double minDistance = Double.MAX_VALUE;
            double attractionRange = CloudConfig.GREEN_CLOUD_ATTRACTION_RANGE.get();

            for (GreenCloudBlockEntity cloud : clouds) {
                double distanceSqr = animal.distanceToSqr(
                        cloud.getBlockPos().getX() + 0.5,
                        cloud.getBlockPos().getY() + 0.5,
                        cloud.getBlockPos().getZ() + 0.5);

                if (distanceSqr <= attractionRange * attractionRange &&
                        distanceSqr < minDistance) {
                    minDistance = distanceSqr;
                    nearestCloud = cloud;
                }
            }

            if (nearestCloud != null) {
                animal.getNavigation().moveTo(
                        nearestCloud.getBlockPos().getX() + 0.5,
                        nearestCloud.getBlockPos().getY(),
                        nearestCloud.getBlockPos().getZ() + 0.5,
                        CloudConfig.GREEN_CLOUD_ATTRACTION_SPEED.get());
            }
        }
    }
}