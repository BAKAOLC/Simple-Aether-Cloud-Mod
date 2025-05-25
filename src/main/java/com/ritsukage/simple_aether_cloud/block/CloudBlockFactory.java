package com.ritsukage.simple_aether_cloud.block;

import com.ritsukage.simple_aether_cloud.item.CloudBlockItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class CloudBlockFactory {
    private static final BlockBehaviour.StatePredicate NO_REDSTONE = (state, getter, pos) -> false;
    private static final BlockBehaviour.StatePredicate NO_SUFFOCATING = (state, getter, pos) -> false;
    private static final BlockBehaviour.StatePredicate NO_VIEW_BLOCKING = (state, getter, pos) -> false;

    private final DeferredRegister.Blocks blocks;
    private final DeferredRegister.Items items;
    private final Map<String, DeferredBlock<? extends Block>> registeredBlocks = new HashMap<>();

    public CloudBlockFactory(DeferredRegister.Blocks blocks, DeferredRegister.Items items) {
        this.blocks = blocks;
        this.items = items;
    }

    private BlockBehaviour.Properties createBaseProperties(MapColor color) {
        return Block.Properties.of()
                .mapColor(color)
                .instrument(NoteBlockInstrument.FLUTE)
                .strength(0.3F)
                .sound(SoundType.WOOL)
                .noOcclusion()
                .dynamicShape()
                .isRedstoneConductor(NO_REDSTONE)
                .isSuffocating(NO_SUFFOCATING)
                .isViewBlocking(NO_VIEW_BLOCKING);
    }

    public <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> blockSupplier) {
        return blocks.register(name, blockSupplier);
    }

    public <T extends Block> DeferredItem<BlockItem> registerItem(String name, DeferredBlock<T> block) {
        return items.register(name, (Function<ResourceLocation, BlockItem>) location -> new CloudBlockItem(block.get(),
                new Item.Properties()));
    }

    public <T extends Block> DeferredBlock<T> registerCloudBlock(String name, Class<T> blockClass, MapColor color) {
        return registerBlock(name, () -> {
            try {
                return blockClass.getConstructor(BlockBehaviour.Properties.class)
                        .newInstance(createBaseProperties(color));
            } catch (Exception e) {
                throw new RuntimeException("Failed to create cloud block: " + name, e);
            }
        });
    }

    public Block getBlock(String name) {
        DeferredBlock<? extends Block> block = registeredBlocks.get(name);
        if (block == null) {
            throw new IllegalArgumentException("Block not found: " + name);
        }
        return block.get();
    }
}