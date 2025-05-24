package com.ritsukage.simple_aether_cloud.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
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
        if (!entity.isShiftKeyDown()
                && (!entity.isVehicle() || !(entity.getControllingPassenger() instanceof Player))) {
            Vec3 prevMotion = entity.getDeltaMovement();
            entity.resetFallDistance();
            entity.setDeltaMovement(prevMotion.x(), 2.0, prevMotion.z());

            if (level.isClientSide()) {
                int amount = 50;
                if (entity.getY() == entity.yOld) {
                    amount = 10;
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
            if (!(entity instanceof Projectile)) {
                entity.setOnGround(false);
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