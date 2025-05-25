package com.ritsukage.simple_aether_cloud.block;

import com.ritsukage.simple_aether_cloud.config.CloudConfig;
import com.ritsukage.simple_aether_cloud.util.CloudUtils;
import net.minecraft.core.BlockPos;
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

public class BlueCloud extends YellowCloud {
    protected static final VoxelShape COLLISION_SHAPE = Shapes.empty();

    public BlueCloud(Properties properties) {
        super(properties);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!entity.isShiftKeyDown()) {
            Vec3 prevMotion = entity.getDeltaMovement();
            if (prevMotion.y < CloudConfig.BLUE_CLOUD_VERTICAL_LAUNCH_SPEED.get()) {
                Vec3 newMotion = CloudUtils.calculateDirection(prevMotion, new Vec3(0, CloudConfig.BLUE_CLOUD_VERTICAL_LAUNCH_SPEED.get(), 0), false, true, false);
                CloudUtils.launchEntity(entity, newMotion);
            }

            if (level.isClientSide()) {
                CloudUtils.playCloudSound(level, pos, entity, SoundEvents.SLIME_BLOCK_BREAK);
                CloudUtils.spawnCloudParticles(level, pos, ParticleTypes.SPLASH,
                        CloudConfig.BLUE_CLOUD_PARTICLE_COUNT.get());
            }
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