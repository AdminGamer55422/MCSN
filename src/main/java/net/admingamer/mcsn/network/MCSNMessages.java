package net.admingamer.mcsn.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class MCSNMessages {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(ResourceLocation.tryBuild("mcsn", "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(CalculatorPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(CalculatorPacket::fromBytes)
                .encoder(CalculatorPacket::toBytes)
                .consumerMainThread(CalculatorPacket::handle)
                .add();

        net.messageBuilder(SaveRGBPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .encoder(SaveRGBPacket::encode)
                .decoder(SaveRGBPacket::decode)
                .consumerMainThread(SaveRGBPacket::handle)
                .add();

        net.messageBuilder(UpdateLaserColorPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .encoder(UpdateLaserColorPacket::toBytes)
                .decoder(UpdateLaserColorPacket::new)
                .consumerMainThread(UpdateLaserColorPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }
}
