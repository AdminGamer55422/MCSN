package net.admingamer.mcsn.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class GauntletItem extends Item {
    private static final UUID BASE_ATTACK_DAMAGE_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    private static final UUID BASE_ATTACK_SPEED_UUID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    private final GauntletType type;

    public enum GauntletType {
        GOLDEN, PRISMARINE, CHAMPION, SNOWY
    }

    public GauntletItem(GauntletType type, Properties properties) {
        super(properties);
        this.type = type;

        double damage = 2.0;
        double speed = -2.4;

        if (type == GauntletType.CHAMPION) {
            damage = 5.5;
            speed = -3.0;
        } else if (type == GauntletType.GOLDEN) {
            damage = 3.0;
            speed = -2.0;
        }

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", damage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", speed, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.defaultModifiers;
        }
        return super.getDefaultAttributeModifiers(slot);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide() && entity instanceof LivingEntity livingEntity) {

            if (livingEntity.getMainHandItem() == stack || livingEntity.getOffhandItem() == stack) {
                applyGauntletEffects(livingEntity);
            }
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, LivingEntity attacker) {
        if (!attacker.level().isClientSide()) {
            switch (this.type) {
                case GOLDEN -> target.knockback(0.5, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
                case PRISMARINE -> {
                    if (target.isInWaterOrRain()) {
                        target.hurt(target.damageSources().mobAttack(attacker), 4.0f);
                    }
                }
                case CHAMPION -> target.setSecondsOnFire(5);
                case SNOWY -> target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    private void applyGauntletEffects(LivingEntity entity) {
        switch (this.type) {
            case GOLDEN -> entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 20, 0, false, false, true));
            case PRISMARINE -> {
                entity.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 20, 0, false, false, true));
                entity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 20, 0, false, false, true));
                entity.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 20, 0, false, false, true));
            }
            case CHAMPION -> {
                entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20, 0, false, false, true));
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 0, false, false, true));
            }
            case SNOWY -> entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 0, false, false, true));
        }
    }
}
