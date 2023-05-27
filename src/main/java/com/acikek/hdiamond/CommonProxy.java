package com.acikek.hdiamond;

import com.acikek.hdiamond.api.util.HazardDataHolder;
import com.acikek.hdiamond.item.PanelItem;
import com.acikek.hdiamond.network.C2SUpdatePanelData;
import com.acikek.hdiamond.network.NetHandler;
import com.acikek.hdiamond.network.S2COpenGui;
import com.myname.mymodid.Tags;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.UUID;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        HDiamondConfig.synchronizeConfiguration(event.getSuggestedConfigurationFile());
        PanelItem.register();
    }

    public void init(FMLInitializationEvent event) {
        NetHandler.initNetwork();
    }

    public void postInit(FMLPostInitializationEvent event) {}

    public void serverStarting(FMLServerStartingEvent event) {}




}
