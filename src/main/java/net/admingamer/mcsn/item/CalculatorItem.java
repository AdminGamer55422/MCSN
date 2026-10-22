package net.admingamer.mcsn.item;

import net.admingamer.mcsn.menu.CalculatorMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class CalculatorItem extends Item {
    public CalculatorItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            NetworkHooks.openScreen((ServerPlayer) player, new SimpleMenuProvider(
                    (containerId, playerInventory, p) -> new CalculatorMenu(containerId, playerInventory),
                    Component.literal("Advanced Java & Minecraft Calculator")
            ));
        }
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}