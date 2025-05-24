package com.ritsukage.simple_aether_cloud.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import net.minecraft.resources.ResourceKey;

public class GreenCloud extends YellowCloud {
    protected static final VoxelShape COLLISION_SHAPE = Shapes.empty();
    private static final int Multiplier = 2;
    private static final int ATTRACTION_RANGE = 16;
    private static final double MIN_DISTANCE = 3.0;
    private static final double ATTRACTION_SPEED = 0.8;
    private static final int BREED_INTERVAL = 200;

    private static final Set<BlockPos> CLOUD_POSITIONS = new HashSet<>();
    private static final Map<UUID, CloudTarget> ANIMAL_TARGETS = new HashMap<>();

    private static class CloudTarget {
        final BlockPos pos;
        final ResourceKey<Level> dimension;

        CloudTarget(BlockPos pos, ResourceKey<Level> dimension) {
            this.pos = pos;
            this.dimension = dimension;
        }
    }

    public GreenCloud(Properties properties) {
        super(properties);
    }

    public static void onChunkLoad(ServerLevel level, ChunkPos chunkPos) {
        for (int x = 0; x < 16; x++) {
            for (int y = level.getMinBuildHeight(); y < level.getMaxBuildHeight(); y++) {
                for (int z = 0; z < 16; z++) {
                    BlockPos pos = new BlockPos(chunkPos.getMinBlockX() + x, y, chunkPos.getMinBlockZ() + z);
                    if (level.getBlockState(pos).getBlock() instanceof GreenCloud) {
                        CLOUD_POSITIONS.add(pos);
                    }
                }
            }
        }
    }

    public static void onChunkUnload(ServerLevel level, ChunkPos chunkPos) {
        CLOUD_POSITIONS.removeIf(pos -> pos.getX() >> 4 == chunkPos.x && pos.getZ() >> 4 == chunkPos.z);
    }

    private static boolean isInAnyCloudRange(Animal animal, Level level) {
        int range = (int) ATTRACTION_RANGE;
        BlockPos animalPos = animal.blockPosition();
        BlockPos minPos = animalPos.offset(-range, -range, -range);
        BlockPos maxPos = animalPos.offset(range, range, range);

        for (BlockPos pos : BlockPos.betweenClosed(minPos, maxPos)) {
            if (level.getBlockState(pos).getBlock() instanceof GreenCloud) {
                if (animal.distanceToSqr(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5) <= MIN_DISTANCE
                        * MIN_DISTANCE) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);

        boolean updated = false;
        if (entity instanceof AgeableMob ageable) {
            if (ageable.getAge() < 0) {
                int newAge = ageable.getAge() + Multiplier;
                ageable.setAge(Math.min(newAge, 0));
                updated = true;
            } else if (ageable.getAge() > 0) {
                int newAge = ageable.getAge() - Multiplier;
                ageable.setAge(Math.max(newAge, 0));
                updated = true;
            }

            if (entity instanceof Animal animal && animal.canFallInLove() && !animal.isInLove()
                    && level.getGameTime() % BREED_INTERVAL == 0) {
                animal.setInLove(null);
                updated = true;
            }
        }

        if (updated) {
            return;
        }

        int amount = level.getRandom().nextInt(10, 20);
        for (int i = 0; i < amount; i++) {
            double xOffset = pos.getX() + level.getRandom().nextDouble();
            double yOffset = pos.getY() + level.getRandom().nextDouble();
            double zOffset = pos.getZ() + level.getRandom().nextDouble();
            level.addParticle(ParticleTypes.HAPPY_VILLAGER, xOffset, yOffset, zOffset, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (!level.isClientSide) {
            CLOUD_POSITIONS.add(pos);
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        if (!level.isClientSide) {
            CLOUD_POSITIONS.remove(pos);
            ANIMAL_TARGETS.entrySet().removeIf(
                    entry -> entry.getValue().pos.equals(pos) && entry.getValue().dimension.equals(level.dimension()));
        }
    }

    public static void processAllClouds(ServerLevel level) {
        ANIMAL_TARGETS.entrySet().removeIf(entry -> {
            Entity entity = level.getEntity(entry.getKey());
            if (entity == null || !(entity instanceof Animal) || !entity.isAlive()) {
                return true;
            }

            CloudTarget target = entry.getValue();
            if (!CLOUD_POSITIONS.contains(target.pos) || !target.dimension.equals(level.dimension())) {
                return true;
            }
            return false;
        });

        Set<Animal> animalsToProcess = new HashSet<>();
        for (BlockPos cloudPos : CLOUD_POSITIONS) {
            level.getEntitiesOfClass(Animal.class,
                    new net.minecraft.world.phys.AABB(cloudPos).inflate(ATTRACTION_RANGE),
                    animal -> true).forEach(animalsToProcess::add);
        }

        Set<UUID> processedAnimals = animalsToProcess.stream()
                .map(Entity::getUUID)
                .collect(Collectors.toSet());
        ANIMAL_TARGETS.keySet().removeIf(uuid -> !processedAnimals.contains(uuid));

        for (Animal animal : animalsToProcess) {
            if (isInAnyCloudRange(animal, level)) {
                ANIMAL_TARGETS.remove(animal.getUUID());
                continue;
            }

            BlockPos nearestCloud = null;
            double minDistance = Double.MAX_VALUE;

            for (BlockPos cloudPos : CLOUD_POSITIONS) {
                double distanceSqr = animal.distanceToSqr(cloudPos.getX() + 0.5, cloudPos.getY(),
                        cloudPos.getZ() + 0.5);
                if (distanceSqr <= ATTRACTION_RANGE * ATTRACTION_RANGE && distanceSqr < minDistance) {
                    minDistance = distanceSqr;
                    nearestCloud = cloudPos;
                }
            }

            if (nearestCloud != null) {
                animal.getNavigation().moveTo(nearestCloud.getX() + 0.5, nearestCloud.getY(), nearestCloud.getZ() + 0.5,
                        ATTRACTION_SPEED);
                ANIMAL_TARGETS.put(animal.getUUID(), new CloudTarget(nearestCloud, level.dimension()));
            } else {
                ANIMAL_TARGETS.remove(animal.getUUID());
            }
        }
    }

    @Override
    public VoxelShape getDefaultCollisionShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) {
        return COLLISION_SHAPE;
    }
}