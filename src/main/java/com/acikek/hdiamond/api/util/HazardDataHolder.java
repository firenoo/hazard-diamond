package com.acikek.hdiamond.api.util;

import com.acikek.hdiamond.core.HazardData;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * An object that contains a {@link HazardData} object.
 * The data itself is an implementor of this interface.
 * A TileEntity can implement this too.
 */
public interface HazardDataHolder {

    /**
     * @param dir The direction to query.
     * @return the contained hazard data, if it exists for that direction,
     *         null otherwise.
     */
    HazardData getHazardData(ForgeDirection dir);

    /**
     * @param dir The direction to query.
     * @return whether the data can be edited; false if no data.
     */
    boolean isEditable(ForgeDirection dir);

    /**
     * Set the data, if it's editable. The implementation is expected to
     * update its state with the new data.
     * @param data the data to replace
     * @param dir
     */
    void setHazardData(HazardData data, ForgeDirection dir);
}
