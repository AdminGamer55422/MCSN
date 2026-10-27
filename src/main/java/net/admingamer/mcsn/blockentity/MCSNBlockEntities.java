package net.admingamer.mcsn.blockentity;

import net.admingamer.mcsn.block.MCSNBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MCSNBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "mcsn");

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

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
