package com.acikek.hdiamond.api;

import com.acikek.hdiamond.block.BlockWithHazardData;
import com.acikek.hdiamond.api.event.HazardScreenEdited;
import com.acikek.hdiamond.api.util.HazardDataHolder;
import com.acikek.hdiamond.core.HazardData;
import com.acikek.hdiamond.core.HazardDiamond;
import com.acikek.hdiamond.core.pictogram.Pictogram;
import com.acikek.hdiamond.core.quadrant.FireHazard;
import com.acikek.hdiamond.core.quadrant.HealthHazard;
import com.acikek.hdiamond.core.quadrant.Reactivity;
import com.acikek.hdiamond.core.quadrant.SpecificHazard;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IChatComponent;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * <h1>Hazard Diamond</h1>
 *
 * <h2>Overview</h2>
 *
 * <p>
 *     This is the main API class for <a href="https://modrinth.com/mod/hazard-diamond">Hazard Diamond</a>.
 *     It provides utility methods for actions such as fetching and creating {@link HazardData} objects, displaying them to
 *     clients, and listening for hazard panel updates.
 * </p>
 * <br>
 *
 * <p>
 *     The public {@code util} API package contains:
 *     <ul>
 *         <li>
 *             {@link HazardDataHolder} interface signifying that an object contains {@link HazardData}.
 *         </li>
 *     </ul>
 * </p>
 * <h2>Examples</h2>
 *
 * <pre>
 * {@code
 * // Create 0-2-1-R hazard data
 * HazardData data = HazardDiamondAPI.data(
 *     FireHazard.INFLAMMABLE,
 *     HealthHazard.HAZARDOUS,
 *     Reactivity.SENSITIVE,
 *     SpecificHazard.RADIOACTIVE
 * );
 * </pre>
 */
public final class HazardDiamondAPI {

    private HazardDiamondAPI() {}

    /**
     * @param diamond the four NFPA 704 quadrants
     * @param pictograms a set of GHS pictograms
     * @return the hazard data object
     */
    public static HazardData data(HazardDiamond diamond, Set<Pictogram> pictograms) {
        return new HazardData(diamond, pictograms);
    }

    /**
     * @see HazardDiamondAPI#data(HazardDiamond, Set)
     */
    public static HazardData data(HazardDiamond diamond, Pictogram... pictograms) {
        return data(diamond, Arrays.stream(pictograms).collect(Collectors.toSet()));
    }

    /**
     * @see HazardDiamondAPI#data(HazardDiamond, Set)
     */
    public static HazardData data(HazardDiamond diamond) {
        return data(diamond, Collections.emptySet());
    }

    /**
     * @see HazardDiamondAPI#data(HazardDiamond, Set)
     */
    public static HazardData data(FireHazard fire, HealthHazard health, Reactivity reactivity, SpecificHazard specific, Set<Pictogram> pictograms) {
        return data(new HazardDiamond(fire, health, reactivity, specific), pictograms);
    }

    /**
     * @see HazardDiamondAPI#data(HazardDiamond, Set)
     */
    public static HazardData data(FireHazard fire, HealthHazard health, Reactivity reactivity, SpecificHazard specific, Pictogram... pictograms) {
        return data(fire, health, reactivity, specific, Arrays.stream(pictograms).collect(Collectors.toSet()));
    }

    /**
     * @see HazardDiamondAPI#data(HazardDiamond, Set)
     */
    public static HazardData data(FireHazard fire, HealthHazard health, Reactivity reactivity, SpecificHazard specific) {
        return data(fire, health, reactivity, specific, Collections.emptySet());
    }

    /**
     * Appends {@code WAILA} data converted from the specified holder to the NBT compound.
     */
    public static void appendWailaServerData(NBTTagCompound nbt, HazardDataHolder holder) {
//        List<String> tooltips = holder.getHazardData().getTooltip().stream()
//                .map(IChatComponent.Serializer::func_150696_a)
//                .collect(Collectors.toList());
//        nbt.setString("WNumerals", tooltips.get(0));
//        nbt.setString("WPictograms", tooltips.get(1));
    }

    /**
     * Fetches {@code WAILA} data from an NBT compound and appends the text lines in some way.
     * @param lineAdder a function such as {@link ITooltip#addLine(Text)}
     */
    public static void appendWailaTooltip(NBTTagCompound nbt, Consumer<IChatComponent> lineAdder) {
        if (!nbt.hasKey("WNumerals")) {
            return;
        }
        lineAdder.accept(IChatComponent.Serializer.func_150699_a(nbt.getString("WNumerals")));
        lineAdder.accept(IChatComponent.Serializer.func_150699_a(nbt.getString("WPictograms")));
    }
}
