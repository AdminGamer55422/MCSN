package net.admingamer.mcsn;

import net.admingamer.mcsn.client.MCSNKeyBindings;
import net.admingamer.mcsn.mcsnactionchat.CustomHudRenderer;
import com.mojang.logging.LogUtils;
import net.admingamer.mcsn.block.MCSNBlocks;
import net.admingamer.mcsn.blockentity.MCSNBlockEntities;
import net.admingamer.mcsn.item.MCSNCreativeModeTabs;
import net.admingamer.mcsn.item.MCSNItems;
import net.admingamer.mcsn.mcsnactionchat.ChatConfigSaver;
import net.admingamer.mcsn.menu.MCSNMenus;
import net.admingamer.mcsn.network.MCSNMessages;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(MCSN.MOD_ID)
public class MCSN {
    public static final String MOD_ID = "mcsn";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MCSN(FMLJavaModLoadingContext context) {
        IEventBus mcsnEventBus = context.getModEventBus();

        MCSNCreativeModeTabs.register(mcsnEventBus);
        MCSNItems.register(mcsnEventBus);
        MCSNBlocks.register(mcsnEventBus);

        MCSNBlockEntities.register(mcsnEventBus);

        mcsnEventBus.addListener(this::commonSetup);

        mcsnEventBus.addListener(this::clientSetup);

        MCSNMenus.register(mcsnEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        mcsnEventBus.addListener(this::addCreative);

        mcsnEventBus.addListener(net.admingamer.mcsn.client.ClientMCSNEvents::registerBlockColors);
        mcsnEventBus.addListener(net.admingamer.mcsn.client.ClientMCSNEvents::registerItemColors);


        MinecraftForge.EVENT_BUS.register(MCSNKeyBindings.class);
        MinecraftForge.EVENT_BUS.register(CustomHudRenderer.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(MCSNMessages::register);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {}

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    private void clientSetup(final FMLClientSetupEvent event) {
        ChatConfigSaver.loadConfig();
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
