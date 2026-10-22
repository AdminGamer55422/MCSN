package net.admingamer.mcsn.mcsnactionchat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class MCSNNetworkHandler {
    private static final String PROTOCOL_VERSION = "1";

    // FIX 1: Using ResourceLocation.tryParse to completely bypass the constructor deprecation warning
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            ResourceLocation.tryParse("mcsnactionchat:main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void register() {
        // FIX 2: This will resolve once EntityChatPacket.java exists in the same package
        INSTANCE.messageBuilder(EntityChatPacket.class, packetId++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(EntityChatPacket::encode)
                .decoder(EntityChatPacket::decode)
                .consumerMainThread(EntityChatPacket::handle)
                .add();
    }

    public static void sendDialogueToPlayer(ServerPlayer player, String speaker, String text, int color, int durationSeconds) {
        INSTANCE.sendTo(new EntityChatPacket(speaker, text, color, durationSeconds),
                player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}
