package net.admingamer.mcsn.client;

import net.admingamer.mcsn.network.MCSNMessages;
import net.admingamer.mcsn.network.SaveRGBPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class RGBScreen extends Screen {
    private final BlockPos blockPos;
    private final int initialColor;

    private EditBox rInput;
    private EditBox gInput;
    private EditBox bInput;

    public RGBScreen(BlockPos pos, int initialColor) {
        super(Component.literal("Paintbrush RGB Selector"));
        this.blockPos = pos;
        this.initialColor = initialColor;
    }

    @Override
    protected void init() {
        super.init();

        int initR = (initialColor >> 16) & 0xff;
        int initG = (initialColor >> 8) & 0xff;
        int initB = initialColor & 0xff;

        int xCenter = this.width / 2;
        int yCenter = this.height / 2;

        this.rInput = new EditBox(this.font, xCenter - 50, yCenter - 45, 100, 20, Component.literal("R"));
        this.rInput.setMaxLength(3);
        this.rInput.setValue(String.valueOf(initR));
        this.rInput.setFilter(string -> string.matches("\\d*"));
        this.addRenderableWidget(this.rInput);

        this.gInput = new EditBox(this.font, xCenter - 50, yCenter - 15, 100, 20, Component.literal("G"));
        this.gInput.setMaxLength(3);
        this.gInput.setValue(String.valueOf(initG));
        this.gInput.setFilter(string -> string.matches("\\d*"));
        this.addRenderableWidget(this.gInput);

        this.bInput = new EditBox(this.font, xCenter - 50, yCenter + 15, 100, 20, Component.literal("B"));
        this.bInput.setMaxLength(3);
        this.bInput.setValue(String.valueOf(initB));
        this.bInput.setFilter(string -> string.matches("\\d*"));
        this.addRenderableWidget(this.bInput);

        this.addRenderableWidget(Button.builder(Component.literal("Apply"), button -> {
            int r = parseColor(rInput.getValue());
            int g = parseColor(gInput.getValue());
            int b = parseColor(bInput.getValue());

            int finalRGB = (r << 16) | (g << 8) | b;

            MCSNMessages.sendToServer(new SaveRGBPacket(this.blockPos, finalRGB));
            this.onClose();
        }).bounds(xCenter - 50, yCenter + 45, 100, 20).build());
    }

    private int parseColor(String val) {
        try {
            int parsed = Integer.parseInt(val);
            return Math.max(0, Math.min(255, parsed));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        int xCenter = this.width / 2;
        int yCenter = this.height / 2;

        guiGraphics.drawString(this.font, "R:", xCenter - 65, yCenter - 39, 0xff0000);
        guiGraphics.drawString(this.font, "G:", xCenter - 65, yCenter - 9, 0x00ff00);
        guiGraphics.drawString(this.font, "B:", xCenter - 65, yCenter + 21, 0x0000ff);
        guiGraphics.drawCenteredString(this.font, this.title, xCenter, yCenter - 65, 0xffffff);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
