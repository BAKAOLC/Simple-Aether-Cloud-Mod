package com.ritsukage.simple_aether_cloud.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;

public class PurpleCloud extends Block {
    public static final IntegerProperty STATE = IntegerProperty.create("state", 0, 31);

    private static final VoxelShape[] SHAPES = new VoxelShape[32];

    static {
        for (int i = 0; i <= 15; i++) {
            double height = (i + 1) / 16.0;
            SHAPES[i] = Shapes.create(0, 0, 0, 1, height, 1);
        }
        for (int i = 16; i <= 31; i++) {
            double height = (31 - i) / 16.0;
            SHAPES[i] = Shapes.create(0, (1 - height), 0, 1, 1, 1);
        }
    }

    public PurpleCloud(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(STATE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STATE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(STATE)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(STATE)];
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        entity.setDeltaMovement(entity.getDeltaMovement().x, Math.max(0.1, entity.getDeltaMovement().y),
                entity.getDeltaMovement().z);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!level.isClientSide) {
            level.scheduleTick(pos, this, 5);
        }
    }

    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pLevel.isClientSide) {
            int currentState = pState.getValue(STATE);
            int nextState = (currentState + 1) % 32;

            switch (nextState) {
                case 16: {
                    BlockPos abovePos = pPos.above();
                    if (pLevel.getBlockState(abovePos).canBeReplaced()) {
                        pLevel.setBlockAndUpdate(abovePos, this.defaultBlockState());
                    }
                    break;
                }
                case 31: {
                    BlockPos abovePos = pPos.above();
                    BlockState aboveState = pLevel.getBlockState(abovePos);

                    if (aboveState.getBlock() instanceof PurpleCloud) {
                        pLevel.removeBlock(pPos, true);
                    } else {
                        pLevel.destroyBlock(pPos, true);
                    }
                    return;
                }
            }

            pLevel.setBlockAndUpdate(pPos, pState.setValue(STATE, nextState));
            pLevel.scheduleTick(pPos, this, 5);
        }
    }
}