package com.ritsukage.simple_aether_cloud.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class InvertedBlackCloudBlock extends BlackCloudBlock {
    public InvertedBlackCloudBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVATED, true));
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos,
            boolean isMoving) {
        if (!world.isClientSide) {
            boolean powered = world.hasNeighborSignal(pos);
            world.setBlock(pos, state.setValue(ACTIVATED, !powered), 2);
        }
    }
}