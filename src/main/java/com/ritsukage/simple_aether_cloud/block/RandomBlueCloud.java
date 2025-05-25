package com.ritsukage.simple_aether_cloud.block;

import com.ritsukage.simple_aether_cloud.config.CloudConfig;
import com.ritsukage.simple_aether_cloud.util.CloudUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class RandomBlueCloud extends YellowCloud {
    protected static final VoxelShape COLLISION_SHAPE = Shapes.empty();
    private static final Random RANDOM = new Random();

    public RandomBlueCloud(Properties properties) {
        super(properties);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!entity.isShiftKeyDown()) {
            Vec3 prevMotion = entity.getDeltaMovement();
            Direction[] directions = Direction.values();
            Direction randomDir = directions[RANDOM.nextInt(directions.length)];
            double speed = CloudConfig.RANDOM_BLUE_CLOUD_LAUNCH_SPEED.get();
            Vec3 newMotion = CloudUtils.calculateDirection(prevMotion, new Vec3(randomDir.getStepX() * speed, randomDir.getStepY() * speed, randomDir.getStepZ() * speed), true, true, true);
            CloudUtils.launchEntity(entity, newMotion);

            if (level.isClientSide()) {
                CloudUtils.playCloudSound(level, pos, entity, SoundEvents.SLIME_BLOCK_BREAK);
                CloudUtils.spawnCloudParticles(level, pos, ParticleTypes.SPLASH,
                        CloudConfig.RANDOM_BLUE_CLOUD_PARTICLE_COUNT.get());
            }
            entity.setOnGround(false);
        } else {
            super.entityInside(state, level, pos, entity);
        }
    }

    @Override
    public VoxelShape getDefaultCollisionShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) {
        return COLLISION_SHAPE;
    }
}