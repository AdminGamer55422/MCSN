package net.admingamer.mcsn.datagen.loot;


import net.admingamer.mcsn.block.MCSNBlocks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;


import java.util.Set;


public class MCSNBlockLootTables extends BlockLootSubProvider {
    public MCSNBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }


    /** Blocks:
     * <blockquote><pre>{@code
     * this.dropSelf(MCSNBlocks.GLOWING_OBSIDIAN.get());
     * this.add(MCSNBlocks.INFINITY_ORE.get(),
     *                 block -> createRedstoneLikeOreDrops(MCSNBlocks.INFINITY_ORE.get(), MCSNItems.INFINITY_CRYSTAL.get()));
     * }</pre></blockquote>
     *
     * Doors and Slabs:
     * <blockquote><pre>{@code
     * this.add(MCSNBlocks.SNOWY_VILLAGE_SHINGLES.get(),
     *     block -> createSlabItemTable(MCSNBlocks.SNOWY_VILLAGE_SHINGLES.get()));
     * this.add(MCSNBlocks.TOWN_FAIR_DOOR_B.get(),
     *     block -> createDoorTable(MCSNBlocks.TOWN_FAIR_DOOR_B.get()));
     * }</pre></blockquote>
     */


    @Override
    protected void generate() {
        this.dropSelf(MCSNBlocks.ASPHALT_A.get());
        this.dropSelf(MCSNBlocks.BLACK_WITHERSTORM_FLESH_BLOCK.get());
        this.dropSelf(MCSNBlocks.BLUE_DIORITE.get());
        this.dropSelf(MCSNBlocks.CLOTH.get());
        this.dropSelf(MCSNBlocks.CREEPY_MANSION_KITCHEN_BRICKS.get());
        this.dropSelf(MCSNBlocks.DARK_DIORITE.get());
        this.dropSelf(MCSNBlocks.DARK_PURPLE_WITHERSTORM_FLESH_BLOCK.get());
        this.dropSelf(MCSNBlocks.DYABLE_CONCRETE.get());
        this.dropSelf(MCSNBlocks.DYABLE_GLASS.get());
        this.dropSelf(MCSNBlocks.DYABLE_GLOWSTONE.get());
        this.dropSelf(MCSNBlocks.DYABLE_PLANKS.get());
        this.dropSelf(MCSNBlocks.GLOWING_OBSIDIAN.get());
        this.dropSelf(MCSNBlocks.GRAY_GRANITE.get());
        this.dropSelf(MCSNBlocks.MIRROR_BLOCK.get());
        this.dropSelf(MCSNBlocks.ORANGE_GLOWSTONE.get());
        this.dropSelf(MCSNBlocks.PURPLE_WITHERSTORM_FLESH_BLOCK.get());
        this.dropSelf(MCSNBlocks.RED_GLOWSTONE.get());
        this.dropSelf(MCSNBlocks.REDSTONE_HEART.get());
        this.dropSelf(MCSNBlocks.SKYLAND_COBBLESTONE.get());
        this.dropSelf(MCSNBlocks.TOWN_FAIR_BRICKS_B.get());
        this.dropSelf(MCSNBlocks.TOWN_FAIR_BRICKS_C.get());
        this.dropSelf(MCSNBlocks.TOWN_FAIR_BRICKS_D.get());
        this.dropSelf(MCSNBlocks.TOWN_FAIR_BRICKS_E.get());
        this.dropSelf(MCSNBlocks.TOWN_FAIR_BUILDING_A_COBBLESTONE.get());
        this.dropSelf(MCSNBlocks.WITHERED_COBBLESTONE.get());
        this.dropSelf(MCSNBlocks.WITHERSTORM_BRICKS.get());
        this.dropSelf(MCSNBlocks.WITHERSTORM_RIBS.get());

        this.add(MCSNBlocks.TOWN_FAIR_DOOR_A.get(),
                block -> createDoorTable(MCSNBlocks.TOWN_FAIR_DOOR_A.get()));
        this.add(MCSNBlocks.TOWN_FAIR_DOOR_B.get(),
                block -> createDoorTable(MCSNBlocks.TOWN_FAIR_DOOR_B.get()));
        this.add(MCSNBlocks.TOWN_FAIR_DOOR_C.get(),
                block -> createDoorTable(MCSNBlocks.TOWN_FAIR_DOOR_C.get()));
        this.add(MCSNBlocks.TOWN_FAIR_DOOR_D.get(),
                block -> createDoorTable(MCSNBlocks.TOWN_FAIR_DOOR_D.get()));
        this.add(MCSNBlocks.TOWN_FAIR_DOOR_E.get(),
                block -> createDoorTable(MCSNBlocks.TOWN_FAIR_DOOR_E.get()));
        this.add(MCSNBlocks.TOWN_FAIR_DOOR_F.get(),
                block -> createDoorTable(MCSNBlocks.TOWN_FAIR_DOOR_F.get()));
        this.add(MCSNBlocks.TOWN_FAIR_DOOR_G.get(),
                block -> createDoorTable(MCSNBlocks.TOWN_FAIR_DOOR_G.get()));
        this.add(MCSNBlocks.TOWN_FAIR_DOOR_H.get(),
                block -> createDoorTable(MCSNBlocks.TOWN_FAIR_DOOR_H.get()));
        this.add(MCSNBlocks.TOWN_FAIR_DOOR_I.get(),
                block -> createDoorTable(MCSNBlocks.TOWN_FAIR_DOOR_I.get()));
        this.add(MCSNBlocks.TOWN_FAIR_DOOR_J.get(),
                block -> createDoorTable(MCSNBlocks.TOWN_FAIR_DOOR_J.get()));
    }


    protected LootTable.Builder createRedstoneLikeOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock,
                LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0f, 5.0f)))
                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))));
    }


    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return MCSNBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
