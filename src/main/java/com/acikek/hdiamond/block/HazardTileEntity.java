package com.acikek.hdiamond.block;

import com.acikek.hdiamond.api.util.HazardDataHolder;
import com.acikek.hdiamond.core.HazardData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * A simple hazard tile entity implementation
 */
public class HazardTileEntity extends TileEntity implements HazardDataHolder {

    private HazardData data;

    @Override
    public HazardData getHazardData(ForgeDirection dir) {
        return data;
    }

    @Override
    public boolean isEditable(ForgeDirection dir) {
        return false;
    }

    @Override
    public void setHazardData(HazardData data, ForgeDirection dir) {

    }
}
