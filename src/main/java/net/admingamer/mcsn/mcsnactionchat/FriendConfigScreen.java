package net.admingamer.mcsn.mcsnactionchat;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class FriendConfigScreen extends Screen {
    private EditBox realNameBox;
    private EditBox aliasBox;
    private EditBox hexColorBox;
    private EditBox rBox, gBox, bBox;

    private boolean isUpdating = false;

    public FriendConfigScreen() {
        super(Component.literal("Chat Profile Customizer"));
    }

    @Override
    protected void init() {
        int startX = this.width / 2 - 100;

        // FIXED: Swapped addWidget() for addRenderableWidget() so they can be clicked and focused!
        this.realNameBox = this.addRenderableWidget(new EditBox(this.font, startX, 40, 200, 16, Component.literal("Real Name")));
        this.realNameBox.setHint(Component.literal("Username"));

        this.aliasBox = this.addRenderableWidget(new EditBox(this.font, startX, 75, 200, 16, Component.literal("Alias")));
        this.aliasBox.setHint(Component.literal("Nickname"));

        this.hexColorBox = this.addRenderableWidget(new EditBox(this.font, startX + 25, 110, 175, 16, Component.literal("Hex Color")));
        this.hexColorBox.setValue("#00ffcc");

        int rgbW = 60;
        this.rBox = this.addRenderableWidget(new EditBox(this.font, startX, 145, rgbW, 16, Component.literal("R")));
        this.gBox = this.addRenderableWidget(new EditBox(this.font, startX + 70, 145, rgbW, 16, Component.literal("G")));
        this.bBox = this.addRenderableWidget(new EditBox(this.font, startX + 140, 145, rgbW, 16, Component.literal("B")));

        this.rBox.setValue("0");
        this.gBox.setValue("255");
        this.bBox.setValue("204");

        // --- RESPONDERS TO AUTO-UPDATE & SYNC COLOR LIVE AS YOU TYPE ---
        this.hexColorBox.setResponder(text -> {
            if (isUpdating) return;
            isUpdating = true;
            int color = FriendManager.hexToRgb(text.trim());
            this.rBox.setValue(String.valueOf((color >> 16) & 0xff));
            this.gBox.setValue(String.valueOf((color >> 8) & 0xff));
            this.bBox.setValue(String.valueOf(color & 0xff));
            isUpdating = false;
        });

        java.util.function.Consumer<String> rgbResponder = text -> {
            if (isUpdating) return;
            isUpdating = true;
            try {
                int r = Math.min(255, Math.max(0, Integer.parseInt(rBox.getValue().trim())));
                int g = Math.min(255, Math.max(0, Integer.parseInt(gBox.getValue().trim())));
                int b = Math.min(255, Math.max(0, Integer.parseInt(bBox.getValue().trim())));
                String hex = String.format("#%02x%02x%02x", r, g, b);
                this.hexColorBox.setValue(hex);
            } catch (NumberFormatException ignored) {}
            isUpdating = false;
        };

        this.rBox.setResponder(rgbResponder);
        this.gBox.setResponder(rgbResponder);
        this.bBox.setResponder(rgbResponder);

        // Reset Button
        this.addRenderableWidget(Button.builder(Component.literal("Reset Color"), (btn) -> {
            this.hexColorBox.setValue("#ffffff");
        }).bounds(startX, 172, 95, 16).build());

        // Save Button
        this.addRenderableWidget(Button.builder(Component.literal("Save Settings"), (btn) -> {
            String real = this.realNameBox.getValue().trim();
            String alias = this.aliasBox.getValue().trim();
            if (!real.isEmpty() && !alias.isEmpty()) {
                FriendManager.addOrUpdateFriend(real, alias, parseChosenColor());
                ChatConfigSaver.saveConfig();
                this.onClose();
            }
        }).bounds(startX + 105, 172, 95, 16).build());
    }

    private int parseChosenColor() {
        try {
            String hex = hexColorBox.getValue().trim();
            return FriendManager.hexToRgb(hex);
        } catch (Exception e) {
            return 0xffffff;
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);

        // REMOVED redundancy: .render() calls on fields are handled automatically by addRenderableWidget!

        int previewColor = parseChosenColor();
        int sqX1 = this.width / 2 - 100;
        int sqY1 = 110;

        // Render live square block updates
        graphics.fill(sqX1 - 1, sqY1 - 1, sqX1 + 17, sqY1 + 17, 0xff444444);
        graphics.fill(sqX1, sqY1, sqX1 + 16, sqY1 + 16, 0xff000000 | previewColor);

        String previewName = aliasBox.getValue().isEmpty() ? "Name" : aliasBox.getValue();
        graphics.drawString(this.font, "HUD Preview Window:", this.width / 2 - 100, this.height - 50, 0xaaaaaa, false);
        graphics.drawString(this.font, "<" + previewName + "> ", this.width / 2 - 100, this.height - 35, previewColor, true);
        graphics.drawString(this.font, "Hello!", this.width / 2 - 100 + this.font.width("<" + previewName + "> "), this.height - 35, 0xffffff, true);

        super.render(graphics, mouseX, mouseY, partialTick);
    }
}
