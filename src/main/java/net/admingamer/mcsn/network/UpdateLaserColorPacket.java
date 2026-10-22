package net.admingamer.mcsn.network; // Ensure this matches your folder package string!

import net.admingamer.mcsn.item.LaserGunItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateLaserColorPacket {
    private final int[] fromColor;
    private final int[] toColor;

    public UpdateLaserColorPacket(int[] fromColor, int[] toColor) {
        this.fromColor = fromColor;
        this.toColor = toColor;
    }

    public UpdateLaserColorPacket(FriendlyByteBuf buf) {
        this.fromColor = buf.readVarIntArray();
        this.toColor = buf.readVarIntArray();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVarIntArray(this.fromColor);
        buf.writeVarIntArray(this.toColor);
    }

    // FIX: Changed signature to take (msg, contextSupplier) matching Forge's BiConsumer requirements!
    public static void handle(UpdateLaserColorPacket msg, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                ItemStack stack = player.getMainHandItem();
                if (stack.getItem() instanceof LaserGunItem) {
                    var nbt = stack.getOrCreateTag();
                    nbt.putIntArray("FromColor", msg.fromColor); // Use msg.fromColor
                    nbt.putIntArray("ToColor", msg.toColor);     // Use msg.toColor
                }
            }
        });
        context.setPacketHandled(true);
    }
}
