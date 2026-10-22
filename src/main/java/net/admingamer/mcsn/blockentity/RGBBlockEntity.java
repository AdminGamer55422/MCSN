package net.admingamer.mcsn.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RGBBlockEntity extends BlockEntity {
    private int color = 16777215; // Default to white (0xFFFFFF)

    public RGBBlockEntity(BlockPos pos, BlockState state) {
        super((BlockEntityType<?>) MCSNBlockEntities.RGB_BLOCK_ENTITY.get(), pos, state);
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
        this.setChanged(); // Replaces m_6596_()

        if (this.level != null) { // Replaces f_58857_
            // Replaces m_7260_ and m_58900_ (Updates block state on the client/world)
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    @Override
    public void load(@NotNull CompoundTag tag) { // Replaces m_142466_
        super.load(tag);
        if (tag.contains("Color")) { // Replaces m_128441_
            this.color = tag.getInt("Color"); // Replaces m_128451_
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) { // Replaces m_183515_
        super.saveAdditional(tag);
        tag.putInt("Color", this.color); // Replaces m_128405_
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() { // Replaces m_5995_
        CompoundTag tag = super.getUpdateTag();
        tag.putInt("Color", this.color);
        return tag;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() { // Replaces m_58483_
        return ClientboundBlockEntityDataPacket.create(this); // Replaces m_195640_
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag tag = pkt.getTag(); // Replaces m_131708_
        if (tag != null && tag.contains("Color")) {
            this.color = tag.getInt("Color");
            if (this.level != null && this.level.isClientSide) { // Replaces f_46443_
                this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
            }
        }
    }
}
