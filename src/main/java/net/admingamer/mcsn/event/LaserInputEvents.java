package net.admingamer.mcsn.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "mcsn", value = Dist.CLIENT)
public class LaserInputEvents {

    @SubscribeEvent
    public static void onSwapToOffhand(PlayerInteractEvent.RightClickItem event) {
    }

    @SubscribeEvent
    public static void onPlayerSwapItems(net.minecraftforge.event.TickEvent.PlayerTickEvent event) {
    }
}
