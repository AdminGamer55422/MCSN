package net.admingamer.mcsn.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class GlowingObsidianBlock extends Block {
    public GlowingObsidianBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        super.animateTick(state, level, pos, random);

        int particleCount = 4;

        for (int i = 0; i < particleCount; i++) {
            double x = (double)pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 1.2;
            double y = (double)pos.getY() + 0.5 + (random.nextDouble() - 0.5) * 1.2;
            double z = (double)pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 1.2;

            float r = 1.0f;
            float g = 0.0f;
            float b = 0.0f;
            float scale = 1.0f;

            level.addParticle(
                    new DustParticleOptions(new Vector3f(r, g, b), scale),
                    x, y, z,
                    0.0, 0.0, 0.0
            );
        }
    }
}
