package com.ritsukage.simple_aether_cloud.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import com.ritsukage.simple_aether_cloud.util.CloudUtils;

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
                boolean newState = !state.getValue(ACTIVATED);
                world.setBlock(pos, state.setValue(ACTIVATED, newState), 2);
                SoundEvent sound = newState ? SoundEvents.NOTE_BLOCK_PLING.value()
                        : SoundEvents.NOTE_BLOCK_BASS.value();
                CloudUtils.playCloudSound(world, pos, null, sound);
            }
        }
    }
}