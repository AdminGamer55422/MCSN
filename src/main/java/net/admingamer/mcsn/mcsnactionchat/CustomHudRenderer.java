package net.admingamer.mcsn.mcsnactionchat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.List;

@Mod.EventBusSubscriber(modid = "mcsnactionchat", value = Dist.CLIENT)
public class CustomHudRenderer {

    @SubscribeEvent
    public static void onRenderOverlayPost(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay().id().equals(VanillaGuiOverlay.HOTBAR.id())) {
            Minecraft mc = Minecraft.getInstance();
            GuiGraphics graphics = event.getGuiGraphics();

            List<FriendManager.ActiveMessage> messages = FriendManager.getVisibleMessages();
            int screenW = event.getWindow().getGuiScaledWidth();
            int screenH = event.getWindow().getGuiScaledHeight();

            int y = screenH - 65;

            for (int i = messages.size() - 1; i >= 0; i--) {
                FriendManager.ActiveMessage msg = messages.get(i);
                int textW = mc.font.width(msg.text);
                int x = (screenW - textW) / 2;

                int alpha = 255;
                if (msg.ticksLeft < 20) {
                    alpha = (int) ((msg.ticksLeft / 20f) * 255);
                }
                int color = (alpha << 24) | 0xffffff;

                graphics.fill(x - 4, y - 2, x + textW + 4, y + 9, (alpha / 2 << 24));
                graphics.drawString(mc.font, msg.text, x, y, color, true);

                y -= 12;
            }
        }
    }
}
