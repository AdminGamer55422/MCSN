package net.admingamer.mcsn.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

public class RGBGuiHelper {
    public static void openScreen(BlockPos pos, int currentColors) {
        Minecraft.getInstance().setScreen(new RGBScreen(pos, currentColors));
    }
}
