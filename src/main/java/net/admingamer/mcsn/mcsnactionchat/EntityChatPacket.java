package net.admingamer.mcsn.mcsnactionchat;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class EntityChatPacket {
    private final String speaker;
    private final String text;
    private final int color;
    private final int durationSeconds;

    public EntityChatPacket(String speaker, String text, int color, int durationSeconds) {
        this.speaker = speaker;
        this.text = text;
        this.color = color;
        this.durationSeconds = durationSeconds;
    }

    public static void encode(EntityChatPacket pkt, FriendlyByteBuf buf) {
        buf.writeUtf(pkt.speaker);
        buf.writeUtf(pkt.text);
        buf.writeInt(pkt.color);
        buf.writeInt(pkt.durationSeconds);
    }

    public static EntityChatPacket decode(FriendlyByteBuf buf) {
        return new EntityChatPacket(buf.readUtf(), buf.readUtf(), buf.readInt(), buf.readInt());
    }

    public static void handle(EntityChatPacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            // Safely route to client-side logic to add to our Story Mode custom queue
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                DialogueQueueManager.queueLine(pkt.speaker, pkt.text, pkt.color, pkt.durationSeconds);
            });
        });
        ctx.setPacketHandled(true);
    }
}
