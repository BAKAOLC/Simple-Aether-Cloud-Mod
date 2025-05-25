package com.ritsukage.simple_aether_cloud.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

import com.ritsukage.simple_aether_cloud.config.CloudConfig;
import com.ritsukage.simple_aether_cloud.util.CloudUtils;

public class BlackCloudBlock extends Block {
    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");
    private static final VoxelShape EMPTY_SHAPE = Shapes.empty();
    private static final VoxelShape FULL_SHAPE = Shapes.block();

    public BlackCloudBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVATED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return FULL_SHAPE;
    }

    @Override
    protected boolean isPathfindable(BlockState pState, PathComputationType pPathComputationType) {
        return false;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(ACTIVATED) ? FULL_SHAPE : EMPTY_SHAPE;
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos,
            boolean isMoving) {
        if (!world.isClientSide) {
            boolean powered = world.hasNeighborSignal(pos);
            if (powered != state.getValue(ACTIVATED)) {
                world.setBlock(pos, state.setValue(ACTIVATED, powered), 2);
            }
        }
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.getValue(ACTIVATED)) {
            CloudUtils.spawnCloudParticles(world, pos, DustParticleOptions.REDSTONE,
                    CloudConfig.BLACK_CLOUD_PARTICLE_COUNT.get());
        }
    }

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
    }
}