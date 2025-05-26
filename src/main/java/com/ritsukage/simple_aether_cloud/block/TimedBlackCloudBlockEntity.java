package com.ritsukage.simple_aether_cloud.block;

import com.ritsukage.simple_aether_cloud.registry.CloudRegistry;
import com.ritsukage.simple_aether_cloud.config.CloudConfig;
import com.ritsukage.simple_aether_cloud.util.CloudUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TimedBlackCloudBlockEntity extends BlockEntity implements BlockEntityTicker<TimedBlackCloudBlockEntity> {
    private static final String TIMER_TAG = "Timer";
    private static final String EMPTY_TIME_TAG = "EmptyTime";
    private int timer = 0;
    private int emptyTime = 0;

    public TimedBlackCloudBlockEntity(BlockPos pos, BlockState state) {
        super(CloudRegistry.getBlockEntityType("black_cloud_timed"), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putInt(TIMER_TAG, timer);
        tag.putInt(EMPTY_TIME_TAG, emptyTime);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        timer = tag.getInt(TIMER_TAG);
        emptyTime = tag.getInt(EMPTY_TIME_TAG);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, TimedBlackCloudBlockEntity blockEntity) {
        if (level.isClientSide) {
            return;
        }

        AABB searchBox = new AABB(pos.getX(), pos.getY() + 1, pos.getZ(),
                pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1);
        int entityCount = level.getEntitiesOfClass(Entity.class, searchBox).size();

        if (entityCount > 0) {
            blockEntity.timer += entityCount;
            blockEntity.emptyTime = 0;

            if (blockEntity.timer >= CloudConfig.BLACK_CLOUD_TIMED_MAX_TIMER.get()) {
                blockEntity.timer = -CloudConfig.BLACK_CLOUD_TIMED_COOLDOWN.get();
            }
        } else {
            if (blockEntity.timer < 0) {
                blockEntity.timer++;
            } else if (blockEntity.timer > 0) {
                blockEntity.emptyTime++;
                if (blockEntity.emptyTime >= CloudConfig.BLACK_CLOUD_TIMED_RESET_TIME.get()) {
                    blockEntity.timer = 0;
                    blockEntity.emptyTime = 0;
                }
            }
        }

        boolean shouldBeActivated = blockEntity.timer >= 0;
        boolean shouldBeWarning = shouldBeActivated &&
                (CloudConfig.BLACK_CLOUD_TIMED_MAX_TIMER.get()
                        - blockEntity.timer) <= CloudConfig.BLACK_CLOUD_TIMED_WARNING_TIME.get();

        if (shouldBeActivated != state.getValue(BlackCloudBlock.ACTIVATED) ||
                shouldBeWarning != state.getValue(BlackCloudBlock.WARNING)) {
            level.setBlock(pos, state
                    .setValue(BlackCloudBlock.ACTIVATED, shouldBeActivated)
                    .setValue(BlackCloudBlock.WARNING, shouldBeWarning), 3);

            SoundEvent sound;
            if (!shouldBeActivated) {
                sound = SoundEvents.NOTE_BLOCK_BASS.value();
            } else if (shouldBeWarning) {
                sound = SoundEvents.NOTE_BLOCK_BIT.value();
            } else {
                sound = SoundEvents.NOTE_BLOCK_PLING.value();
            }
            CloudUtils.playCloudSound(level, pos, null, sound);
        }
    }
}