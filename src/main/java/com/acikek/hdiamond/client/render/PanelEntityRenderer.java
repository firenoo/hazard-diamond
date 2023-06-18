package com.acikek.hdiamond.client.render;

import com.acikek.hdiamond.HDiamond;
import com.acikek.hdiamond.client.HDiamondClient;
import com.acikek.hdiamond.core.HazardDiamond;
import com.acikek.hdiamond.core.quadrant.SpecificHazard;
import com.acikek.hdiamond.core.section.DiamondSection;
import com.acikek.hdiamond.entity.PanelEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class PanelEntityRenderer extends Render {

    public static final ResourceLocation PANEL_MODEL = HDiamond.id("block/panel");
    public static final float ICON_RATIO = 64.0f;
    public static final float ICON_SCALE = 1.0f / ICON_RATIO;

    public void renderPanel(int lightFront) {

//        matrices.translate(-1, 0, -0.5f + 1.0f / 32.0f);
//        matrices.translate(-1.5f, -0.5f, -0.5f);

//        modelRenderer.render(
//                matrices.peek(), vertexConsumers.getBuffer(RenderLayer.getCutout()),
//                null, BakedModelManagerHelper.getModel(modelManager, PANEL_MODEL),
//                0.0f, 1.0f, 1.0f, lightFront, OverlayTexture.DEFAULT_UV
//        );
    }

    public void renderIcon(DiamondSection<?> section, int x1, int y1) {
        var texture = section.getTexture();
        int x2 = x1 + texture.width();
        int y2 = y1 + texture.height();
        float u1 = texture.u() / 256.0f;
        float v1 = texture.v() / 256.0f;
        float u2 = (texture.u() + texture.width()) / 256.0f;
        float v2 = (texture.v() + texture.height()) / 256.0f;
        Tessellator tessellator = Tessellator.instance;

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, 0.0f, -1.0f);
        tessellator.addVertexWithUV(x2, y1, -0.5, u2, v1);
        tessellator.addVertexWithUV(x1, y1, -0.5, u1, v1);
        tessellator.addVertexWithUV(x1, y2, -0.5, u1, v2);
        tessellator.addVertexWithUV(x2, y2, -0.5, u2, v2);
        tessellator.draw();
    }

    public void renderIcons(HazardDiamond diamond) {
        GL11.glPushMatrix();
        GL11.glScalef(-ICON_SCALE, -ICON_SCALE, -ICON_SCALE);
        GL11.glTranslatef(-ICON_RATIO / 2.0f, -ICON_RATIO / 2.0f, -0.75f);
        renderIcon(diamond.fire().get(), 26, 9);
        renderIcon(diamond.health().get(), 10, 25);
        renderIcon(diamond.reactivity().get(), 42, 25);
        SpecificHazard specific = diamond.specific().get();
        if (specific != SpecificHazard.NONE) {
            var rad = specific == SpecificHazard.RADIOACTIVE;
            renderIcon(specific, 23 - (rad ? 1 : 0), 42 - (rad ? 2 : 0));
        }
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity, double xPos, double yPos, double zPos, float hx, float hy) {
        if (entity.ticksExisted == 0) {
            return;
        }
        // Sadly this needsto be done
        if (entity instanceof PanelEntity panelEntity) {
            GL11.glPushMatrix();
            GL11.glTranslated(xPos, yPos, zPos);
            GL11.glRotatef(hx, 0.0f, 1.0f, 0.0f);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            this.bindEntityTexture(panelEntity);
            float scale = 0.0625f;
            GL11.glScalef(scale, scale, scale);
            renderIcons(panelEntity.getHazardData(ForgeDirection.UNKNOWN).diamond());
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return Minecraft.getMinecraft().getTextureManager().getResourceLocation(0);
    }
}