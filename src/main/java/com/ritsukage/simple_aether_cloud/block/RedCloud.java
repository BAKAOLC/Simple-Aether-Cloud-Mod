package com.ritsukage.simple_aether_cloud.block;

import com.ritsukage.simple_aether_cloud.SimpleAetherCloud;
import com.ritsukage.simple_aether_cloud.config.CloudConfig;
import com.ritsukage.simple_aether_cloud.util.CloudUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RedCloud extends YellowCloud {
    protected static final VoxelShape COLLISION_SHAPE = Shapes.empty();

    public RedCloud(Properties properties) {
        super(properties);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            return;
        }

        if (entity.isShiftKeyDown()) {
            return;
        }

        if (level.isClientSide()) {
            CloudUtils.playCloudSound(level, pos, entity, SoundEvents.SLIME_BLOCK_BREAK);
            CloudUtils.spawnCloudParticles(level, pos, ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER,
                    CloudConfig.RED_CLOUD_PARTICLE_COUNT.get());
            return;
        }

        livingEntity.hurt(
                new DamageSource(
                        level.registryAccess().lookupOrThrow(net.minecraft.core.registries.Registries.DAMAGE_TYPE)
                                .getOrThrow(SimpleAetherCloud.RED_CLOUD_DAMAGE_TYPE)),
                CloudConfig.RED_CLOUD_DAMAGE_AMOUNT.get().floatValue());
    }

    @Override
    public VoxelShape getDefaultCollisionShape(BlockState state, BlockGetter level, BlockPos pos,
            CollisionContext context) {
        return COLLISION_SHAPE;
    }
}