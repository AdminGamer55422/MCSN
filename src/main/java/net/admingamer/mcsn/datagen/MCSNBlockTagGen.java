package net.admingamer.mcsn.datagen;

import net.admingamer.mcsn.MCSN;
import net.admingamer.mcsn.block.MCSNBlocks;
import net.admingamer.mcsn.util.MCSNTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MCSNBlockTagGen extends BlockTagsProvider {
    public MCSNBlockTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MCSN.MOD_ID, existingFileHelper);
    }

    /** Necessary Tags:
     * <blockquote><pre>{@code
     * this.tag(BlockTags.FENCES)
     *                 .add(MCSNBlocks.DYABLE_WOODEN_FENCE.get());
     *         this.tag(BlockTags.FENCE_GATES)
     *                 .add(MCSNBlocks.DYABLE_WOODEN_FENCE_GATE.get());
     *         this.tag(BlockTags.WALLS)
     *                 .add(MCSNBlocks.IRON_WALL.get());
     *                 }</pre></blockquote>
     */

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        this.tag(MCSNTags.Blocks.DIAMOND_ORE_VARIANTS);

        this.tag(BlockTags.NEEDS_STONE_TOOL);

        this.tag(BlockTags.NEEDS_IRON_TOOL);

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL);

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE);

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL);

        this.tag(BlockTags.MINEABLE_WITH_HOE);

        this.tag(BlockTags.MINEABLE_WITH_AXE);
    }
}
