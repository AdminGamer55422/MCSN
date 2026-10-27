package net.admingamer.mcsn.datagen;

import net.admingamer.mcsn.MCSN;
import net.admingamer.mcsn.block.MCSNBlocks;
import net.admingamer.mcsn.item.MCSNItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MCSNItemModelProvider extends ItemModelProvider {
    public MCSNItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MCSN.MOD_ID, existingFileHelper);
    }

    /** Items:
     * <blockquote><pre>{@code
     * simpleItem(MCSNItems.WITHERED_NETHER_STAR);
     * }</pre></blockquote>
     *
     * Blocks:
     * <blockquote><pre>{@code
     * simpleBlockItem(MCSNBlocks.GLOWING_OBSIDIAN);
     * }</pre></blockquote>
     *
     * Fences, Buttons, and Walls:
     * <blockquote><pre>{@code
     * fenceItem(MCSNBlocks.DYABLE_WOODEN_FENCE, MCSNBlocks.DYABLE_PLANKS);
     * buttonItem(MCSNBlocks.DYABLE_WOODEN_BUTTON, MCSNBlocks.DYABLE_WOODEN_FENCE);
     * wallItem(MCSNBlocks.IRON_WALL, MCSNBlocks.CLASSIC_IRON_BLOCK);
     * }</pre></blockquote>
     *
     * Stairs, Slabs, Pressure Plates, and Fence Gates:
     * <blockquote><pre>{@code
     * evenSimplerBlockItem(MCSNBlocks.DYABLE_TERRACOTTA_STAIRS);
     * evenSimplerBlockItem(MCSNBlocks.SNOWY_VILLAGE_SHINGLES);
     * evenSimplerBlockItem(MCSNBlocks.DYABLE_WOODEN_PRESSURE_PLATE);
     * evenSimplerBlockItem(MCSNBlocks.DYABLE_WOODEN_FENCE_GATE);
     * }</pre></blockquote>
     *
     * Trapdoors:
     * <blockquote><pre>{@code
     * trapdoorItem(MCSNBlocks.GLASS_TRAPDOOR);
     * }</pre></blockquote>
     */

    @Override
    protected void registerModels() {
        handheldItem(MCSNItems.PRISMARINE_AXE);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.tryParse("item/generated")).texture("layer0",
                ResourceLocation.tryBuild(MCSN.MOD_ID, "item/" + item.getId().getPath()));
    }

    public void evenSimplerBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(MCSN.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }

    public void trapdoorItem(RegistryObject<Block> block) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
    }

    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(MCSN.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture", ResourceLocation.fromNamespaceAndPath(MCSN.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", ResourceLocation.fromNamespaceAndPath(MCSN.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(MCSN.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(MCSN.MOD_ID, "item/" + item.getId().getPath()));
    }
}
