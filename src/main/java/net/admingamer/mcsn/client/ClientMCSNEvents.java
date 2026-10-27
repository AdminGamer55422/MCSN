package net.admingamer.mcsn.client;

import net.admingamer.mcsn.block.MCSNBlocks;
import net.admingamer.mcsn.blockentity.RGBBlockEntity;
import net.admingamer.mcsn.menu.MCSNMenus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = "mcsn", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientMCSNEvents {

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(MCSNMenus.CALCULATOR.get(), CalculatorScreen::new);
        });
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) -> {
                    if (level != null && pos != null) {
                        var be = level.getBlockEntity(pos);
                        if (be instanceof RGBBlockEntity rgbBE) {
                            return rgbBE.getColor();
                        }
                    }
                    return 0xffffff;
                },
                MCSNBlocks.CLOTH.get(),
                MCSNBlocks.DYABLE_PLANKS.get(),
                MCSNBlocks.DYABLE_GLASS.get(),
                MCSNBlocks.DYABLE_CONCRETE.get(),
                MCSNBlocks.DYABLE_GLOWSTONE.get()
        );
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
                    net.minecraft.nbt.CompoundTag blockEntityTag = stack.getTagElement("BlockEntityTag");
                    if (blockEntityTag != null && blockEntityTag.contains("Color")) {
                        return blockEntityTag.getInt("Color");
                    }
                    return 0xffffff;
                },
                MCSNBlocks.CLOTH.get(),
                MCSNBlocks.DYABLE_PLANKS.get(),
                MCSNBlocks.DYABLE_GLASS.get(),
                MCSNBlocks.DYABLE_CONCRETE.get(),
                MCSNBlocks.DYABLE_GLOWSTONE.get()
        );
    }
}
