package net.admingamer.mcsn.client;

import net.admingamer.mcsn.block.MCSNBlocks;
import net.admingamer.mcsn.item.LaserGunItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = "mcsn", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TooltipEventHandler {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        List<Component> tooltip = event.getToolTip();

        if (stack.getItem() instanceof BlockItem blockItem) {
            var block = blockItem.getBlock();

            if (block == MCSNBlocks.CLOTH.get() ||
                    block == MCSNBlocks.DYABLE_PLANKS.get() ||
                    block == MCSNBlocks.DYABLE_GLASS.get() ||
                    block == MCSNBlocks.DYABLE_CONCRETE.get() ||
                    block == MCSNBlocks.DYABLE_GLOWSTONE.get()) {

                int r = 255, g = 255, b = 255;

                CompoundTag blockEntityTag = stack.getTagElement("BlockEntityTag");
                if (blockEntityTag != null && blockEntityTag.contains("Color")) {
                    int hexColor = blockEntityTag.getInt("Color");
                    r = (hexColor >> 16) & 0xff;
                    g = (hexColor >> 8) & 0xff;
                    b = hexColor & 0xff;
                }

                tooltip.add(Component.literal("§cR: " + r));
                tooltip.add(Component.literal("§aG: " + g));
                tooltip.add(Component.literal("§9B: " + b));
            }
        }
        else if (stack.getItem() instanceof LaserGunItem) {
            CompoundTag nbt = stack.getTag();

            // Fetch FromColor data arrays or fallback to default Red [255, 0, 0]
            int[] fromRGB = (nbt != null && nbt.contains("FromColor")) ? nbt.getIntArray("FromColor") : new int[]{255, 0, 0};
            // Fetch ToColor data arrays or fallback to default Blue [0, 0, 255]
            int[] toRGB = (nbt != null && nbt.contains("ToColor")) ? nbt.getIntArray("ToColor") : new int[]{0, 0, 255};

            // Formatting variables safely to avoid potential out-of-bounds crashes
            if (fromRGB.length >= 3 && toRGB.length >= 3) {
                tooltip.add(Component.literal("§7Laser Transition:"));
                tooltip.add(Component.literal("  §fFrom: §c" + fromRGB[0] + "§7, §a" + fromRGB[1] + "§7, §9" + fromRGB[2]));
                tooltip.add(Component.literal("  §fTo:   §c" + toRGB[0] + "§7, §a" + toRGB[1] + "§7, §9" + toRGB[2]));
            }
        }
    }
}
