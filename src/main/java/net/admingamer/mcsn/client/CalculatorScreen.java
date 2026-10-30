package net.admingamer.mcsn.client;

import net.admingamer.mcsn.menu.CalculatorMenu;
import net.admingamer.mcsn.network.CalculatorPacket;
import net.admingamer.mcsn.network.MCSNMessages;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class CalculatorScreen extends AbstractContainerScreen<CalculatorMenu> {
    private EditBox inputN1;
    private EditBox inputN2;
    private EditBox inputN3;
    private MathScrollPanel mathPanel;

    private final String[] javaMathOps = {"abs", "ceil", "sign", "floor", "exp", "expm1", "hypot", "ln", "log10", "log1p", "log_b", "mod", "asin", "acos", "atan", "atan2", "random", "round", "max", "min", "²", "pow", "signum", "!", "nPr", "nCr", "gcd", "lcm", "beta", "gamma", "erf", "erfc", "log2", "scalb", "√", "∛", "1/√", "nextUp", "nextDown", "deg", "rad", "ulp", "seed"};
    private final String[] customMathOps = {"mth_sin", "mth_tan", "mth_cos", "mth_sinh", "mth_tanh", "mth_cosh", "mth_color", "mth_hsvToRgb", "mth_clamp", "mth_clampedLerp", "mth_wrap", "mth_lerp", "mth_inverseLerp", "mth_rotLerp", "mth_triangleWave", "mth_wobble", "mth_frac", "mth_acosh", "mth_asinh", "mth_atanh", "mth_csc", "mth_csch", "mth_acsc", "mth_acsch", "mth_sec", "mth_sech", "mth_asec", "mth_asech", "mth_cot", "mth_coth", "mth_acot", "mth_acoth"};

    public CalculatorScreen(CalculatorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 256;
        this.imageHeight = 220;
    }

    @Override
    protected void init() {
        super.init();
        int baseX = this.leftPos;
        int baseY = this.topPos;

        this.inputN1 = new EditBox(this.font, baseX + 15, baseY + 25, 70, 20, Component.literal("Num a"));
        this.inputN1.setValue("0");
        this.addWidget(this.inputN1);

        this.inputN2 = new EditBox(this.font, baseX + 92, baseY + 25, 70, 20, Component.literal("Num b"));
        this.inputN2.setValue("0");
        this.addWidget(this.inputN2);

        this.inputN3 = new EditBox(this.font, baseX + 169, baseY + 25, 70, 20, Component.literal("Num c"));
        this.inputN3.setValue("0");
        this.addWidget(this.inputN3);

        String[] basicOps = {"+", "-", "×", "÷", "%"};
        for (int i = 0; i < basicOps.length; i++) {
            String op = basicOps[i];

            String tooltipText = switch (op) {
                case "+" -> "Adds a and b\n\na + b";
                case "-" -> "Subtracts b from a\n\na - b";
                case "×" -> "Multiplies a and b\n\na · b";
                case "÷" -> "Divides a by b\n\na/b";
                case "%" -> "Finds a percent of b\n\na % of b";
                default -> "Basic mathematical operator.";
            };

            this.addRenderableWidget(Button.builder(Component.literal(op), b -> sendCalc(op))
                    .bounds(baseX + 15 + (i % 5 * 45), baseY + 55, 42, 20)
                    .tooltip(Tooltip.create(Component.literal(tooltipText)))
                    .build());
        }

        this.addRenderableWidget(Button.builder(Component.literal("MC"), b -> sendCalc("m_clear"))
                .bounds(baseX + 15, baseY + 77, 42, 16) // Shorter 16px high sleek utility buttons
                .tooltip(Tooltip.create(Component.literal("Memory Clear\n\nResets stored value to 0")))
                .build());

        this.addRenderableWidget(Button.builder(Component.literal("MR"), b -> sendCalc("m_recall"))
                .bounds(baseX + 60, baseY + 77, 42, 16)
                .tooltip(Tooltip.create(Component.literal("Memory Recall\n\nPrints out your currently saved memory number")))
                .build());

        this.addRenderableWidget(Button.builder(Component.literal("M+"), b -> sendCalc("m_add"))
                .bounds(baseX + 105, baseY + 77, 42, 16)
                .tooltip(Tooltip.create(Component.literal("Memory Plus\n\nAdds current value to memory tracking slot")))
                .build());

        this.addRenderableWidget(Button.builder(Component.literal("M-"), b -> sendCalc("m_sub"))
                .bounds(baseX + 150, baseY + 77, 42, 16)
                .tooltip(Tooltip.create(Component.literal("Memory Minus\n\nSubtracted current value from memory tracking slot")))
                .build());

        this.addRenderableWidget(Button.builder(Component.literal("MS"), b -> sendCalc("m_store"))
                .bounds(baseX + 195, baseY + 77, 42, 16)
                .tooltip(Tooltip.create(Component.literal("Memory Store\n\nOverwrites and sets the memory slot to the current value")))
                .build());

        this.mathPanel = new MathScrollPanel(this.minecraft, 210, 114, baseY + 96, baseX + 15);
        this.addWidget(this.mathPanel);

    }

    private void sendCalc(String operation) {
        String n1Str = this.inputN1.getValue();
        String n2Str = this.inputN2.getValue();
        String n3Str = this.inputN3.getValue();
        MCSNMessages.sendToServer(new CalculatorPacket(operation, n1Str, n2Str, n3Str));
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics graphics, int mouseX, int mouseY) {
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.fill(this.leftPos, this.topPos, this.leftPos + this.imageWidth, this.topPos + this.imageHeight, 0xff2d2d2d);
        graphics.fill(this.leftPos + 5, this.topPos + 5, this.leftPos + this.imageWidth - 5, this.topPos + this.imageHeight - 5, 0xFF151515);
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);

        this.inputN1.render(graphics, mouseX, mouseY, partialTick);
        this.inputN2.render(graphics, mouseX, mouseY, partialTick);
        this.inputN3.render(graphics, mouseX, mouseY, partialTick);

        graphics.drawString(this.font, "Basic Operators", this.leftPos + 15, this.topPos + 46, 0x909090, false);

        if (this.mathPanel != null) {
            this.mathPanel.render(graphics, mouseX, mouseY, partialTick);
        }

        this.renderTooltip(graphics, mouseX, mouseY);
    }

    public void receiveMemoryValueFromServer(String memoryValue) {
        if (this.inputN1 != null && this.inputN1.isFocused()) {
            this.inputN1.setValue(memoryValue);
        } else if (this.inputN2 != null && this.inputN2.isFocused()) {
            this.inputN2.setValue(memoryValue);
        } else if (this.inputN3 != null && this.inputN3.isFocused()) {
            this.inputN3.setValue(memoryValue);
        } else if (this.inputN1 != null) {
            this.inputN1.setValue(memoryValue);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.mathPanel != null && this.mathPanel.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private class MathScrollPanel extends net.minecraftforge.client.gui.widget.ScrollPanel {
        private final java.util.List<Button> buttons = new java.util.ArrayList<>();
        private final java.util.List<Integer> internalYOffsets = new java.util.ArrayList<>();
        private int dynamicCustomMathLabelY = 0;

        public MathScrollPanel(net.minecraft.client.Minecraft mc, int width, int height, int top, int left) {
            super(mc, width, height, top, left);

            int lastJavaMathY = 0;

            for (int i = 0; i < javaMathOps.length; i++) {
                String op = javaMathOps[i];
                String tooltipText = switch (op) {
                    case "mod" -> "Remainder of a divided by b\n\nmod(a, b)";
                    case "factorial" -> "Factorial of a\n\na!";
                    case "log_b" -> "Logarithm of a with base b\n\nlog_b(a)";
                    case "max" -> "Returns the larger value of a and b\n\nmax(a, b)";
                    case "min" -> "Returns the smaller value of a and b\n\nmin(a, b)";
                    case "^" -> "Raises a to the power of b\n\na ^ b";
                    case "abs" -> "Absolute value of a\n\n|a|";
                    default -> "Uses a only (Ignores b and c)";
                };

                int btnX = (i % 5 * 40);
                int btnY = 15 + (i / 5 * 22);

                lastJavaMathY = Math.max(lastJavaMathY, btnY);

                this.internalYOffsets.add(btnY);
                this.buttons.add(Button.builder(Component.literal(op), b -> sendCalc(op))
                        .bounds(this.left + btnX, this.top + btnY, 36, 20)
                        .tooltip(Tooltip.create(Component.literal(tooltipText)))
                        .build());
            }

            int customMathStartY = lastJavaMathY + 35;

            for (int i = 0; i < customMathOps.length; i++) {
                String op = customMathOps[i];
                String tooltipText = switch (op) {
                    case "mth_clamp" -> "Clamps value a between min b and max c\n\nclamp(a, b, c)";
                    case "mth_wrap" -> "Wraps angle to degrees between -180 and 180\n\nwrapDegrees(a)";
                    case "√" -> "Square root of a\n\n√(a)";
                    case "a²" -> "Squares the input value a\n\na²";
                    default -> "Minecraft math helper.\n\nUses a only.";
                };

                int btnX = (i % 3 * 66);
                int btnY = customMathStartY + 15 + (i / 3 * 22);

                this.internalYOffsets.add(btnY);
                this.buttons.add(Button.builder(Component.literal(op.substring(4)), b -> sendCalc(op))
                        .bounds(this.left + btnX, this.top + btnY, 62, 20)
                        .tooltip(Tooltip.create(Component.literal(tooltipText)))
                        .build());
            }
            this.dynamicCustomMathLabelY = customMathStartY;
        }

        @Override
        protected int getContentHeight() {
            if (this.internalYOffsets.isEmpty()) {
                return 215;
            }

            int lastButtonY = this.internalYOffsets.get(this.internalYOffsets.size() - 1);

            return lastButtonY + 25;
        }

        @Override
        protected void drawPanel(GuiGraphics graphics, int entryRight, int relativeY, com.mojang.blaze3d.vertex.Tesselator tesselator, int mouseX, int mouseY) {
            graphics.drawString(CalculatorScreen.this.font, "Java & Custom Math", this.left, relativeY + 2, 0x909090, false);

            int customMathLabelY = relativeY + this.dynamicCustomMathLabelY;

            graphics.drawString(CalculatorScreen.this.font, "Minecraft Mth", this.left, customMathLabelY, 0x909090, false);


            for (int i = 0; i < this.buttons.size(); i++) {
                Button btn = this.buttons.get(i);
                int yOffset = this.internalYOffsets.get(i);

                btn.setY(relativeY + yOffset);

                if (btn.getY() >= this.top - 15 && btn.getY() + btn.getHeight() <= this.top + this.height + 15) {
                    btn.render(graphics, mouseX, mouseY, 0);
                }
            }
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (this.left <= mouseX && mouseX <= this.left + this.width && this.top <= mouseY && mouseY <= this.top + this.height) {
                for (Button btn : this.buttons) {
                    if (btn.mouseClicked(mouseX, mouseY, button)) {
                        return true;
                    }
                }
            }
            return super.mouseClicked(mouseX, mouseY, button);
        }
        @Override
        public @NotNull net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority narrationPriority() {
            return net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority.NONE;
        }
        @Override
        public void updateNarration(@NotNull net.minecraft.client.gui.narration.NarrationElementOutput narrationElementOutput) {
        }
    }
}