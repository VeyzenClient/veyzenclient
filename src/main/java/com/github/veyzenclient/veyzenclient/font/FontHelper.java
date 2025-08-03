package com.github.veyzenclient.veyzenclient.font;

import com.github.veyzenclient.veyzenclient.VeyzenClient;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class FontHelper {

    private String font;

    public GlyphPageFontRenderer size15;
    public GlyphPageFontRenderer size20;
    public GlyphPageFontRenderer size25;
    public GlyphPageFontRenderer size30;
    public GlyphPageFontRenderer size35;
    public GlyphPageFontRenderer size40;

    public void init(){
        try {
            Font outfit = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(VeyzenClient.class.getResourceAsStream("/assets/veyzen/fonts/Outfit.ttf")));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(outfit);
            font = outfit.getFontName();
            size15 = GlyphPageFontRenderer.create(font, 15, true, true, true);
            size20 = GlyphPageFontRenderer.create(font, 20, true, true, true);
            size25 = GlyphPageFontRenderer.create(font, 25, true, true, true);
            size30 = GlyphPageFontRenderer.create(font, 30, true, true, true);
            size35 = GlyphPageFontRenderer.create(font, 35, true, true, true);
            size40 = GlyphPageFontRenderer.create(font, 40, true, true, true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }
}
