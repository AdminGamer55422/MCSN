package net.admingamer.mcsn.item;

import net.admingamer.mcsn.MCSN;
import net.admingamer.mcsn.block.MCSNBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class MCSNCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MCSN.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MCSN_ITEMS_TAB = CREATIVE_MODE_TABS.register("mcsn_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(MCSNItems.LASER_GUN.get()))
                    .title(Component.translatable("creativetab.mcsn_items_tab"))
                    .displayItems((itemDisplayParameters, output) ->  {
                        for(RegistryObject<Item> item : MCSNItems.ITEMS.getEntries()) {
                            output.accept(item.get());
                        }
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
