package net.admingamer.mcsn.item;

import net.admingamer.mcsn.util.MCSNTags;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class DiamondDetectorItem extends Item {
    public DiamondDetectorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext) {
        Player player = pContext.getPlayer();

        if (player == null) {
            return InteractionResult.PASS;
        }

        pContext.getLevel().playSound(player, player.getX(), player.getY(), player.getZ(),
                SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 1.0f, 1.0f);

        if (!pContext.getLevel().isClientSide()) {
            BlockPos positionClicked = pContext.getClickedPos();
            boolean foundDiamondOre = false;

            for (int i = 0; i <= positionClicked.getY() + 64; i++) {
                BlockState state = pContext.getLevel().getBlockState(positionClicked.below(i));

                if (foundDiamondBlock(state)) {
                    outputDiamondCoordinates(positionClicked.below(i), player, state.getBlock());
                    foundDiamondOre = true;
                    break;
                }
            }

            if (!foundDiamondOre) {
                player.displayClientMessage(Component.literal("§cNo diamonds found!§r"), true);
            }

            pContext.getItemInHand().hurtAndBreak(1, player,
                    p -> p.broadcastBreakEvent(pContext.getHand()));
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return true;
    }


    private void outputDiamondCoordinates(BlockPos blockPos, Player player, Block block) {
        player.displayClientMessage(Component.literal("§aFound " + I18n.get(block.getDescriptionId()) + " at [X: " + blockPos.getX() + ", Y: " + blockPos.getY() + ", Z: " + blockPos.getZ() + "]§r"), true);
    }

    private boolean foundDiamondBlock(BlockState state) {
        return state.is(MCSNTags.Blocks.DIAMOND_ORE_VARIANTS);
    }
}

