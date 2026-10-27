package net.admingamer.mcsn.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.admingamer.mcsn.item.LaserGunItem;
import net.admingamer.mcsn.mcsnactionchat.CustomChatScreen;
import net.admingamer.mcsn.mcsnactionchat.DialogueQueueManager;
import net.admingamer.mcsn.mcsnactionchat.PlayerConfigScreen;
import net.admingamer.mcsn.mcsnactionchat.PlayerManager;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = "mcsn", value = Dist.CLIENT)
public class MCSNKeyBindings {
    public static final KeyMapping LASER_MENU_KEY = new KeyMapping(
            "key.mcsn.laser_menu",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_I,
            "key.categories.mcsn"
    );

    public static final KeyMapping OPEN_CUSTOM_CHAT = new KeyMapping(
            "key.mcsmactionchat.open_chat", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Y, "key.categories.multiplayer"
    );
    public static final KeyMapping OPEN_FRIEND_CONFIG = new KeyMapping(
            "key.mcsmactionchat.open_config", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_J, "key.categories.multiplayer"
    );

    @Mod.EventBusSubscriber(modid = "mcsn", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void registerKeys(RegisterKeyMappingsEvent event) {
            event.register(LASER_MENU_KEY);
            event.register(OPEN_CUSTOM_CHAT);
            event.register(OPEN_FRIEND_CONFIG);
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            // 1. Tick down background text fading and dialogue progression systems
            PlayerManager.tickHudMessages();
            DialogueQueueManager.clientTick();

            Minecraft mc = Minecraft.getInstance();
            // Secure checks to ensure inputs only evaluate while actively playing in-world
            if (mc.level != null && mc.player != null && mc.screen == null) {

                // Original Laser Weapon Menu click listener
                if (LASER_MENU_KEY.consumeClick()) {
                    ItemStack mainHand = mc.player.getMainHandItem();
                    if (mainHand.getItem() instanceof LaserGunItem) {
                        mc.setScreen(new net.admingamer.mcsn.client.LaserColorScreen());
                    }
                }

                // FIXED: Added missing active click listeners for your custom chat keys!
                while (OPEN_CUSTOM_CHAT.consumeClick()) {
                    mc.setScreen(new CustomChatScreen(""));
                }

                while (OPEN_FRIEND_CONFIG.consumeClick()) {
                    mc.setScreen(new PlayerConfigScreen());
                }
            }
        }
    }
}
