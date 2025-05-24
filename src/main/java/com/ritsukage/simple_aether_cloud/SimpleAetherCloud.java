package com.ritsukage.simple_aether_cloud;

import com.ritsukage.simple_aether_cloud.block.*;
import com.ritsukage.simple_aether_cloud.item.CloudBlockItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(SimpleAetherCloud.MODID)
public class SimpleAetherCloud {
    public static final String MODID = "simple_aether_cloud";
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, MODID);

    private static final BlockBehaviour.StatePredicate NO_REDSTONE = (state, getter, pos) -> false;
    private static final BlockBehaviour.StatePredicate NO_SUFFOCATING = (state, getter, pos) -> false;
    private static final BlockBehaviour.StatePredicate NO_VIEW_BLOCKING = (state, getter, pos) -> false;

    public static final DeferredBlock<Block> YELLOW_CLOUD = BLOCKS.register("yellow_cloud",
            () -> new YellowCloud(Block.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .instrument(NoteBlockInstrument.FLUTE)
                    .strength(0.3F)
                    .sound(SoundType.WOOL)
                    .noOcclusion()
                    .dynamicShape()
                    .isRedstoneConductor(NO_REDSTONE)
                    .isSuffocating(NO_SUFFOCATING)
                    .isViewBlocking(NO_VIEW_BLOCKING)));

    public static final DeferredBlock<Block> BLUE_CLOUD = BLOCKS.register("blue_cloud",
            () -> new BlueCloud(Block.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .instrument(NoteBlockInstrument.FLUTE)
                    .strength(0.3F)
                    .sound(SoundType.WOOL)
                    .noOcclusion()
                    .dynamicShape()
                    .isRedstoneConductor(NO_REDSTONE)
                    .isSuffocating(NO_SUFFOCATING)
                    .isViewBlocking(NO_VIEW_BLOCKING)));

    public static final DeferredBlock<Block> HORIZONTAL_BLUE_CLOUD = BLOCKS.register("horizontal_blue_cloud",
            () -> new HorizontalBlueCloud(Block.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .instrument(NoteBlockInstrument.FLUTE)
                    .strength(0.3F)
                    .sound(SoundType.WOOL)
                    .noOcclusion()
                    .dynamicShape()
                    .isRedstoneConductor(NO_REDSTONE)
                    .isSuffocating(NO_SUFFOCATING)
                    .isViewBlocking(NO_VIEW_BLOCKING)));

    public static final DeferredBlock<Block> RED_CLOUD = BLOCKS.register("red_cloud",
            () -> new RedCloud(Block.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .instrument(NoteBlockInstrument.FLUTE)
                    .strength(0.3F)
                    .sound(SoundType.WOOL)
                    .noOcclusion()
                    .dynamicShape()
                    .isRedstoneConductor(NO_REDSTONE)
                    .isSuffocating(NO_SUFFOCATING)
                    .isViewBlocking(NO_VIEW_BLOCKING)));

    public static final DeferredItem<BlockItem> YELLOW_CLOUD_ITEM = ITEMS.register("yellow_cloud",
            () -> new CloudBlockItem(YELLOW_CLOUD.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> BLUE_CLOUD_ITEM = ITEMS.register("blue_cloud",
            () -> new CloudBlockItem(BLUE_CLOUD.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> HORIZONTAL_BLUE_CLOUD_ITEM = ITEMS.register("horizontal_blue_cloud",
            () -> new CloudBlockItem(HORIZONTAL_BLUE_CLOUD.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> RED_CLOUD_ITEM = ITEMS.register("red_cloud",
            () -> new CloudBlockItem(RED_CLOUD.get(), new Item.Properties()));

    private static final CreativeModeTab.DisplayItemsGenerator CLOUD_ITEMS = (parameters, output) -> {
        output.accept(YELLOW_CLOUD_ITEM.get());
        output.accept(BLUE_CLOUD_ITEM.get());
        output.accept(HORIZONTAL_BLUE_CLOUD_ITEM.get());
        output.accept(RED_CLOUD_ITEM.get());
    };

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CLOUD_TAB = CREATIVE_MODE_TABS.register(
            "cloud_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.simple_aether_cloud"))
                    .icon(() -> YELLOW_CLOUD_ITEM.get().getDefaultInstance())
                    .displayItems(CLOUD_ITEMS)
                    .build());

    public SimpleAetherCloud(IEventBus modEventBus, ModContainer modContainer) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        modEventBus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(YELLOW_CLOUD_ITEM);
            event.accept(BLUE_CLOUD_ITEM);
            event.accept(HORIZONTAL_BLUE_CLOUD_ITEM);
            event.accept(RED_CLOUD_ITEM);
        }
    }
}
