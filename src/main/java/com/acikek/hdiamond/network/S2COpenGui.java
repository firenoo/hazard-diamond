package com.acikek.hdiamond.network;

import com.acikek.hdiamond.client.screen.HazardScreen;
import com.acikek.hdiamond.core.HazardData;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;

/**
 * Notify the client to open the gui
 */
public class S2COpenGui implements IMessage {

    HazardData data;
    boolean isEditable;

    @SuppressWarnings("unused")
    public S2COpenGui() {}

    public S2COpenGui(HazardData data, boolean isEditable) {
        this.data = data;
        this.isEditable = isEditable;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.data = HazardData.read(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static final class Handler implements IMessageHandler<S2COpenGui, IMessage> {

        @Override
        public IMessage onMessage(S2COpenGui msg, MessageContext ctx) {
            Minecraft mc = Minecraft.getMinecraft();
            if (!(mc.currentScreen instanceof HazardScreen)) {
                mc.displayGuiScreen(new HazardScreen(msg.data, msg.isEditable));
            }
            return null;
        }
    }
}
