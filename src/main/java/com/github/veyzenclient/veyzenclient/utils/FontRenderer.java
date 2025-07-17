package com.github.veyzenclient.veyzenclient.utils;

import net.minecraft.client.renderer.GlStateManager;
public class FontRenderer {

    public static UnicodeFontRenderer ufc;
    public static void initFont(){
        ufc = UnicodeFontRenderer.getFontOnPC("Arial",35);
    }


    public static void drawString(String text,int x,int y,int width,int color) {
        GlStateManager.pushMatrix();
        float scale = (float)width/ufc.getStringWidth(text);
        if(scale >1f) scale = 1f;
        GlStateManager.translate(x, y, 0);
        GlStateManager.scale(scale, scale, scale);
        ufc.drawString(text, (float)0, (float)0, color);
        GlStateManager.popMatrix();

    }

    public static void drawStringWithShadow(String text,int x,int y,int width,int color) {
        GlStateManager.pushMatrix();
        float scale = (float)width/ufc.getStringWidth(text);
        if(scale >1f) scale = 1f;
        GlStateManager.translate(x, y, 0);
        GlStateManager.scale(scale, scale, scale);
        ufc.drawStringWithShadow(text, (float)0, (float)0, color);
        GlStateManager.popMatrix();
    }

    public static void drawCenteredString(String text,int x,int y,int width,int color) {
        GlStateManager.pushMatrix();
        float scale = (float)width/ufc.getStringWidth(text);
        if(scale >1f) scale = 1f;
        GlStateManager.translate(x, y, 0);
        GlStateManager.scale(scale, scale, scale);
        ufc.drawCenteredString(text, (float)0, (float)0, color);
        GlStateManager.popMatrix();
    }

    public static void drawCenteredStringWithShadow(String text,int x,int y,int width,int color) {
        GlStateManager.pushMatrix();
        float scale = (float)width/ufc.getStringWidth(text);
        if(scale >1f) scale = 1f;
        GlStateManager.translate(x, y, 0);
        GlStateManager.scale(scale, scale, scale);
        ufc.drawCenteredStringWithShadow(text, (float)0, (float)0, color);
        GlStateManager.popMatrix();
    }


}
