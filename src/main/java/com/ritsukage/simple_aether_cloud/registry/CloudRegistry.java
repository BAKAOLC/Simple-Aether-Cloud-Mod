package com.ritsukage.simple_aether_cloud.registry;

import com.ritsukage.simple_aether_cloud.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CloudRegistry {
    private static CloudRegistry instance;
    private final List<DeferredItem<BlockItem>> cloudItems = new ArrayList<>();
    private final CloudBlockFactory cloudFactory;
    private final BlockEntityFactory blockEntityFactory;
    private final Map<String, DeferredHolder<BlockEntityType<?>, ? extends BlockEntityType<?>>> blockEntities = new HashMap<>();

    public CloudRegistry(DeferredRegister.Blocks blocks, DeferredRegister.Items items,
            DeferredRegister<BlockEntityType<?>> blockEntities) {
        this.cloudFactory = new CloudBlockFactory(blocks, items);
        this.blockEntityFactory = new BlockEntityFactory(blockEntities);
        instance = this;
    }

    public static BlockEntityType<?> getBlockEntityType(String name) {
        if (instance == null) {
            throw new IllegalStateException("CloudRegistry not initialized");
        }
        DeferredHolder<BlockEntityType<?>, ? extends BlockEntityType<?>> holder = instance.blockEntities.get(name);
        if (holder == null) {
            throw new IllegalArgumentException("Block entity type not found: " + name);
        }
        return holder.get();
    }

    public void registerAll(IEventBus modEventBus) {
        for (CloudType type : CloudType.values()) {
            registerCloudBlock(type);
        }

        registerBlockEntity("green_cloud", GreenCloudBlockEntity::new);
        registerBlockEntity("black_cloud_timed", TimedBlackCloudBlockEntity::new);
    }

    private void registerCloudBlock(CloudType type) {
        DeferredBlock<? extends Block> block = cloudFactory.registerCloudBlock(
                type.getName(),
                type.getBlockClass(),
                type.getColor());
        cloudItems.add(cloudFactory.registerItem(type.getName(), block));
    }

    private <T extends BlockEntity> void registerBlockEntity(String name,
            BlockEntityType.BlockEntitySupplier<T> supplier) {
        blockEntities.put(name, blockEntityFactory.register(name, supplier));
    }

    public List<DeferredItem<BlockItem>> getAllCloudItems() {
        return cloudItems;
    }

    public DeferredItem<BlockItem> getFirstCloudItem() {
        if (cloudItems.isEmpty()) {
            throw new IllegalStateException("No cloud items registered");
        }
        return cloudItems.get(0);
    }
}