package com.ritsukage.simple_aether_cloud;

import com.ritsukage.simple_aether_cloud.block.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

@Mod(SimpleAetherCloud.MODID)
public class SimpleAetherCloud {
    public static final String MODID = "simple_aether_cloud";
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, MODID);

    private static final CloudBlockFactory CLOUD_FACTORY = new CloudBlockFactory(BLOCKS, ITEMS);
    private static final List<DeferredItem<BlockItem>> CLOUD_ITEMS = new ArrayList<>();

    private static final record CloudBlockInfo(String name, Class<? extends Block> blockClass, MapColor color) {
    }

    private static final List<CloudBlockInfo> CLOUD_BLOCKS = new ArrayList<>(List.of(
            new CloudBlockInfo("yellow_cloud", YellowCloud.class, MapColor.COLOR_YELLOW),
            new CloudBlockInfo("blue_cloud", BlueCloud.class, MapColor.COLOR_LIGHT_BLUE),
            new CloudBlockInfo("horizontal_blue_cloud", HorizontalBlueCloud.class,
                    MapColor.COLOR_LIGHT_BLUE),
            new CloudBlockInfo("red_cloud", RedCloud.class, MapColor.COLOR_RED)));

    static {
        CLOUD_BLOCKS.forEach(info -> {
            DeferredBlock<? extends Block> block = CLOUD_FACTORY.registerCloudBlock(info.name,
                    info.blockClass, info.color);
            CLOUD_ITEMS.add(CLOUD_FACTORY.registerItem(info.name, block));
        });
    }

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CLOUD_TAB = CREATIVE_MODE_TABS.register(
            "cloud_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.simple_aether_cloud"))
                    .icon(() -> CLOUD_ITEMS.get(0).get().getDefaultInstance())
                    .displayItems((parameters, output) -> CLOUD_ITEMS
                            .forEach(item -> output.accept(item.get())))
                    .build());

    public SimpleAetherCloud(IEventBus modEventBus, ModContainer modContainer) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        modEventBus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            CLOUD_ITEMS.forEach(event::accept);
        }
    }
}
