package com.ritsukage.simple_aether_cloud.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class CloudBlockItem extends BlockItem {
    public CloudBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents,
            TooltipFlag isAdvanced) {
        super.appendHoverText(stack, context, tooltipComponents, isAdvanced);
        String descKey = this.getDescriptionId() + ".desc";
        String[] lines = Component.translatable(descKey).getString().split("\n");
        for (String line : lines) {
            tooltipComponents.add(Component.literal(line));
        }
    }
}