package com.github.veyzenclient.veyzenclient.splash;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.utils.FontRenderer;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class CustomSplash {

    private static final int MAX = 6;
    private static int CUR = 0;
    private static String TEX = "";
    private static final ResourceLocation splash = new ResourceLocation("veyzen:splash.png");
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void update(int p, String t) {
        CUR = Math.min(p, MAX);
        TEX = t;

        Minecraft mc = Minecraft.getMinecraft();
        if (mc == null || mc.getTextureManager() == null) return;

        draw();
    }


    public static void draw() {


        ScaledResolution sr = new ScaledResolution(mc);
        int scaleFactor = sr.getScaleFactor();

        mc.getFramebuffer().framebufferClear();
        mc.getFramebuffer().createBindFramebuffer(mc.displayWidth, mc.displayHeight);
        mc.getFramebuffer().bindFramebuffer(true);

        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, sr.getScaledWidth(), sr.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);

        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Helper2D.drawPicture(0,0,sr.getScaledWidth(),sr.getScaledHeight(), VeyzenClient.text.getRGB(),splash);
        int startX = (int)(sr.getScaledWidth() * 0.15);
        int width = (int)(sr.getScaledWidth() * 0.70);
        int startY = sr.getScaledHeight() - 45;
        int height = 8;
        Helper2D.drawRoundedRectangle(startX,startY,width,height,3,VeyzenClient.bg.getRGB(),0);
        float c = (float) CUR / (float) MAX;
        float calc = c * width;
        Helper2D.drawRoundedRectangle(startX,startY,(int)calc,height,3,VeyzenClient.fg.getRGB(),0);
        String text = CUR + "/" + MAX + ": " + TEX;
        VeyzenClient.INSTANCE.fontHelper.size40.drawString(
                text,
                startX + (float) width / 2 - (float) VeyzenClient.INSTANCE.fontHelper.size40.getStringWidth(text) / 2,
                startY + height + 5,
                VeyzenClient.text.getRGB()
                );
        mc.getFramebuffer().unbindFramebuffer();
        mc.getFramebuffer().framebufferRender(sr.getScaledWidth() * scaleFactor, sr.getScaledHeight() * scaleFactor);
        updateDisplay();
    }

    private static void updateDisplay(){
        Display.update();
        Minecraft mc = Minecraft.getMinecraft();
        if (!mc.isFullScreen() && Display.wasResized()) {
            int i = mc.displayWidth;
            int j = mc.displayHeight;
            mc.displayWidth = Display.getWidth();
            mc.displayHeight = Display.getHeight();
            if (mc.displayWidth != i || mc.displayHeight != j) {
                if (mc.displayWidth <= 0) {
                    mc.displayWidth = 1;
                }

                if (mc.displayHeight <= 0) {
                    mc.displayHeight = 1;
                }

            }
        }
    }
}