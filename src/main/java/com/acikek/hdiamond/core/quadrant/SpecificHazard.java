package com.acikek.hdiamond.core.quadrant;

import com.acikek.hdiamond.core.section.QuadrantSection;
import net.minecraft.util.Formatting;

/**
 * Describes special hazards pertaining to a material.
 * Source: NFPA, <em>Fire Protection on Hazardous Materials</em>, Standard 704, 1990
 */
public enum SpecificHazard implements QuadrantSection<SpecificHazard> {
    /**
     * No specific hazard to denote.
     */
    NONE,
    /**
     * Reacts with water in an unusual or dangerous manner.
     */
    REACTS_WITH_WATER,
    /**
     * Allows chemicals to burn without air supply.
     */
    OXIDIZER,
    /**
     * Denotes a "simple asphyxiant gas," or one that alters the normal oxygen concentration in breathable air.
     */
    SIMPLE_ASPHYXIANT,
    /**
     * Decays radioactively.
     */
    RADIOACTIVE;

    @Override
    public SpecificHazard getValue() {
        return this;
    }

    @Override
    public Texture getTexture() {
        return this == RADIOACTIVE
                ? new Texture(256 - 20, 256, 20, 20)
                : new Texture(64 + (ordinal() - 1) * 18, 42, 18, 14);
    }

    @Override
    public String getType() {
        return "quadrant.hdiamond.specific";
    }

    @Override
    public Formatting getTypeColor() {
        return Formatting.WHITE;
    }
}
