package com.ritsukage.simple_aether_cloud.block;

import com.ritsukage.simple_aether_cloud.config.CloudConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
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
            entity.resetFallDistance();

            Direction[] directions = Direction.values();
            Direction randomDir = directions[RANDOM.nextInt(directions.length)];

            double speed = CloudConfig.BLUE_CLOUD_VERTICAL_LAUNCH_SPEED.get();
            Vec3 newMotion = new Vec3(
                    randomDir.getStepX() * speed,
                    randomDir.getStepY() * speed,
                    randomDir.getStepZ() * speed);

            entity.setDeltaMovement(newMotion);

            if (level.isClientSide()) {
                int amount = CloudConfig.BLUE_CLOUD_MOVING_PARTICLE_COUNT.get();
                if (entity.getY() == entity.yOld) {
                    amount = CloudConfig.BLUE_CLOUD_STATIC_PARTICLE_COUNT.get();
                }
                if (entity.getDeltaMovement().y() != prevMotion.y()) {
                    level.playSound((entity instanceof Player player ? player : null), pos,
                            SoundEvents.SLIME_BLOCK_BREAK, SoundSource.BLOCKS, 0.8F,
                            0.5F + (((float) (Math.pow(level.getRandom().nextDouble(), 2.5))) * 0.5F));
                }
                for (int count = 0; count < amount; count++) {
                    double xOffset = pos.getX() + level.getRandom().nextDouble();
                    double yOffset = pos.getY() + level.getRandom().nextDouble();
                    double zOffset = pos.getZ() + level.getRandom().nextDouble();
                    level.addParticle(ParticleTypes.SPLASH, xOffset, yOffset, zOffset, 0.0, 0.0, 0.0);
                }
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