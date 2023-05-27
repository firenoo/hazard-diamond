package com.acikek.hdiamond.entity;

import com.acikek.hdiamond.HDiamond;
import com.acikek.hdiamond.api.util.HazardDataHolder;
import com.acikek.hdiamond.core.HazardData;
import com.acikek.hdiamond.network.NetHandler;
import com.acikek.hdiamond.network.S2COpenGui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class PanelEntity extends EntityHanging implements HazardDataHolder {

    private boolean isEditable;

    public PanelEntity(World world) {
        super(world);
    }

    public PanelEntity(World world, int xPos, int yPos, int zPos, int side) {
        super(world, xPos, yPos, zPos, side);
        setDirection(side);
        this.isEditable = true;
    }

    @Override
    protected void entityInit() {
        HazardData.initDataWatcher(getDataWatcher());
        getDataWatcher().addObject(7, 0);
    }

    @Override
    public boolean interactFirst(EntityPlayer player) {
        ItemStack stack = player.getHeldItem();
        if (player.isSneaking() && stack.getItem() == Items.slime_ball) {
            if (isEditable) {
                return false;
            }
            if (!player.capabilities.isCreativeMode) {
                stack.stackSize -= 1;
            }
            playSound("mob.slime.small", 1.0f, 1.0f);
            getDataWatcher().updateObject(7, 1);
            getDataWatcher().setObjectWatched(7);
        } else if (!player.worldObj.isRemote) {
            NetHandler.sendToPlayer(new S2COpenGui(getHazardData(ForgeDirection.UNKNOWN), isEditable), player);
        }
        return player.worldObj.isRemote;
    }

    @Override
    public int getWidthPixels() {
        return 14;
    }

    @Override
    public int getHeightPixels() {
        return 14;
    }

    @Override
    public void onBroken(Entity p_110128_1_) {

    }

    @Override
    public HazardData getHazardData(ForgeDirection dir) {
        return HazardData.readDataWatcher(getDataWatcher());
    }

    @Override
    public boolean isEditable(ForgeDirection dir) {
        return false;
    }

    @Override
    public void setHazardData(HazardData data, ForgeDirection dir) {
        if (isEditable(dir)) {

        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        HazardData.fromNbt(nbt.getCompoundTag("HazardData")).writeDataWatcher(getDataWatcher());
        getDataWatcher().updateObject(7, nbt.getBoolean("Waxed") ? 1 : 0);
        getDataWatcher().setObjectWatched(7);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setTag("HazardData", getHazardData(ForgeDirection.UNKNOWN).toNbt());
    }
}
