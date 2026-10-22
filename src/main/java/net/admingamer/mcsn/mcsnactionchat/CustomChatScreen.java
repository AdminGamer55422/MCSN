package net.admingamer.mcsn.mcsnactionchat;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.lwjgl.glfw.GLFW;
import java.util.List;

public class CustomChatScreen extends Screen {
    private EditBox inputField;
    private final String initialText;
    private int scrollOffset = 0;

    public CustomChatScreen(String initialText) {
        super(Component.literal("Action Bar Chat Box Channel"));
        this.initialText = initialText;
    }

    @Override
    protected void init() {
        int boxW = 300;
        int x = (this.width - boxW) / 2;
        int y = this.height - 45; // Action bar space marker alignment

        // FIXED: Using addRenderableWidget() so clicking inside the input box focuses it perfectly
        this.inputField = this.addRenderableWidget(new EditBox(this.font, x, y, boxW, 14, Component.literal("Input Field")));
        this.inputField.setMaxLength(256);
        this.inputField.setValue(this.initialText);
        this.setInitialFocus(this.inputField);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics); // Clean dark tint behind our chat overlay interface

        List<Component> log = FriendManager.getCustomLog();
        int logX = 15;
        int logY = this.height - 65;
        int maxLines = 12;

        int linesDrawn = 0;
        for (int i = log.size() - 1 - scrollOffset; i >= 0; i--) {
            if (linesDrawn >= maxLines) break;
            graphics.drawString(this.font, log.get(i), logX, logY - (linesDrawn * 12), 0xccffffff, true);
            linesDrawn++;
        }
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.onClose();
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            String text = this.inputField.getValue().trim();
            if (!text.isEmpty()) {
                if (text.startsWith("/")) {
                    this.minecraft.player.connection.sendChat(text); // Vanilla command pass-through handler
                } else {
                    String myName = this.minecraft.player.getName().getString();
                    TextColor col = FriendManager.getFriendColor(myName);
                    String disp = FriendManager.getDisplayName(myName);

                    // 1. Style your bracket name header prefix with your custom color
                    MutableComponent formattedName = Component.literal("<" + disp + "> ")
                            .withStyle(Style.EMPTY.withColor(col));

                    // FIXED: Replaced TextColor.fromRgb(0xffffff) with 'col'
                    // This forces your entire typed chat body text to match your chosen name color!
                    MutableComponent formattedBody = Component.literal(text)
                            .withStyle(Style.EMPTY.withColor(col));

                    // Append the message body text directly onto your colored name header
                    FriendManager.addCustomMessage(formattedName.append(formattedBody), 120);
                }
            }
            this.onClose();
            return true;
        }
        return this.inputField.keyPressed(keyCode, scanCode, modifiers) || super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (delta > 0 && scrollOffset < FriendManager.getCustomLog().size() - 1) scrollOffset++;
        else if (delta < 0 && scrollOffset > 0) scrollOffset--;
        return true;
    }

    @Override
    public boolean isPauseScreen() { return false; }
}
