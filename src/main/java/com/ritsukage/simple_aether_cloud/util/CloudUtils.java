package com.ritsukage.simple_aether_cloud.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class CloudUtils {
    private static final Random RANDOM = new Random();

    /**
     * 设置实体的移动速度
     * 
     * @param entity 要设置的实体
     * @param motion 新的移动速度
     */
    public static void launchEntity(Entity entity, Vec3 motion) {
        entity.resetFallDistance();
        entity.setDeltaMovement(motion);
        entity.setOnGround(false);
    }

    /**
     * 计算方向向量，只修改指定的部分，保留原始移动的其他部分
     * 
     * @param originalMotion 原始移动向量
     * @param newDirection   新的方向向量
     * @param x              是否修改X轴
     * @param y              是否修改Y轴
     * @param z              是否修改Z轴
     * @return 新的移动向量
     */
    public static Vec3 calculateDirection(Vec3 originalMotion, Vec3 newDirection, boolean x, boolean y, boolean z) {
        return new Vec3(
                x ? newDirection.x : originalMotion.x,
                y ? newDirection.y : originalMotion.y,
                z ? newDirection.z : originalMotion.z);
    }

    /**
     * 生成云方块的粒子效果
     * 
     * @param level         世界
     * @param pos           方块位置
     * @param particleType  粒子类型
     * @param particleCount 粒子数量
     */
    public static void spawnCloudParticles(Level level, BlockPos pos, ParticleOptions particleType, int particleCount) {
        if (!level.isClientSide())
            return;

        for (int i = 0; i < particleCount; i++) {
            double x = pos.getX() + RANDOM.nextDouble();
            double y = pos.getY() + RANDOM.nextDouble();
            double z = pos.getZ() + RANDOM.nextDouble();
            double offset = 0.1;

            Direction direction = Direction.values()[RANDOM.nextInt(6)];
            switch (direction) {
                case DOWN -> y = pos.getY() + offset;
                case UP -> y = pos.getY() + 1 - offset;
                case NORTH -> z = pos.getZ() + offset;
                case SOUTH -> z = pos.getZ() + 1 - offset;
                case WEST -> x = pos.getX() + offset;
                case EAST -> x = pos.getX() + 1 - offset;
            }

            double randomOffset = 0.05;
            x += (RANDOM.nextDouble() - 0.5) * randomOffset;
            y += (RANDOM.nextDouble() - 0.5) * randomOffset;
            z += (RANDOM.nextDouble() - 0.5) * randomOffset;

            level.addParticle(particleType, x, y, z, 0.0, 0.0, 0.0);
        }
    }

    /**
     * 播放云方块的触发音效
     * 
     * @param level  世界
     * @param pos    方块位置
     * @param entity 触发实体
     * @param sound  要播放的音效
     */
    public static void playCloudSound(Level level, BlockPos pos, Entity entity, SoundEvent sound) {
        level.playSound(
                (entity instanceof Player player ? player : null),
                pos,
                sound,
                SoundSource.BLOCKS,
                0.8F,
                0.5F + (((float) (Math.pow(level.getRandom().nextDouble(), 2.5))) * 0.5F));
    }
}