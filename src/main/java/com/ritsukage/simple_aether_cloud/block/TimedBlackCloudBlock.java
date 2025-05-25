package com.ritsukage.simple_aether_cloud.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TimedBlackCloudBlock extends BlackCloudBlock implements EntityBlock {
    public TimedBlackCloudBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TimedBlackCloudBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
            BlockEntityType<T> type) {
        return level.isClientSide ? null : (level1, pos, state1, blockEntity) -> {
            if (blockEntity instanceof TimedBlackCloudBlockEntity) {
                ((TimedBlackCloudBlockEntity) blockEntity).tick(level1, pos, state1,
                        (TimedBlackCloudBlockEntity) blockEntity);
            }
        };
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.isClientSide) {
            level.setBlock(pos, state.setValue(ACTIVATED, true), 2);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos,
            boolean isMoving) {
    }
}