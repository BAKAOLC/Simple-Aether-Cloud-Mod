package com.ritsukage.simple_aether_cloud.block;

import com.ritsukage.simple_aether_cloud.SimpleAetherCloud;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.Registries;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(Registries.BLOCK_ENTITY_TYPE, SimpleAetherCloud.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GreenCloudBlockEntity>> GREEN_CLOUD = BLOCK_ENTITIES
            .register("green_cloud",
                    () -> BlockEntityType.Builder.of(GreenCloudBlockEntity::new,
                            SimpleAetherCloud.BLOCKS.getEntries().stream()
                                    .filter(entry -> entry.getId().getPath().equals("green_cloud"))
                                    .findFirst()
                                    .orElseThrow()
                                    .get())
                            .build(null));
}