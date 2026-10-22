package net.admingamer.mcsn.item;

import net.admingamer.mcsn.MCSN;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MCSNItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MCSN.MOD_ID);

    public static final RegistryObject<Item> WITHERED_NETHER_STAR = ITEMS.register("withered_nether_star",
            () -> new WitheredNetherStarItem(new Item.Properties()
                    .rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> WITHERED_FLESH = ITEMS.register("withered_flesh",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationMod(0.1f)
                    .meat()
                    .effect(() -> new MobEffectInstance(MobEffects.WITHER, 200, 0), 1.0f)
                    .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 1.0f)
                    .build())));

    public static final RegistryObject<Item> GOLDEN_GAUNTLET = ITEMS.register("golden_gauntlet",
            () -> new GauntletItem(GauntletItem.GauntletType.GOLDEN, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> PRISMARINE_GAUNTLET = ITEMS.register("prismarine_gauntlet",
            () -> new GauntletItem(GauntletItem.GauntletType.PRISMARINE, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CHAMPION_GAUNTLET = ITEMS.register("champion_gauntlet",
            () -> new GauntletItem(GauntletItem.GauntletType.CHAMPION, new Item.Properties().stacksTo(1)));

    /*
    public static final RegistryObject<Item> SNOWY_GAUNTLET = ITEMS.register("snowy_gauntlet",
            () -> new GauntletItem(GauntletItem.GauntletType.SNOWY, new Item.Properties().stacksTo(1)));
    */

    public static final RegistryObject<Item> PRISMARINE_AXE = ITEMS.register("prismarine_axe",
            () -> new AxeItem(MCSNToolTiers.PRISMARINE, 4, 2, new Item.Properties()));

    public static final RegistryObject<Item> DIAMOND_DETECTOR = ITEMS.register("diamond_detector",
            () -> new DiamondDetectorItem(new Item.Properties().durability(200)));

    public static final RegistryObject<Item> CALCULATOR = ITEMS.register("calculator",
            () -> new CalculatorItem(new Item.Properties()));

    public static final RegistryObject<Item> RGB_PAINTBRUSH = ITEMS.register("rgb_paintbrush",
            () -> new PaintbrushItem(new Item.Properties()));

    public static final RegistryObject<Item> LASER_GUN = ITEMS.register("laser_gun",
            () -> new LaserGunItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    .durability(489)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
