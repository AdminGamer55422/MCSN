package net.admingamer.mcsn.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.List;

public class LaserGunItem extends Item {
    public LaserGunItem(Properties properties) {
        super(properties);
    }

    // 1. Right-Click Trigger: Handles Screen opening (Shift or Look Up) and Laser charging
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // SHIFT + RIGHT CLICK: Secondary path to open the menu manually
        if (player.isCrouching()) {
            if (level.isClientSide()) {
                net.minecraft.client.Minecraft.getInstance().setScreen(new net.admingamer.mcsn.client.LaserColorScreen());
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }

        // STANDARD RIGHT CLICK: Fire the continuous laser loop
        player.startUsingItem(hand);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }



    // 2. Engine Core Hook: Runs every frame on the server to shoot the laser if held
    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel && entity instanceof Player player) {
            // Checks if the player is currently actively using/holding right-click on this item
            if (isSelected && player.isUsingItem() && player.getUseItem().getItem() == this) {

                // --- NEW: FIRING SOUND LOOP ---
                // Plays a sound on the player every 4 ticks while holding down the trigger
                if (player.tickCount % 4 == 0) {
                    serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 0.5f, 2.0f);
                }
                // ------------------------------

                runLaserLogic(serverLevel, player, stack);
            }
        }
    }

    // 3. The Core Laser Loop Method
    private void runLaserLogic(ServerLevel serverLevel, Player player, ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        Vector3f fromColor = nbt.contains("FromColor") ? getVectorFromNBT(nbt.getIntArray("FromColor")) : new Vector3f(1.0f, 0.0f, 0.0f);
        Vector3f toColor = nbt.contains("ToColor") ? getVectorFromNBT(nbt.getIntArray("ToColor")) : new Vector3f(0.0f, 0.0f, 1.0f);

        Vec3 startPos = player.position().add(0, player.getEyeHeight(), 0);
        Vec3 direction = player.getLookAngle().normalize();

        int maxBounces = 30;
        double remainingDistance = 125.0;
        double stepSize = 0.25;

        Vec3 currentPos = startPos;

        for (int bounce = 0; bounce < maxBounces; bounce++) {
            Vec3 endPos = currentPos.add(direction.scale(remainingDistance));

            BlockHitResult hitResult = serverLevel.clip(new net.minecraft.world.level.ClipContext(
                    currentPos, endPos,
                    net.minecraft.world.level.ClipContext.Block.COLLIDER,
                    net.minecraft.world.level.ClipContext.Fluid.NONE,
                    player
            ));

            Vec3 endSegment = hitResult.getType() == HitResult.Type.MISS ? endPos : hitResult.getLocation();
            double segmentLength = currentPos.distanceTo(endSegment);

            AABB laserBox = new AABB(currentPos, endSegment).inflate(0.2);
            List<LivingEntity> targets = serverLevel.getEntitiesOfClass(LivingEntity.class, laserBox,
                    target -> target != player && target.isAlive());

            for (LivingEntity target : targets) {
                AABB targetBox = target.getBoundingBox().inflate(0.1);
                if (targetBox.clip(currentPos, endSegment).isPresent()) {
                    target.hurt(serverLevel.damageSources().playerAttack(player), 2.0f);
                }
            }

            double distanceTraveled = 0;
            while (distanceTraveled < segmentLength) {
                Vec3 particlePos = currentPos.add(direction.scale(distanceTraveled));
                serverLevel.sendParticles(
                        new DustColorTransitionOptions(fromColor, toColor, 1.0f),
                        particlePos.x, particlePos.y, particlePos.z,
                        1, 0, 0, 0, 0
                );
                distanceTraveled += stepSize;
            }

            if (hitResult.getType() == HitResult.Type.MISS) {
                break;
            }

            remainingDistance -= segmentLength;
            if (remainingDistance <= 0) break;

            BlockPos hitPos = hitResult.getBlockPos();
            BlockState hitState = serverLevel.getBlockState(hitPos);

            if (hitState.is(BlockTags.IMPERMEABLE) || hitState.is(Tags.Blocks.GLASS)) {
                currentPos = endSegment.add(direction.scale(0.1));
                bounce--;
                continue;
            }

            serverLevel.playSound(null, hitPos, SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 1.0f, 2.0f);

            Direction face = hitResult.getDirection();
            currentPos = endSegment.add(Vec3.atLowerCornerOf(face.getNormal()).scale(0.01));

            switch (face.getAxis()) {
                case X -> direction = new Vec3(-direction.x, direction.y, direction.z);
                case Y -> direction = new Vec3(direction.x, -direction.y, direction.z);
                case Z -> direction = new Vec3(direction.x, direction.y, -direction.z);
            }
        }
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 88000;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.SPYGLASS;
    }

    // Fixed: Properly reading the precise integer indices for Vector3f conversion
    private Vector3f getVectorFromNBT(int[] rgb) {
        if (rgb.length >= 3) {
            return new Vector3f(rgb[0] / 255.0f, rgb[1] / 255.0f, rgb[2] / 255.0f);
        }
        return new Vector3f(1.0f, 0.0f, 0.0f);
    }
}
