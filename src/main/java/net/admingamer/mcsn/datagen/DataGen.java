package net.admingamer.mcsn.datagen;

import net.admingamer.mcsn.MCSN;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = MCSN.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator dataGen = event.getGenerator();
        PackOutput packOutput = dataGen.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        dataGen.addProvider(event.includeServer(), new MCSNRecipeProvider(packOutput));
        //dataGen.addProvider(event.includeServer(), MCSNLootTableProvider.create(packOutput));
        dataGen.addProvider(event.includeClient(), new MCSNBlockStateProvider(packOutput, existingFileHelper));
        dataGen.addProvider(event.includeClient(), new MCSNItemModelProvider(packOutput, existingFileHelper));
        MCSNBlockTagGen mcsnBlockTagGen = dataGen.addProvider(event.includeServer(),
                new MCSNBlockTagGen(packOutput, lookupProvider, existingFileHelper));
        dataGen.addProvider(event.includeServer(), new MCSNItemTagGen(packOutput, lookupProvider, mcsnBlockTagGen.contentsGetter(), existingFileHelper));




    }
}
