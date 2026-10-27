package net.admingamer.mcsn.client;

import net.admingamer.mcsn.MCSN;
import net.admingamer.mcsn.network.MCSNMessages;
import net.admingamer.mcsn.network.UpdateLaserColorPacket;
import net.admingamer.mcsn.network.UpdateLaserColorPacket;
import net.admingamer.mcsn.network.MCSNMessages; // Replace with your packet registration class
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class LaserColorScreen extends Screen {
    private EditBox fromR, fromG, fromB;
    private EditBox toR, toG, toB;

    public LaserColorScreen() {
        super(Component.literal("Laser Color Configuration"));
    }

    @Override
    protected void init() {
        int x = this.width / 2;
        int y = this.height / 2;

        this.fromR = new EditBox(this.font, x - 110, y - 50, 60, 20, Component.literal("From R"));
        this.fromR.setValue("255");
        this.fromG = new EditBox(this.font, x - 40, y - 50, 60, 20, Component.literal("From G"));
        this.fromG.setValue("0");
        this.fromB = new EditBox(this.font, x + 30, y - 50, 60, 20, Component.literal("From B"));
        this.fromB.setValue("0");

        this.toR = new EditBox(this.font, x - 110, y, 60, 20, Component.literal("To R"));
        this.toR.setValue("0");
        this.toG = new EditBox(this.font, x - 40, y, 60, 20, Component.literal("To G"));
        this.toG.setValue("0");
        this.toB = new EditBox(this.font, x + 30, y, 60, 20, Component.literal("To B"));
        this.toB.setValue("255");

        this.addRenderableWidget(fromR);
        this.addRenderableWidget(fromG);
        this.addRenderableWidget(fromB);
        this.addRenderableWidget(toR);
        this.addRenderableWidget(toG);
        this.addRenderableWidget(toB);

        this.addRenderableWidget(Button.builder(Component.literal("Apply"), button -> {
            int[] fromArray = new int[]{ parse(fromR.getValue()), parse(fromG.getValue()), parse(fromB.getValue()) };
            int[] toArray = new int[]{ parse(toR.getValue()), parse(toG.getValue()), parse(toB.getValue()) };

            MCSNMessages.sendToServer(new UpdateLaserColorPacket(fromArray, toArray));
            this.onClose();
        }).bounds(x - 60, y + 40, 120, 20).build());
    }

    private int parse(String val) {
        try {
            return Math.max(0, Math.min(255, Integer.parseInt(val)));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);

        int x = this.width / 2;
        int y = this.height / 2;

        graphics.drawCenteredString(this.font, "Start Color (RGB)", x, y - 65, 0xffffff);
        graphics.drawCenteredString(this.font, "End Color (RGB)", x, y - 15, 0xffffff);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
