package com.acikek.hdiamond.network;

import com.acikek.hdiamond.HDiamond;
import com.acikek.hdiamond.api.util.HazardDataHolder;
import com.acikek.hdiamond.core.HazardData;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;

public final class C2SUpdatePanelData implements IMessage {

    HazardData data = null;

    public C2SUpdatePanelData() {}

    public C2SUpdatePanelData(HazardData data) {
        this.data = data;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.data = HazardData.read(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        this.data.write(buf);
    }

    public static final class Handler implements IMessageHandler<C2SUpdatePanelData, IMessage> {

        @Override
        public IMessage onMessage(C2SUpdatePanelData msg, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            NetHandler.DataPair hd = NetHandler.getTracker(player);
            hd.dataHolder.setHazardData(msg.data, hd.dir);
            return null;
        }
    }
}
