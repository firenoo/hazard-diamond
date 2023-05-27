package com.acikek.hdiamond.block;

import com.acikek.hdiamond.HDiamond;
import com.acikek.hdiamond.api.util.HazardDataHolder;
import com.acikek.hdiamond.core.HazardData;
import com.acikek.hdiamond.network.NetHandler;
import com.acikek.hdiamond.network.S2COpenGui;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * A block base that, when interacted with, displays immutable {@link HazardData}.<br>
 * This is not automatically included in
 */
public class BlockWithHazardData extends Block implements HazardDataHolder {

    private HazardData data;

    protected BlockWithHazardData(Material materialIn, HazardData data) {
        super(materialIn);
        this.data = data;
    }

    public void updateHazardData(HazardData newData) {
        this.data = newData;
    }

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

    @Override
    public boolean hasTileEntity(int metadata) {
        return super.hasTileEntity(metadata);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return super.createTileEntity(world, metadata);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ) {
        if (world.isRemote) {
            return true;
        }

        if (world.blockExists(x, y, z)) {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te instanceof HazardTileEntity hte) {
                NetHandler.sendToPlayer(
                    new S2COpenGui(hte.getHazardData(ForgeDirection.getOrientation(side)), hte.isEditable(ForgeDirection.getOrientation(side))), player);
            }
        }

        return false;
    }
}
