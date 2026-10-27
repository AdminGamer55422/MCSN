package net.admingamer.mcsn.mcsnactionchat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.sounds.SoundEvents;
import java.util.LinkedList;
import java.util.Queue;

public class DialogueQueueManager {

    private static class DialogueLine {
        String speaker;
        String text;
        int color;
        int durationTicks;

        public DialogueLine(String speaker, String text, int color, int durationSeconds) {
            this.speaker = speaker;
            this.text = text;
            this.color = color;
            this.durationTicks = durationSeconds * 20;
        }
    }

    private static final Queue<DialogueLine> QUEUE = new LinkedList<>();
    private static int activeLineTicksLeft = 0;

    public static void queueLine(String speaker, String text, int color, int durationSeconds) {
        QUEUE.add(new DialogueLine(speaker, text, color, durationSeconds));
    }

    public static void clientTick() {
        if (activeLineTicksLeft > 0) {
            activeLineTicksLeft--;
            return;
        }

        if (!QUEUE.isEmpty()) {
            DialogueLine line = QUEUE.poll();

            MutableComponent header = Component.literal("<" + line.speaker + "> ")
                    .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(line.color)));
            MutableComponent body = Component.literal(line.text)
                    .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xffffff)));

            PlayerManager.addCustomMessage(header.append(body), line.durationTicks);
            activeLineTicksLeft = line.durationTicks;

            // Dialogue notification blip cue sound execution
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                mc.getSoundManager().play(SimpleSoundInstance.forUI(
                        SoundEvents.UI_BUTTON_CLICK.get(), 1.4f, 0.3f
                ));
            }
        }
    }
}
