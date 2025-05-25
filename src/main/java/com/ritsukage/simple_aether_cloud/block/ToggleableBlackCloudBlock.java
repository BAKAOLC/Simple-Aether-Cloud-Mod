package com.ritsukage.simple_aether_cloud.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class ToggleableBlackCloudBlock extends BlackCloudBlock {
    public ToggleableBlackCloudBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos,
            boolean isMoving) {
        if (!world.isClientSide) {
            boolean powered = world.hasNeighborSignal(pos);
            if (powered) {
                world.setBlock(pos, state.cycle(ACTIVATED), 2);
            }
        }
    }
}