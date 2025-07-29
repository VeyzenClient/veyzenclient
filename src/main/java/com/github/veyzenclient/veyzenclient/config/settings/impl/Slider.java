package com.github.veyzenclient.veyzenclient.config.settings.impl;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.config.settings.Setting;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;
import org.lwjgl.input.Mouse;

public class Slider extends Setting {

    public float cur,max,min;
    public boolean dragging;
    public boolean hovered = false;
    public float step = 0.01f;
    public Slider(String i, String n, String d, String parent,float minValue,float maxValue,float defValue) {
        super(i, n, d, 140, 20,parent);
        this.cur = defValue;
        this.max = maxValue;
        this.min = minValue;
        this.step = 0.01f;
    }

    public Slider(String i, String n, String d, String parent,float minValue,float maxValue,float defValue, float step) {
        super(i, n, d, 140, 20,parent);
        this.cur = defValue;
        this.max = maxValue;
        this.min = minValue;
        this.step = step;
    }

    @Override
    public void render(int x, int y, int mX, int mY) {
        this.hovered = (mX >= x && mX <= x + width && mY >= y && mY <= y + height);
        Helper2D.drawRoundedRectangle(x,y,width,6,2, VeyzenClient.bgcomp.getRGB(),0);
        if(step >= 1.0){
            int totalSteps = (int) ((max - min) / step);
            for (int i = 0; i <= totalSteps; i++) {
                float dataValue = min + i * step;
                float percent = (dataValue - min) / (max - min);
                int tickX = x + (int) (percent * width);
                Helper2D.drawRectangle(tickX, y , 1, 6, VeyzenClient.fg.getRGB());
            }
        }

        cur = clamp(cur, min, max);
        cur = roundToStep(cur, step);

        float perc = ((cur - min) / (max - min)) * width;
        int pinX = x + (int) perc + 5;
        float centerY = y + height / 4f;
        float radius = height / 2f;
        Helper2D.drawCircle(pinX, centerY, radius, 0, 360, VeyzenClient.fg.getRGB());
        Helper2D.drawRoundedRectangle(x - 25,y - 1, 20,8,2,VeyzenClient.bgcomp.getRGB(),0);
        VeyzenClient.INSTANCE.fontHelper.size15.drawString(String.valueOf(cur),x - 24,y,VeyzenClient.text.getRGB());

        if (dragging) {
            float newPerc = (float) (mX - x) / width;
            cur = min + newPerc * (max - min);
            cur = clamp(cur, min, max);
            cur = roundToStep(cur, step);

        }
    }

    @Override
    public void mouseClick(int mouseX, int mouseY, int mouseButton) {
        super.mouseClick(mouseX, mouseY, mouseButton);
        if(hovered && mouseButton == 0){
            dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        dragging = false;
    }

    private float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    private float roundToStep(float value, float step) {
        return Math.round(value / step) * step;
    }
}
