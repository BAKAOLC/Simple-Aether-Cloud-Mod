package com.ritsukage.simple_aether_cloud.block;

import com.ritsukage.simple_aether_cloud.SimpleAetherCloud;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntityFactory {
    private final DeferredRegister<BlockEntityType<?>> blockEntities;

    public BlockEntityFactory(DeferredRegister<BlockEntityType<?>> blockEntities) {
        this.blockEntities = blockEntities;
    }

    public <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(
            String name, BlockEntityType.BlockEntitySupplier<T> blockEntitySupplier, Block block) {
        return blockEntities.register(name,
                () -> BlockEntityType.Builder.of(blockEntitySupplier, block).build(null));
    }

    public <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(
            String name, BlockEntityType.BlockEntitySupplier<T> blockEntitySupplier) {
        return blockEntities.register(name,
                () -> BlockEntityType.Builder.of(blockEntitySupplier,
                        SimpleAetherCloud.BLOCKS.getEntries().stream()
                                .filter(entry -> entry.getId().getPath().equals(name))
                                .findFirst()
                                .orElseThrow()
                                .get())
                        .build(null));
    }
}