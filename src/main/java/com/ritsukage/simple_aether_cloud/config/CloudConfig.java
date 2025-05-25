package com.ritsukage.simple_aether_cloud.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CloudConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    // Blue Cloud
    public static final ModConfigSpec.DoubleValue BLUE_CLOUD_VERTICAL_LAUNCH_SPEED;
    public static final ModConfigSpec.IntValue BLUE_CLOUD_MOVING_PARTICLE_COUNT;
    public static final ModConfigSpec.IntValue BLUE_CLOUD_STATIC_PARTICLE_COUNT;

    // Horizontal Blue Cloud
    public static final ModConfigSpec.DoubleValue HORIZONTAL_BLUE_CLOUD_HORIZONTAL_LAUNCH_SPEED;
    public static final ModConfigSpec.IntValue HORIZONTAL_BLUE_CLOUD_MOVING_PARTICLE_COUNT;
    public static final ModConfigSpec.IntValue HORIZONTAL_BLUE_CLOUD_STATIC_PARTICLE_COUNT;

    // Red Cloud
    public static final ModConfigSpec.DoubleValue RED_CLOUD_DAMAGE_AMOUNT;
    public static final ModConfigSpec.IntValue RED_CLOUD_PARTICLE_COUNT;

    // Green Cloud
    public static final ModConfigSpec.IntValue GREEN_CLOUD_ATTRACTION_RANGE;
    public static final ModConfigSpec.DoubleValue GREEN_CLOUD_MIN_DISTANCE;
    public static final ModConfigSpec.DoubleValue GREEN_CLOUD_ATTRACTION_SPEED;
    public static final ModConfigSpec.IntValue GREEN_CLOUD_BREED_INTERVAL;
    public static final ModConfigSpec.IntValue GREEN_CLOUD_TIMER_MODIFIER;

    static {
        BUILDER.push("blue_cloud");
        BLUE_CLOUD_VERTICAL_LAUNCH_SPEED = BUILDER
                .comment("Vertical launch speed of the blue cloud")
                .translation("simple_aether_cloud.configuration.blue_cloud.vertical_launch_speed")
                .defineInRange("verticalLaunchSpeed", 2.0, 0.1, 10.0);
        BLUE_CLOUD_MOVING_PARTICLE_COUNT = BUILDER
                .comment("Number of moving particles for the blue cloud")
                .translation("simple_aether_cloud.configuration.blue_cloud.moving_particle_count")
                .defineInRange("movingParticleCount", 50, 0, 100);
        BLUE_CLOUD_STATIC_PARTICLE_COUNT = BUILDER
                .comment("Number of static particles for the blue cloud")
                .translation("simple_aether_cloud.configuration.blue_cloud.static_particle_count")
                .defineInRange("staticParticleCount", 10, 0, 100);
        BUILDER.pop();

        BUILDER.push("horizontal_blue_cloud");
        HORIZONTAL_BLUE_CLOUD_HORIZONTAL_LAUNCH_SPEED = BUILDER
                .comment("Horizontal launch speed of the horizontal blue cloud")
                .translation("simple_aether_cloud.configuration.horizontal_blue_cloud.horizontal_launch_speed")
                .defineInRange("horizontalLaunchSpeed", 2.0, 0.1, 10.0);
        HORIZONTAL_BLUE_CLOUD_MOVING_PARTICLE_COUNT = BUILDER
                .comment("Number of moving particles for the horizontal blue cloud")
                .translation("simple_aether_cloud.configuration.horizontal_blue_cloud.moving_particle_count")
                .defineInRange("movingParticleCount", 50, 0, 100);
        HORIZONTAL_BLUE_CLOUD_STATIC_PARTICLE_COUNT = BUILDER
                .comment("Number of static particles for the horizontal blue cloud")
                .translation("simple_aether_cloud.configuration.horizontal_blue_cloud.static_particle_count")
                .defineInRange("staticParticleCount", 10, 0, 100);
        BUILDER.pop();

        BUILDER.push("red_cloud");
        RED_CLOUD_DAMAGE_AMOUNT = BUILDER
                .comment("Amount of damage dealt by the red cloud")
                .translation("simple_aether_cloud.configuration.red_cloud.damage_amount")
                .defineInRange("damageAmount", 4.0, 0, Double.MAX_VALUE);
        RED_CLOUD_PARTICLE_COUNT = BUILDER
                .comment("Number of particles for the red cloud")
                .translation("simple_aether_cloud.configuration.red_cloud.particle_count")
                .defineInRange("particleCount", 10, 0, 100);
        BUILDER.pop();

        BUILDER.push("green_cloud");
        GREEN_CLOUD_ATTRACTION_RANGE = BUILDER
                .comment("Range at which the green cloud attracts entities")
                .translation("simple_aether_cloud.configuration.green_cloud.attraction_range")
                .defineInRange("attractionRange", 16, 1, 64);
        GREEN_CLOUD_MIN_DISTANCE = BUILDER
                .comment("Minimum distance maintained by the green cloud")
                .translation("simple_aether_cloud.configuration.green_cloud.min_distance")
                .defineInRange("minDistance", 3.0, 0.1, 16.0);
        GREEN_CLOUD_ATTRACTION_SPEED = BUILDER
                .comment("Speed at which entities are attracted to the green cloud")
                .translation("simple_aether_cloud.configuration.green_cloud.attraction_speed")
                .defineInRange("attractionSpeed", 0.8, 0.1, 2.0);
        GREEN_CLOUD_BREED_INTERVAL = BUILDER
                .comment("Interval between breeding attempts for the green cloud")
                .translation("simple_aether_cloud.configuration.green_cloud.breed_interval")
                .defineInRange("breedInterval", 200, 20, Integer.MAX_VALUE);
        GREEN_CLOUD_TIMER_MODIFIER = BUILDER
                .comment("Timer modifier for the green cloud's effects")
                .translation("simple_aether_cloud.configuration.green_cloud.timer_modifier")
                .defineInRange("timerModifier", 2, 1, Integer.MAX_VALUE);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}