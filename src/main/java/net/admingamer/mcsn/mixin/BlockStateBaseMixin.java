package net.admingamer.mcsn.mixin;

import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.admingamer.mcsn.block.MCSNBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class BlockStateBaseMixin {

    @Inject(method = "getMapColor", at = @At("HEAD"), cancellable = true)
    private void mcsn$injectTrueRGBHexColors(BlockGetter level, BlockPos pos, CallbackInfoReturnable<MapColor> cir) {
        BlockBehaviour.BlockStateBase state = (BlockBehaviour.BlockStateBase) (Object) this;

        if (state.is(MCSNBlocks.BLACK_WITHERSTORM_FLESH_BLOCK.get())) {
            cir.setReturnValue(MapColorAccessor.createTrueRGBColor(0x1A0A31));
        } else if (state.is(MCSNBlocks.DARK_PURPLE_WITHERSTORM_FLESH_BLOCK.get())) {
            cir.setReturnValue(MapColorAccessor.createTrueRGBColor(0x29184A));
        } else if (state.is(MCSNBlocks.PURPLE_WITHERSTORM_FLESH_BLOCK.get())) {
            cir.setReturnValue(MapColorAccessor.createTrueRGBColor(0x6D348D));
        }
    }
}
