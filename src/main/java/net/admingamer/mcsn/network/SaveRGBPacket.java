package net.admingamer.mcsn.network;

import net.admingamer.mcsn.blockentity.RGBBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SaveRGBPacket {
    private final BlockPos pos;
    private final int rgb;

    public SaveRGBPacket(BlockPos pos, int rgb) {
        this.pos = pos;
        this.rgb = rgb;
    }

    public static void encode(SaveRGBPacket msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeInt(msg.rgb);
    }

    public static SaveRGBPacket decode(FriendlyByteBuf buf) {
        return new SaveRGBPacket(buf.readBlockPos(), buf.readInt());
    }

    public static void handle(SaveRGBPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player != null) {
                var level = player.level();
                BlockEntity be = level.getBlockEntity(msg.pos);
                if (be instanceof RGBBlockEntity rgbBE) {
                    rgbBE.setColor(msg.rgb);

                    var state = level.getBlockState(msg.pos);

                    level.sendBlockUpdated(msg.pos, state, state, 3);
                }
            }
        });
        ctx.setPacketHandled(true);
    }

}
