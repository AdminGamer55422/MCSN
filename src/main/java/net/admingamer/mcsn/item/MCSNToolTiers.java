package net.admingamer.mcsn.item;

import net.admingamer.mcsn.MCSN;
import net.admingamer.mcsn.util.MCSNTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class MCSNToolTiers {
    public static final Tier PRISMARINE = TierSortingRegistry.registerTier(
            new ForgeTier(2, 500, 6.5f, 2.5f, 18,
                    MCSNTags.Blocks.NEEDS_PRISMARINE_TOOL,
                    () -> Ingredient.of(Items.PRISMARINE_SHARD)
            ),
            ResourceLocation.fromNamespaceAndPath(MCSN.MOD_ID, "prismarine"),
            List.of(Tiers.IRON),
            List.of(Tiers.DIAMOND)
    );
}
