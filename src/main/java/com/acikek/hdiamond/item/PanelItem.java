package com.acikek.hdiamond.item;

import com.acikek.hdiamond.HDiamond;
import com.acikek.hdiamond.core.HazardData;
import com.acikek.hdiamond.entity.PanelEntity;
import com.myname.mymodid.Tags;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class PanelItem extends Item {

    private static final PanelItem INSTANCE = new PanelItem();

    public PanelItem() {
        setUnlocalizedName(Tags.MODID + ".panel_item");
        setHasSubtypes(false);
        setMaxDamage(0);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int xPos, int yPos, int zPos, int side, float hx, float hy, float hz) {
        ForgeDirection dir = ForgeDirection.getOrientation(side);
        if (dir == ForgeDirection.DOWN || dir == ForgeDirection.UP) {
            return false;
        }
        if (!player.canPlayerEdit(xPos, yPos, zPos, side, stack)) {
            return false;
        } else {
            // Finally, we can create the entity after RIGOROUS checks. Logic from ItemHangingEntity
            PanelEntity entity = new PanelEntity(world, xPos, yPos, zPos, dir.ordinal());
            if (!world.isRemote && entity.onValidSurface()) {
                world.spawnEntityInWorld(entity);
            }
            return true;
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean show) {
        if (stack.hasTagCompound()) {
            NBTTagCompound nbt = stack.getTagCompound();
            if (nbt.hasKey("HazardData", Constants.NBT.TAG_COMPOUND)) {
                var data = HazardData.fromNbt(nbt.getCompoundTag("HazardData"));
                tooltip.addAll(data.getTooltip());
            }
        }
        super.addInformation(stack, player, tooltip, show);
    }

    public static void register() {
        GameRegistry.registerItem(INSTANCE, "panel_item", Tags.MODID);
    }

    @Override
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon(Tags.MODID + ":panel_item");
    }
}
