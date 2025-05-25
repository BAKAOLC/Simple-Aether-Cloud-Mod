package com.ritsukage.simple_aether_cloud.block;

import com.ritsukage.simple_aether_cloud.config.CloudConfig;
import com.ritsukage.simple_aether_cloud.util.CloudUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class GreenCloud extends YellowCloud implements EntityBlock {
    protected static final VoxelShape COLLISION_SHAPE = Shapes.empty();

    public GreenCloud(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getDefaultCollisionShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);

        if (level.isClientSide()) {
            CloudUtils.playCloudSound(level, pos, entity, SoundEvents.SLIME_BLOCK_BREAK);
            CloudUtils.spawnCloudParticles(level, pos, ParticleTypes.HAPPY_VILLAGER,
                    CloudConfig.GREEN_CLOUD_PARTICLE_COUNT.get());
            return;
        }

        if (entity instanceof AgeableMob ageable) {
            if (ageable.getAge() < 0) {
                int newAge = ageable.getAge() + CloudConfig.GREEN_CLOUD_TIMER_MODIFIER.get();
                ageable.setAge(Math.min(newAge, 0));
            } else if (ageable.getAge() > 0) {
                int newAge = ageable.getAge() - CloudConfig.GREEN_CLOUD_TIMER_MODIFIER.get();
                ageable.setAge(Math.max(newAge, 0));
            }

            if (entity instanceof Animal animal && animal.canFallInLove() && !animal.isInLove()
                    && level.getGameTime() % CloudConfig.GREEN_CLOUD_BREED_INTERVAL.get() == 0) {
                animal.setInLove(null);
            }
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GreenCloudBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
            BlockEntityType<T> type) {
        return level.isClientSide ? null : (level1, pos, state1, blockEntity) -> {
            if (blockEntity instanceof GreenCloudBlockEntity greenCloud) {
                greenCloud.tick();
            }
        };
    }
}