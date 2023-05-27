package com.acikek.hdiamond.network;

import com.acikek.hdiamond.api.util.HazardDataHolder;
import com.myname.mymodid.Tags;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.util.ForgeDirection;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.UUID;

public final class NetHandler {
    public final SimpleNetworkWrapper NET = NetworkRegistry.INSTANCE.newSimpleChannel(Tags.MODID);
    private final HashMap<UUID, DataPair> players = new HashMap<>();

    private static NetHandler INSTANCE;

    private NetHandler() {
        int id = 0;
        NET.registerMessage(C2SUpdatePanelData.Handler.class, C2SUpdatePanelData.class, id++, Side.SERVER);
        NET.registerMessage(S2COpenGui.Handler.class, S2COpenGui.class, id++, Side.CLIENT);
    }

    public static void initNetwork() {
        INSTANCE = new NetHandler();
    }

    public static void sendToPlayer(IMessage msg, EntityPlayer player) {
        INSTANCE.NET.sendTo(msg, (EntityPlayerMP) player);
    }

    /**
     * Wrapper for sending messages to server (client side only)
     */
    @SideOnly(Side.CLIENT)
    public static void sendToServer(IMessage message) {
        INSTANCE.NET.sendToServer(message);
    }

    /**
     * Associates the player with the specified data holder. This data holder
     * will have its data holder updated.
     */
    public static void addTracker(@Nonnull EntityPlayer player, @Nonnull HazardDataHolder holder, @Nonnull ForgeDirection dir) {
        INSTANCE.players.put(player.getUniqueID(), new DataPair(holder, dir));
    }

    /**
     * Clears any data holder associated to the player.
     */
    public static void removeTracker(@Nonnull EntityPlayer player) {
        INSTANCE.players.remove(player.getUniqueID());
    }

    /**
     * Get the data holder tracked, or null if player/data is not tracked.
     */
    public static DataPair getTracker(@Nonnull EntityPlayer player) {
        return INSTANCE.players.get(player.getUniqueID());
    }

    public static class DataPair {
        @Nonnull
        public final HazardDataHolder dataHolder;
        @Nonnull
        public final ForgeDirection dir;

        private DataPair(@Nonnull HazardDataHolder dataHolder, @Nonnull ForgeDirection dir) {
            this.dataHolder = dataHolder;
            this.dir = dir;
        }
    }
}
