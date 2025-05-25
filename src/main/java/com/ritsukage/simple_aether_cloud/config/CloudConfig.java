package com.ritsukage.simple_aether_cloud.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CloudConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.DoubleValue BLUE_CLOUD_VERTICAL_LAUNCH_SPEED;
    public static final ModConfigSpec.IntValue BLUE_CLOUD_PARTICLE_COUNT;
    public static final ModConfigSpec.DoubleValue HORIZONTAL_BLUE_CLOUD_HORIZONTAL_LAUNCH_SPEED;
    public static final ModConfigSpec.IntValue HORIZONTAL_BLUE_CLOUD_PARTICLE_COUNT;
    public static final ModConfigSpec.DoubleValue RANDOM_BLUE_CLOUD_LAUNCH_SPEED;
    public static final ModConfigSpec.IntValue RANDOM_BLUE_CLOUD_PARTICLE_COUNT;
    public static final ModConfigSpec.DoubleValue RED_CLOUD_DAMAGE_AMOUNT;
    public static final ModConfigSpec.IntValue RED_CLOUD_PARTICLE_COUNT;
    public static final ModConfigSpec.IntValue GREEN_CLOUD_ATTRACTION_RANGE;
    public static final ModConfigSpec.DoubleValue GREEN_CLOUD_MIN_DISTANCE;
    public static final ModConfigSpec.DoubleValue GREEN_CLOUD_ATTRACTION_SPEED;
    public static final ModConfigSpec.IntValue GREEN_CLOUD_BREED_INTERVAL;
    public static final ModConfigSpec.IntValue GREEN_CLOUD_TIMER_MODIFIER;
    public static final ModConfigSpec.IntValue GREEN_CLOUD_MOVE_INTERVAL;
    public static final ModConfigSpec.IntValue GREEN_CLOUD_PARTICLE_COUNT;
    public static final ModConfigSpec.IntValue BLACK_CLOUD_PARTICLE_COUNT;
    public static final ModConfigSpec.IntValue BLACK_CLOUD_TIMED_MAX_TIMER;
    public static final ModConfigSpec.IntValue BLACK_CLOUD_TIMED_COOLDOWN;
    public static final ModConfigSpec.IntValue BLACK_CLOUD_TIMED_RESET_TIME;

    static {
        BUILDER.push("blue_cloud");
        BLUE_CLOUD_VERTICAL_LAUNCH_SPEED = BUILDER
                .comment("Vertical launch speed of the blue cloud")
                .translation("simple_aether_cloud.configuration.blue_cloud.launch_speed")
                .defineInRange("blue_cloud_launch_speed", 2.0, 0.1, 10.0);
        BLUE_CLOUD_PARTICLE_COUNT = BUILDER
                .comment("Number of particles for the blue cloud")
                .translation("simple_aether_cloud.configuration.blue_cloud.particle_count")
                .defineInRange("blue_cloud_particle_count", 20, 0, 100);

        HORIZONTAL_BLUE_CLOUD_HORIZONTAL_LAUNCH_SPEED = BUILDER
                .comment("Horizontal launch speed of the horizontal blue cloud")
                .translation("simple_aether_cloud.configuration.horizontal_blue_cloud.launch_speed")
                .defineInRange("horizontal_blue_cloud_launch_speed", 2.0, 0.1, 10.0);
        HORIZONTAL_BLUE_CLOUD_PARTICLE_COUNT = BUILDER
                .comment("Number of particles for the horizontal blue cloud")
                .translation("simple_aether_cloud.configuration.horizontal_blue_cloud.particle_count")
                .defineInRange("horizontal_blue_cloud_particle_count", 20, 0, 100);

        RANDOM_BLUE_CLOUD_LAUNCH_SPEED = BUILDER
                .comment("Speed at which entities are launched in random directions")
                .translation("simple_aether_cloud.configuration.random_blue_cloud.launch_speed")
                .defineInRange("random_blue_cloud_launch_speed", 2.0, 0.1, 10.0);
        RANDOM_BLUE_CLOUD_PARTICLE_COUNT = BUILDER
                .comment("Number of particles for the random blue cloud")
                .translation("simple_aether_cloud.configuration.random_blue_cloud.particle_count")
                .defineInRange("random_blue_cloud_particle_count", 20, 0, 100);
        BUILDER.pop();

        BUILDER.push("red_cloud");
        RED_CLOUD_DAMAGE_AMOUNT = BUILDER
                .comment("Amount of damage dealt by the red cloud")
                .translation("simple_aether_cloud.configuration.red_cloud.damage_amount")
                .defineInRange("red_cloud_damage_amount", 4.0, 0, Double.MAX_VALUE);
        RED_CLOUD_PARTICLE_COUNT = BUILDER
                .comment("Number of particles for the red cloud")
                .translation("simple_aether_cloud.configuration.red_cloud.particle_count")
                .defineInRange("red_cloud_particle_count", 5, 0, 100);
        BUILDER.pop();

        BUILDER.push("green_cloud");
        GREEN_CLOUD_ATTRACTION_RANGE = BUILDER
                .comment("Range at which the green cloud attracts entities")
                .translation("simple_aether_cloud.configuration.green_cloud.attraction_range")
                .defineInRange("green_cloud_attraction_range", 16, 1, 64);
        GREEN_CLOUD_MIN_DISTANCE = BUILDER
                .comment("Minimum distance maintained by the green cloud")
                .translation("simple_aether_cloud.configuration.green_cloud.min_distance")
                .defineInRange("green_cloud_min_distance", 3.0, 0.1, 16.0);
        GREEN_CLOUD_ATTRACTION_SPEED = BUILDER
                .comment("Speed at which entities are attracted to the green cloud")
                .translation("simple_aether_cloud.configuration.green_cloud.attraction_speed")
                .defineInRange("green_cloud_attraction_speed", 0.8, 0.1, 2.0);
        GREEN_CLOUD_BREED_INTERVAL = BUILDER
                .comment("Interval between breeding attempts for the green cloud")
                .translation("simple_aether_cloud.configuration.green_cloud.breed_interval")
                .defineInRange("green_cloud_breed_interval", 200, 20, Integer.MAX_VALUE);
        GREEN_CLOUD_TIMER_MODIFIER = BUILDER
                .comment("Timer modifier for the green cloud's effects")
                .translation("simple_aether_cloud.configuration.green_cloud.timer_modifier")
                .defineInRange("green_cloud_timer_modifier", 2, 1, Integer.MAX_VALUE);
        GREEN_CLOUD_MOVE_INTERVAL = BUILDER
                .comment("Interval between movement updates for the green cloud")
                .translation("simple_aether_cloud.configuration.green_cloud.move_interval")
                .defineInRange("green_cloud_move_interval", 20, 1, Integer.MAX_VALUE);
        GREEN_CLOUD_PARTICLE_COUNT = BUILDER
                .comment("Number of particles for the green cloud")
                .translation("simple_aether_cloud.configuration.green_cloud.particle_count")
                .defineInRange("green_cloud_particle_count", 5, 0, 100);
        BUILDER.pop();

        BUILDER.push("black_cloud");
        BLACK_CLOUD_PARTICLE_COUNT = BUILDER
                .comment("Number of particles for the black cloud")
                .translation("simple_aether_cloud.configuration.black_cloud.particle_count")
                .defineInRange("black_cloud_particle_count", 1, 0, 100);
        BLACK_CLOUD_TIMED_MAX_TIMER = BUILDER
                .comment("Maximum timer value for timed black cloud blocks")
                .translation("simple_aether_cloud.configuration.black_cloud.timed_max_timer")
                .defineInRange("black_cloud_timed_max_timer", 200, 1, 1000);
        BLACK_CLOUD_TIMED_COOLDOWN = BUILDER
                .comment("Cooldown duration for timed black cloud blocks")
                .translation("simple_aether_cloud.configuration.black_cloud.timed_cooldown")
                .defineInRange("black_cloud_timed_cooldown", 100, 1, 1000);
        BLACK_CLOUD_TIMED_RESET_TIME = BUILDER
                .comment("Time in ticks before resetting positive timer when no entity is present")
                .translation("simple_aether_cloud.configuration.black_cloud.timed_reset_time")
                .defineInRange("black_cloud_timed_reset_time", 100, 1, 1000);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}