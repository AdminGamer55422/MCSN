package net.admingamer.mcsn.util;

import net.admingamer.mcsn.MCSN;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class MCSNTags {
    public static class Blocks {
        public static final TagKey<Block> DIAMOND_ORE_VARIANTS = tag("diamond_ore_variants");
        public static final TagKey<Block> NEEDS_PRISMARINE_TOOL = tag("needs_prismarine_tool");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(MCSN.MOD_ID, name));
        }
    }
    public static class Items {
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(MCSN.MOD_ID, name));
        }

    }
}
