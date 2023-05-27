package com.acikek.hdiamond.core.quadrant;

import com.acikek.hdiamond.core.section.QuadrantSection;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

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
    public EnumChatFormatting getTypeColor() {
        return EnumChatFormatting.WHITE;
    }

    @Override
    public IChatComponent getSymbol() {
        IChatComponent text;
        switch (this) {
            case NONE -> text = new ChatComponentText("");
            case REACTS_WITH_WATER -> text = new ChatComponentText(EnumChatFormatting.STRIKETHROUGH + "W");
            case OXIDIZER -> text = new ChatComponentText("OX");
            case SIMPLE_ASPHYXIANT -> text = new ChatComponentText("SA");
            case RADIOACTIVE -> text = new ChatComponentText("R");
            default -> throw new IllegalArgumentException();
        }
        return text.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.WHITE));
    }

    @Override
    public EnumChatFormatting getLevelColor() {
        switch (this) {
            case NONE -> {
                return EnumChatFormatting.GRAY;
            }
            case REACTS_WITH_WATER -> {
                return EnumChatFormatting.DARK_AQUA;
            }
            case OXIDIZER -> {
                return EnumChatFormatting.RED;
            }
            case SIMPLE_ASPHYXIANT -> {
                return EnumChatFormatting.GOLD;
            }
            case RADIOACTIVE -> {
                return EnumChatFormatting.DARK_GREEN;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public SpecificHazard scroll(boolean reverse) {
        return null;
    }
}
