package com.github.veyzenclient.veyzenclient.config.settings.impl;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.config.settings.Setting;
import com.github.veyzenclient.veyzenclient.font.GlyphPageFontRenderer;

import java.awt.*;

public class Text extends Setting {

    public String text;
    public Color c;
    public Text(String i, String n, String parent,Color c, String text) {
        super(i, n, "", 20, 20,parent);
        this.text = text;
        this.c = c;
    }

    @Override
    public void render(int x, int y, int mX, int mY) {
        GlyphPageFontRenderer font = VeyzenClient.INSTANCE.fontHelper.size20;
        GlyphPageFontRenderer font1 = VeyzenClient.INSTANCE.fontHelper.size30;
        font1.drawString(name,x,y,c.getRGB());
        font.drawString(text,x + 5 + font1.getStringWidth(name),y + font.getFontHeight(),VeyzenClient.fg.getRGB());
    }
}
