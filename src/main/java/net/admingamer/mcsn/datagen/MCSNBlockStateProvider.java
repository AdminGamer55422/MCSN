package net.admingamer.mcsn.datagen;

import net.admingamer.mcsn.MCSN;
import net.admingamer.mcsn.block.MCSNBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.compress.compressors.lz77support.LZ77Compressor;

public class MCSNBlockStateProvider extends BlockStateProvider {
    public MCSNBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MCSN.MOD_ID, exFileHelper);
    }

    /** Block:
     * <blockquote><pre>{@code
     * blockWithItem(MCSNBlocks.GLOWING_OBSIDIAN);
     * }</pre></blockquote>
     *
     * Stairs and Slabs:
     * <blockquote><pre>{@code
     * stairsBlock(((StairBlock) MCSNBlocks.DYABLE_TERRACOTTA_STAIRS.get()), blockTexture(MCSNBlocks.DYABLE_TERRACOTTA.get()));
     * slabBlock(((SlabBlock) MCSNBlocks.SNOWY_VILLAGE_SHINGLES.get()), blockTexture(MCSNBlocks.SNOWY_VILLAGE_SHINGLES_BLOCK.get()), blockTexture(MCSNBlocks.SNOWY_VILLAGE_SHINGLES_BLOCK.get));
     * }</pre></blockquote>
     *
     * Buttons and Pressure Plates:
     * <blockquote><pre>{@code
     * buttonBlock(((ButtonBlock) MCSNBlocks.DYABLE_WOODEN_BUTTON.get()), blockTextxure(MCSNBlocks.DYABLE_PLANKS.get()));
     * pressurePlateBlock(((PressurePlateBlock) MCSNBlocks.DYABLE_WOODEN_PRESSURE_PLATE.get()), blockTexture(MCSNBlocks.DYABLE_PLANKS.get()));
     * }</pre></blockquote>
     *
     * Fences, Fence Gates, and Walls:
     * <blockquote><pre>{@code
     * fenceBlock(((FenceBlock) MCSNBlocks.DYABLE_WOODEN_FENCE.get()), blockTexture(MCSNBlocks.DYABLE_PLANKS.get()));
     * fenceGateBlock(((FenceGateBlock) MCSNBlocks.DYABLE_WOODEN_FENCE_GATE.get()), blockTexture(MCSNBlocks.DYABLE_PLANKS.get()));
     * wallBlock(((WallBlock) MCSNBlocks.IRON_WALL.get()), blockTexture(MCSNBlocks.CLASSIC_IRON_BLOCK.get()));
     * }</pre></blockquote>
     *
     * Doors and Trapdoors:
     * <blockquote><pre>{@code
     * doorBlockWithRenderType(((DoorBlock) MCSNBlocks.TOWN_FAIR_DOOR_A.get()), modLoc("block/town_fair_door_a/town_fair_door_a_bottom"), modLoc("block/town_fair_door_a/town_fair_door_a_top"), "cutout");
     * * trapdoorBlockWithRenderType(((TrapDoorBlock) MCSNBlocks.GLASS_TRAPDOOR.get()), modLoc("block/glass_trapdoor"), true, "cutout");
     * }</pre></blockquote>
     */

    @Override
    protected void registerStatesAndModels() {
        doorBlockWithRenderType(((DoorBlock) MCSNBlocks.TOWN_FAIR_DOOR_A.get()), modLoc("block/town_fair_door_a/town_fair_door_a_bottom"), modLoc("block/town_fair_door_a/town_fair_door_a_top"), "cutout");
        doorBlockWithRenderType(((DoorBlock) MCSNBlocks.TOWN_FAIR_DOOR_B.get()), modLoc("block/town_fair_door_b/town_fair_door_b_bottom"), modLoc("block/town_fair_door_b/town_fair_door_b_top"), "cutout");
        doorBlockWithRenderType(((DoorBlock) MCSNBlocks.TOWN_FAIR_DOOR_C.get()), modLoc("block/town_fair_door_c/town_fair_door_c_bottom"), modLoc("block/town_fair_door_c/town_fair_door_c_top"), "cutout");
        doorBlockWithRenderType(((DoorBlock) MCSNBlocks.TOWN_FAIR_DOOR_D.get()), modLoc("block/town_fair_door_d/town_fair_door_d_bottom"), modLoc("block/town_fair_door_d/town_fair_door_d_top"), "cutout");
        doorBlockWithRenderType(((DoorBlock) MCSNBlocks.TOWN_FAIR_DOOR_E.get()), modLoc("block/town_fair_door_e/town_fair_door_e_bottom"), modLoc("block/town_fair_door_e/town_fair_door_e_top"), "cutout");
        doorBlockWithRenderType(((DoorBlock) MCSNBlocks.TOWN_FAIR_DOOR_F.get()), modLoc("block/town_fair_door_f/town_fair_door_f_bottom"), modLoc("block/town_fair_door_f/town_fair_door_f_top"), "cutout");
        doorBlockWithRenderType(((DoorBlock) MCSNBlocks.TOWN_FAIR_DOOR_G.get()), modLoc("block/town_fair_door_g/town_fair_door_g_bottom"), modLoc("block/town_fair_door_g/town_fair_door_g_top"), "cutout");
        doorBlockWithRenderType(((DoorBlock) MCSNBlocks.TOWN_FAIR_DOOR_H.get()), modLoc("block/town_fair_door_h/town_fair_door_h_bottom"), modLoc("block/town_fair_door_h/town_fair_door_h_top"), "cutout");
        doorBlockWithRenderType(((DoorBlock) MCSNBlocks.TOWN_FAIR_DOOR_I.get()), modLoc("block/town_fair_door_i/town_fair_door_i_bottom"), modLoc("block/town_fair_door_i/town_fair_door_i_top"), "cutout");
        doorBlockWithRenderType(((DoorBlock) MCSNBlocks.TOWN_FAIR_DOOR_J.get()), modLoc("block/town_fair_door_j/town_fair_door_j_bottom"), modLoc("block/town_fair_door_j/town_fair_door_j_top"), "cutout");

    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
