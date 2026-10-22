package net.admingamer.mcsn.blockentity;

import net.admingamer.mcsn.block.MCSNBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MCSNBlockEntities {

    // 1. Create the Deferred Register for Block Entity Types under your "mcsn" mod ID namespace
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "mcsn");

    // 2. Cleanly register the RGB Block Entity and map it to all valid dyable story blocks
    public static final RegistryObject<BlockEntityType<RGBBlockEntity>> RGB_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("rgb_block_entity", () ->
                    BlockEntityType.Builder.of(RGBBlockEntity::new,
                            MCSNBlocks.CLOTH.get(),
                            MCSNBlocks.DYABLE_PLANKS.get(),
                            MCSNBlocks.DYABLE_GLASS.get(),
                            MCSNBlocks.DYABLE_CONCRETE.get(),
                            MCSNBlocks.DYABLE_GLOWSTONE.get()
                    ).build(null) // Replaces .m_58966_((Type)null)
            );

    // 3. Main register hook called from your primary mod class setup
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
