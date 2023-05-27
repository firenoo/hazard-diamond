package com.acikek.hdiamond.client.render;

import com.acikek.hdiamond.HDiamond;
import com.acikek.hdiamond.client.HDiamondClient;
import com.acikek.hdiamond.core.HazardDiamond;
import com.acikek.hdiamond.core.quadrant.SpecificHazard;
import com.acikek.hdiamond.core.section.DiamondSection;
import com.acikek.hdiamond.entity.PanelEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class PanelEntityRenderer extends Render {

    public static final ResourceLocation PANEL_MODEL = HDiamond.id("block/panel");
    public static final float ICON_RATIO = 64.0f;
    public static final float ICON_SCALE = 1.0f / ICON_RATIO;

    public void render(PanelEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
    }

    public void renderPanel(int lightFront) {

//        matrices.translate(-1, 0, -0.5f + 1.0f / 32.0f);
//        matrices.translate(-1.5f, -0.5f, -0.5f);

//        modelRenderer.render(
//                matrices.peek(), vertexConsumers.getBuffer(RenderLayer.getCutout()),
//                null, BakedModelManagerHelper.getModel(modelManager, PANEL_MODEL),
//                0.0f, 1.0f, 1.0f, lightFront, OverlayTexture.DEFAULT_UV
//        );
    }

    public void renderIcon(VertexConsumer buffer, Matrix4f pos, Vec3f normal, DiamondSection<?> section, int x1, int y1, int light) {
        var texture = section.getTexture();
        int x2 = x1 + texture.width();
        int y2 = y1 + texture.height();
        float u1 = texture.u() / 256.0f;
        float v1 = texture.v() / 256.0f;
        float u2 = (texture.u() + texture.width()) / 256.0f;
        float v2 = (texture.v() + texture.height()) / 256.0f;

        float nx = normal.getX();
        float ny = normal.getY();
        float nz = normal.getZ(); // kiwi

        buffer.vertex(pos, x1, y1, 0.0f).color(0xFFFFFFFF).texture(u1, v1).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(nx, ny, nz).next();
        buffer.vertex(pos, x1, y2, 0.0f).color(0xFFFFFFFF).texture(u1, v2).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(nx, ny, nz).next();
        buffer.vertex(pos, x2, y2, 0.0f).color(0xFFFFFFFF).texture(u2, v2).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(nx, ny, nz).next();
        buffer.vertex(pos, x2, y1, 0.0f).color(0xFFFFFFFF).texture(u2, v1).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(nx, ny, nz).next();
    }

    public void renderIcons(HazardDiamond diamond, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int lightFront) {
        matrices.scale(-ICON_SCALE, -ICON_SCALE, -ICON_SCALE);
        matrices.translate(-ICON_RATIO / 2.0f, -ICON_RATIO / 2.0f, -0.75f);

        var entry = matrices.peek();
        Matrix4f pos = entry.getPositionMatrix();
        Matrix3f normal = entry.getNormalMatrix();
        Vec3f vec3f = new Vec3f(0, 1, 0);
        vec3f.transform(normal);

        var buffer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(HDiamondClient.WIDGETS));
        renderIcon(buffer, pos, vec3f, diamond.fire().get(), 26, 9, lightFront);
        renderIcon(buffer, pos, vec3f, diamond.health().get(), 10, 25, lightFront);
        renderIcon(buffer, pos, vec3f, diamond.reactivity().get(), 42, 25, lightFront);
        SpecificHazard specific = diamond.specific().get();
        if (specific != SpecificHazard.NONE) {
            var rad = specific == SpecificHazard.RADIOACTIVE;
            renderIcon(buffer, pos, vec3f, specific, 23 - (rad ? 1 : 0), 42 - (rad ? 2 : 0), lightFront);
        }
    }

    @Override
    public void doRender(Entity panelEntity, double xPos, double yPos, double zPos, float hx, float hy) {
        if (panelEntity.ticksExisted == 0) {
            return;
        }
        float lightFront = panelEntity.worldObj.getLightBrightness((int) xPos, (int) yPos, (int) zPos);

//        int lightFront = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getBlockPos());

        GL11.glPushMatrix();
        GL11.glTranslated(xPos, yPos, zPos);
        GL11.glRotatef(hx, 0.0f, 1.0f, 0.0f);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        this.bindEntityTexture(panelEntity);
//        matrices.multiply(Vec2f.POSITIVE_Y.getDegreesQuaternion(entity.getPitch()));
//        matrices.multiply(Vec2f.POSITIVE_Y.getDegreesQuaternion(180 - entity.getYaw()));

//        matrices.push();
//        renderPanel(matrices, vertexConsumers, lightFront);
//        matrices.pop();
        GL11.glScalef();

        if (HDiamondClient.config.renderFull) {
            matrices.push();
            renderIcons(entity.getHazardData().diamond(), matrices, vertexConsumers, lightFront);
            matrices.pop();
        }

        matrices.pop();

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return Minecraft.getMinecraft().getTextureManager().getResourceLocation(0);
    }
}