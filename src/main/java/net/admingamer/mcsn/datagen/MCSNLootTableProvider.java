package net.admingamer.mcsn.datagen;


import net.admingamer.mcsn.datagen.loot.MCSNBlockLootTables;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;


import java.util.List;
import java.util.Set;


public class MCSNLootTableProvider {
    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(MCSNBlockLootTables::new, LootContextParamSets.BLOCK)
        ));
    }
}
