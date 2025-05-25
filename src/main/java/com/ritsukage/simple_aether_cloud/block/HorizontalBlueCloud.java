package com.ritsukage.simple_aether_cloud.block;

import com.ritsukage.simple_aether_cloud.config.CloudConfig;
import com.ritsukage.simple_aether_cloud.util.CloudUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HorizontalBlueCloud extends BlueCloud {
    protected static final VoxelShape COLLISION_SHAPE = Shapes.empty();

    public HorizontalBlueCloud(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING,
                context.getHorizontalDirection());
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!entity.isShiftKeyDown()
                && (!entity.isVehicle() || !(entity.getControllingPassenger() instanceof Player))) {
            Vec3 prevMotion = entity.getDeltaMovement();
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            double speed = CloudConfig.HORIZONTAL_BLUE_CLOUD_HORIZONTAL_LAUNCH_SPEED.get();
            Vec3 newMotion = CloudUtils.calculateDirection(prevMotion,
                    new Vec3(facing.getStepX() * speed, 0, facing.getStepZ() * speed), true, false, true);
            CloudUtils.launchEntity(entity, newMotion);

            if (level.isClientSide()) {
                CloudUtils.playCloudSound(level, pos, entity, SoundEvents.SLIME_BLOCK_BREAK);
                CloudUtils.spawnCloudParticles(level, pos, ParticleTypes.SPLASH,
                        CloudConfig.HORIZONTAL_BLUE_CLOUD_PARTICLE_COUNT.get());
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