package com.github.veyzenclient.veyzenclient.config.settings.impl;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.config.settings.Setting;
import com.github.veyzenclient.veyzenclient.font.GlyphPageFontRenderer;

import java.awt.*;

public class Text extends Setting {

    public String text;
    public Color c;
    public Text(String i, String n, Color c, String text) {
        super(i, n, "", 20, 20);
        this.text = text;
        this.c = c;
    }

    @Override
    public void render(int x, int y, int mX, int mY) {
        GlyphPageFontRenderer font = VeyzenClient.INSTANCE.fontHelper.size20;
        font.drawString(name,x,y,c.getRGB());
        font.drawString(text,x,y + font.getFontHeight(),c.getRGB());
    }
}
