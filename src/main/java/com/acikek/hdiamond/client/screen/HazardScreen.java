package com.acikek.hdiamond.client.screen;

import com.acikek.hdiamond.HDiamond;
import com.acikek.hdiamond.client.HDiamondClient;
import com.acikek.hdiamond.core.HazardData;
import com.acikek.hdiamond.core.pictogram.Pictogram;
import com.acikek.hdiamond.core.quadrant.QuadrantValue;
import com.acikek.hdiamond.core.quadrant.SpecificHazard;
import com.acikek.hdiamond.core.section.DiamondSection;
import com.acikek.hdiamond.network.C2SUpdatePanelData;
import com.acikek.hdiamond.network.NetHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.IChatComponent;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * Base class for the gui editing a hazard diamond.
 */
public class HazardScreen extends GuiScreen {

    protected int x;
    protected int y;
    protected boolean movedCursor = false;
    protected boolean isEditable;
    protected HazardData originalData;
    protected HazardData newData;

    public HazardScreen(HazardData data, boolean isEditable) {
        this.originalData = data;
        this.newData = data.copy();
        this.isEditable = isEditable;
    }

    public void addQuadrant(QuadrantValue<?> quadrant, int id, int halfX, int halfY) {
        buttonList.add(new DiamondWidget.Quadrant(this, quadrant, id, halfX, halfY, 62));
    }

    public void addPictogram(Pictogram pictogram, int id, int halfX, int halfY) {
        buttonList.add(new DiamondWidget.PictogramLabel(this, pictogram, id, halfX, halfY, 66));
    }

    @Override
    public void initGui() {
        this.x = (this.width - 128) / 4;
        this.y = (this.height) / 4 - 2;
        addQuadrant(newData.diamond().fire(), 0, x + 16, y - 50);
        addQuadrant(newData.diamond().health(), 1, x, y - 34);
        addQuadrant(newData.diamond().reactivity(), 2, x + 32, y - 34);
        addQuadrant(newData.diamond().specific(), 3, x + 16, y - 18);
        for (int i = 0; i < Pictogram.values().length; i++) {
            Pictogram pictogram = Pictogram.values()[i];
            addPictogram(pictogram, 4 + i, x - 57 + i * 18, y + 3 + (i % 2 == 0 ? 18 : 0));
        }
    }

    public void renderTooltip(List<IChatComponent> text, int x, int y) {
        drawHoveringText(text, x, y, fontRendererObj);
    }

    public static void setTexture() {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().renderEngine.bindTexture(HDiamondClient.WIDGETS);
    }

    public void renderPanel() {
        drawTexturedModalRect(x, y - 50, 0, 0, 64, 64);
    }

    public void renderElement(DiamondSection<?> element, int x, int y) {
        DiamondSection.Texture texture = element.getTexture();
        drawTexturedModalRect(x, y, texture.u(), texture.v(), texture.width(), texture.height());
    }

    public void renderQuadrants() {
        renderElement(newData.diamond().fire().get(), x + 26, y - 41);
        renderElement(newData.diamond().health().get(), x + 10, y - 25);
        renderElement(newData.diamond().reactivity().get(), x + 42, y - 25);
        SpecificHazard specific = newData.diamond().specific().get();
        if (specific != SpecificHazard.NONE) {
            boolean rad = specific == SpecificHazard.RADIOACTIVE;
            renderElement(specific, x + 23 - (rad ? 1 : 0), y - 9 - (rad ? 2 : 0));
        }
    }

    public void renderPictogram(Pictogram pictogram, int x, int y) {
        float color = newData.pictograms().contains(pictogram) ? 1.0f : 0.5f;
        GL11.glColor4f(color, color, color, 1.0f);
        renderElement(pictogram, x, y);
    }

    public void renderPictograms() {
        for (int i = 0; i < Pictogram.values().length; i++) {
            Pictogram pictogram = Pictogram.values()[i];
            renderPictogram(pictogram, x - 56 + i * 18, y + 4 + (i % 2 == 0 ? 18 : 0));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawBackground(0);
        setTexture();
        GL11.glPushMatrix();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        renderPanel();
        renderQuadrants();
        renderPictograms();
        GL11.glPopMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int state) {
        super.mouseMovedOrUp(mouseX, mouseY, state);
        if (!movedCursor) {
            movedCursor = true;
        }
    }

    @Override
    public void onGuiClosed() {
        NetHandler.sendToServer(new C2SUpdatePanelData(newData));
    }
}
