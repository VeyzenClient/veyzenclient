package com.github.veyzenclient.veyzenclient.config.settings.impl;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.config.settings.Setting;
import com.github.veyzenclient.veyzenclient.font.GlyphPageFontRenderer;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;
import org.lwjgl.input.Keyboard;

public class Keybind extends Setting {

    public int keycode;
    public transient boolean focused;
    public transient String text;
    public Keybind(String i, String n, String d, int defKey) {
        super(i, n, d, 60, 20);
        this.keycode = defKey;
        this.text = Keyboard.getKeyName(keycode);
    }

    @Override
    public void render(int x, int y, int mX, int mY) {
        this.x = x;
        this.y = y;
        Helper2D.drawRoundedRectangle(x,y,width,height,4, focused ? VeyzenClient.fg.getRGB() : VeyzenClient.bgcomp.getRGB(),0);
        GlyphPageFontRenderer font = VeyzenClient.INSTANCE.fontHelper.size20;
        int xPos = x + width/2 - font.getStringWidth(text) / 2;
        float yOffset = y + (height - font.getFontHeight()) / 2f;
        font.drawString(text, xPos, yOffset, VeyzenClient.text.getRGB());
    }

    @Override
    public void keyTyped(char key, int keycode) {
        super.keyTyped(key, keycode);
        if(this.focused){
            if(this.keycode == Keyboard.KEY_ESCAPE){
                this.keycode = Keyboard.KEY_NONE;
                this.text = "NONE";
            }
            this.keycode = keycode;
            this.text = Keyboard.getKeyName(keycode);
            this.focused = false;
        }
    }

    @Override
    public void mouseClick(int mouseX, int mouseY, int mouseButton) {
        super.mouseClick(mouseX, mouseY, mouseButton);
        if(this.isHovered(mouseX,mouseY)){
            this.focused = true;
            this.text = "Press any Key, ESC for none";
        }
    }
}
