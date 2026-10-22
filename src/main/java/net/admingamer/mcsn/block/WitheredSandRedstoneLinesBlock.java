package net.admingamer.mcsn.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class WitheredSandRedstoneLinesBlock extends FallingBlock {
    private final int dustColor;

    public WitheredSandRedstoneLinesBlock(int pDustColor, BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.dustColor = pDustColor;
    }

    public int getDustColor(@NotNull BlockState pState, @NotNull BlockGetter pReader, @NotNull BlockPos pPos) {
        return this.dustColor;
    }
}

