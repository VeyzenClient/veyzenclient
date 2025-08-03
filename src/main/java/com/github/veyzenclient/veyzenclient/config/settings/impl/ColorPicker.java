package com.github.veyzenclient.veyzenclient.config.settings.impl;

import com.github.veyzenclient.veyzenclient.config.settings.Setting;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class ColorPicker extends Setting {



    public float hue = 0f;
    public float saturation = 1f;
    public float brightness = 1f;
    public float alpha = 1f;

    private boolean draggingSB = false;
    private boolean draggingHue = false;
    private boolean draggingAlpha = false;
    private final int sbSize = 50;
    private final int hueHeight = 50;
    private final int alphaWidth = 50;

    public ColorPicker(String i, String n, String d,String parent,float h, float s, float b, float a) {
        super(i, n, d, 120, 140,parent);
        hue = h;
        saturation = s;
        brightness = b;
        alpha = a;
    }

    @Override
    public void render(int x, int y, int mX, int mY) {
        int hueX = x + sbSize + 10;
        int alphaY = y + sbSize + 10;

        for (int i = 0; i < sbSize; i++) {
            for (int j = 0; j < sbSize; j++) {
                Color c = Color.getHSBColor(hue, i / (float) sbSize, 1 - j / (float) sbSize);
                drawPixel(x + i, y + j, c.getRGB());
            }
        }
        Helper2D.drawRectangle(hueX - 1, y - 1, 12, hueHeight + 2, new Color(0,0,0,255).getRGB());
        for (int j = 0; j < hueHeight; j++) {
            Color c = Color.getHSBColor(j / (float) hueHeight, 1, 1);
            drawPixelRect(hueX, y + j, 10, 1, c.getRGB());
        }

        if(Mouse.isButtonDown(0)) {
            int hueY = y;

            int alphaX = x;

            if (mX >= x && mX <= x + sbSize && mY >= y && mY <= y + sbSize) {
                draggingSB = true;
                updateSB(mX - x, mY - y);
            } else if (mX >= hueX && mX <= hueX + 10 && mY >= hueY && mY <= hueY + hueHeight) {
                draggingHue = true;
                updateHue(mY - hueY);
            } else if (mX >= alphaX && mX <= alphaX + alphaWidth && mY >= alphaY && mY <= alphaY + 10) {
                draggingAlpha = true;
                updateAlpha(mX - alphaX);
            }

        }else {
            draggingSB = false;
            draggingHue = false;
            draggingAlpha = false;
        }

        Helper2D.drawRectangle(x - 1, alphaY - 1, alphaWidth + 2, 12, new Color(0,0,0,255).getRGB());
        for (int i = 0; i < alphaWidth; i++) {
            Color c = new Color(getColor().getRed(), getColor().getGreen(), getColor().getBlue(), (int) (255 * (i / (float) alphaWidth)));
            drawPixelRect(x + i, alphaY, 1, 10, c.getRGB());
        }
        Helper2D.drawRectangle(x + 5, y + sbSize + 25 , 15, 15, getColor().getRGB());
        drawIndicators(x, y);
    }

    private void drawIndicators(int x, int y) {
        int hueX = x + sbSize + 10;
        int alphaY = y + sbSize + 10;

        // 1. SB Cursor
        int sbCursorX = x + (int) (saturation * sbSize);
        int sbCursorY = y + (int) ((1 - brightness) * sbSize);
        Helper2D.drawCircle(sbCursorX, sbCursorY, 4, 0, 360, Color.WHITE.getRGB());

        // 2. Hue Indicator
        int hueBarY = y + (int) (hue * hueHeight);
        Helper2D.drawRectangle(hueX - 1, hueBarY - 1, 12, 2, Color.WHITE.getRGB());

        // 3. Alpha Indicator
        int alphaX = x + (int) (alpha * alphaWidth);
        Helper2D.drawRectangle(alphaX - 1, alphaY - 1, 2, 12, Color.WHITE.getRGB());
    }

    public Color getColor() {
        int rgb = Color.HSBtoRGB(hue, saturation, brightness);
        return new Color((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF, (int)(alpha * 255));
    }

    private void updateSB(int dx, int dy) {
        saturation = clamp(dx / (float) sbSize);
        brightness = clamp(1 - dy / (float) sbSize);
    }

    private void updateHue(int dy) {
        hue = clamp(dy / (float) hueHeight);
    }

    private void updateAlpha(int dx) {
        alpha = clamp(dx / (float) alphaWidth);
    }

    private float clamp(float val) {
        return Math.max(0f, Math.min(1f, val));
    }

    private void drawPixel(int x, int y, int color) {
        Gui.drawRect(x, y, x + 1, y + 1, color);
    }

    private void drawPixelRect(int x, int y, int width, int height, int color) {
        Gui.drawRect(x, y, x + width, y + height, color);
    }
}
