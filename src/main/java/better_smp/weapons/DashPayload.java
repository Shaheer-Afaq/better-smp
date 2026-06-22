package better_smp.weapons;

import better_smp.BetterSMP;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record DashPayload(double dirX, double dirY, double dirZ, int ticks) implements CustomPacketPayload {
    public static final Type<DashPayload> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath(BetterSMP.MOD_ID, "dash")
    );

    public static final StreamCodec<ByteBuf, DashPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE, DashPayload::dirX,
            ByteBufCodecs.DOUBLE, DashPayload::dirY,
            ByteBufCodecs.DOUBLE, DashPayload::dirZ,
            ByteBufCodecs.VAR_INT, DashPayload::ticks,
            DashPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}