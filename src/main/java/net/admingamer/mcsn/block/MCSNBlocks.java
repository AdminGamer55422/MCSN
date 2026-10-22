package net.admingamer.mcsn.block;

import net.admingamer.mcsn.MCSN;
import net.admingamer.mcsn.item.MCSNItems;
import net.admingamer.mcsn.blockentity.RGBBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class MCSNBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MCSN.MOD_ID);

    public static final RegistryObject<Block> BLACK_WITHERSTORM_FLESH_BLOCK = registerBlock("black_witherstorm_flesh_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)
                    .mapColor(MapColor.SNOW)
                    .sound(SoundType.SLIME_BLOCK)));

    public static final RegistryObject<Block> DARK_PURPLE_WITHERSTORM_FLESH_BLOCK = registerBlock("dark_purple_witherstorm_flesh_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)
                    .mapColor(MapColor.SNOW)
                    .sound(SoundType.SLIME_BLOCK)));

    public static final RegistryObject<Block> PURPLE_WITHERSTORM_FLESH_BLOCK = registerBlock("purple_witherstorm_flesh_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)
                    .mapColor(MapColor.SNOW)
                    .sound(SoundType.SLIME_BLOCK)));

    public static final RegistryObject<Block> WITHERSTORM_RIBS = registerBlock("witherstorm_ribs",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(2.5f)
                    .sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> ASPHALT_A = registerBlock("asphalt_a",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .requiresCorrectToolForDrops()
                    .mapColor(MapColor.COLOR_GRAY)));

    public static final RegistryObject<Block> CREEPY_MANSION_KITCHEN_BRICKS = registerBlock("creepy_mansion_kitchen_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS)
                    .requiresCorrectToolForDrops()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final RegistryObject<Block> TOWN_FAIR_BRICKS_B = registerBlock("town_fair_bricks_b",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS)
                    .requiresCorrectToolForDrops()
                    .mapColor(MapColor.TERRACOTTA_WHITE)));

    public static final RegistryObject<Block> TOWN_FAIR_BRICKS_C = registerBlock("town_fair_bricks_c",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS)
                    .requiresCorrectToolForDrops()
                    .mapColor(MapColor.TERRACOTTA_ORANGE)));

    public static final RegistryObject<Block> TOWN_FAIR_BRICKS_D = registerBlock("town_fair_bricks_d",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS)
                    .requiresCorrectToolForDrops()
                    .mapColor(MapColor.SAND)));

    public static final RegistryObject<Block> TOWN_FAIR_BRICKS_E = registerBlock("town_fair_bricks_e",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS)
                    .requiresCorrectToolForDrops()
                    .mapColor(MapColor.RAW_IRON)));

    public static final RegistryObject<Block> WITHERSTORM_BRICKS = registerBlock("witherstorm_bricks",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SNOW)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .strength(2.5f, 6.0f)
                    .sound(SoundType.NETHER_BRICKS)));

    public static final RegistryObject<Block> BLUE_DIORITE = registerBlock("blue_diorite",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIORITE)
                    .mapColor(MapColor.COLOR_BLUE)));

    public static final RegistryObject<Block> DARK_DIORITE = registerBlock("dark_diorite",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIORITE)
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final RegistryObject<Block> GRAY_GRANITE = registerBlock("gray_granite",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GRANITE)
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final RegistryObject<Block> SKYLAND_COBBLESTONE = registerBlock("skyland_cobblestone",
            () -> new SkylandCobblestoneBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)
                    .mapColor(MapColor.COLOR_GRAY)));

    public static final RegistryObject<Block> TOWN_FAIR_BUILDING_A_COBBLESTONE = registerBlock("town_fair_building_a_cobblestone",
            () -> new SkylandCobblestoneBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)
                    .mapColor(MapColor.TERRACOTTA_WHITE)));

    public static final RegistryObject<Block> WITHERED_COBBLESTONE = registerBlock("withered_cobblestone_redstone_lines",
            () -> new WitheredCobblestoneRedstoneLinesBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));

    public static final RegistryObject<Block> CLOTH = registerBlock("cloth",
            () -> new DyableRGBBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SNOW)
                    .sound(SoundType.WOOL)
                    .strength(0.8f)
                    .ignitedByLava().instrument(NoteBlockInstrument.GUITAR)));

    public static final RegistryObject<Block> ORANGE_GLOWSTONE = registerBlock("orange_glowstone",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .instrument(NoteBlockInstrument.PLING)
                    .strength(0.3f)
                    .sound(SoundType.GLASS)
                    .lightLevel((state) -> 15)));

    public static final RegistryObject<Block> RED_GLOWSTONE = registerBlock("red_glowstone",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.CRIMSON_NYLIUM)
                    .instrument(NoteBlockInstrument.PLING)
                    .strength(0.3f)
                    .sound(SoundType.GLASS)
                    .lightLevel((state) -> 15)));

    public static final RegistryObject<Block> TOWN_FAIR_DOOR_A = registerBlock("town_fair_door_a",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), BlockSetType.OAK));

    public static final RegistryObject<Block> TOWN_FAIR_DOOR_B = registerBlock("town_fair_door_b",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), BlockSetType.OAK));

    public static final RegistryObject<Block> TOWN_FAIR_DOOR_C = registerBlock("town_fair_door_c",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), BlockSetType.OAK));

    public static final RegistryObject<Block> TOWN_FAIR_DOOR_D = registerBlock("town_fair_door_d",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), BlockSetType.OAK));

    public static final RegistryObject<Block> TOWN_FAIR_DOOR_E = registerBlock("town_fair_door_e",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), BlockSetType.OAK));

    public static final RegistryObject<Block> TOWN_FAIR_DOOR_F = registerBlock("town_fair_door_f",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), BlockSetType.OAK));

    public static final RegistryObject<Block> TOWN_FAIR_DOOR_G = registerBlock("town_fair_door_g",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), BlockSetType.OAK));

    public static final RegistryObject<Block> TOWN_FAIR_DOOR_H = registerBlock("town_fair_door_h",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), BlockSetType.OAK));

    public static final RegistryObject<Block> TOWN_FAIR_DOOR_I = registerBlock("town_fair_door_i",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), BlockSetType.OAK));

    public static final RegistryObject<Block> TOWN_FAIR_DOOR_J = registerBlock("town_fair_door_j",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), BlockSetType.OAK));

    public static final RegistryObject<Block> DYABLE_PLANKS = registerBlock("dyable_planks",
            () -> new DyableRGBBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .sound(SoundType.WOOD)
                    .strength(2.0f, 3.0f)));

    public static final RegistryObject<Block> DYABLE_GLASS = registerBlock("dyable_glass",
            () -> new DyableRGBBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.NONE)
                    .sound(SoundType.GLASS)
                    .strength(0.3f)
                    .noOcclusion()));

    public static final RegistryObject<Block> DYABLE_CONCRETE = registerBlock("dyable_concrete",
            () -> new DyableRGBBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .sound(SoundType.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(1.8f)));

    public static final RegistryObject<Block> DYABLE_GLOWSTONE = registerBlock("dyable_glowstone",
            () -> new DyableRGBBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SAND)
                    .sound(SoundType.GLASS)
                    .lightLevel((state) -> 15)
                    .strength(0.3f)
                    .instrument(NoteBlockInstrument.PLING)));

    public static final RegistryObject<Block> GLOWING_OBSIDIAN = registerBlock("glowing_obsidian",
            () -> new GlowingObsidianBlock(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN)
                    .mapColor(MapColor.NETHER)));

    public static final RegistryObject<Block> REDSTONE_HEART = registerBlock("redstone_heart",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.FIRE)
                    .sound(SoundType.GLASS)
                    .strength(0.4f)
                    .lightLevel((state) -> 15)));

    public static final RegistryObject<Block> MIRROR_BLOCK = registerBlock("mirror_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GLASS)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .mapColor(MapColor.SNOW)
                    .noOcclusion()));

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return MCSNItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    public static class DyableRGBBlock extends Block implements EntityBlock {
        public DyableRGBBlock(Properties properties) {
            super(properties);
        }

        @Nullable
        @Override
        public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
            return new RGBBlockEntity(pos, state);
        }
    }
}
