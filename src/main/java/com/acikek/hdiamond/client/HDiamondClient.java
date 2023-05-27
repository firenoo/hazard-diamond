package com.acikek.hdiamond.client;

import com.acikek.hdiamond.CommonProxy;
import com.acikek.hdiamond.HDiamond;
import com.acikek.hdiamond.client.render.PanelEntityRenderer;
import com.acikek.hdiamond.entity.PanelEntity;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.util.ResourceLocation;

public class HDiamondClient extends CommonProxy {

    public static final ResourceLocation WIDGETS = HDiamond.id("textures/gui/hazards.png");

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        RenderingRegistry.registerEntityRenderingHandler(PanelEntity.class, new PanelEntityRenderer());
    }
}
