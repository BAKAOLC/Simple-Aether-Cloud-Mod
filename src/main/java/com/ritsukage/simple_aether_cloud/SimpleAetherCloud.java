package com.ritsukage.simple_aether_cloud;

import com.ritsukage.simple_aether_cloud.config.CloudConfig;
import com.ritsukage.simple_aether_cloud.event.GreenCloudHandler;
import com.ritsukage.simple_aether_cloud.registry.CloudRegistry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.fml.loading.FMLEnvironment;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

@Mod(SimpleAetherCloud.MODID)
public class SimpleAetherCloud {
    public static final String MODID = "simple_aether_cloud";

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(Registries.BLOCK_ENTITY_TYPE, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister<DamageType> DAMAGE_TYPES = DeferredRegister
            .create(Registries.DAMAGE_TYPE, MODID);

    public static final ResourceKey<DamageType> RED_CLOUD_DAMAGE_TYPE = ResourceKey.create(
            Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "red_cloud"));

    public static final CloudRegistry CLOUD_REGISTRY = new CloudRegistry(BLOCKS, ITEMS, BLOCK_ENTITIES);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CLOUD_TAB = CREATIVE_MODE_TABS.register(
            "cloud_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.simple_aether_cloud"))
                    .icon(() -> CLOUD_REGISTRY.getFirstCloudItem().get().getDefaultInstance())
                    .displayItems((parameters, output) -> CLOUD_REGISTRY.getAllCloudItems()
                            .forEach(item -> output.accept(item.get())))
                    .build());

    public SimpleAetherCloud(IEventBus modEventBus, ModContainer modContainer) {
        CLOUD_REGISTRY.registerAll(modEventBus);
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        DAMAGE_TYPES.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);

        NeoForge.EVENT_BUS.register(GreenCloudHandler.class);
        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, CloudConfig.SPEC);
        if (FMLEnvironment.dist.isClient()) {
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            CLOUD_REGISTRY.getAllCloudItems().forEach(event::accept);
        }
    }
}
