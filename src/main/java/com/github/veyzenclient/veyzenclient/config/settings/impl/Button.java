package com.github.veyzenclient.veyzenclient.config.settings.impl;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.config.settings.Setting;
import com.github.veyzenclient.veyzenclient.features.ModManager;
import com.github.veyzenclient.veyzenclient.font.GlyphPageFontRenderer;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;

public class Button extends Setting {

    public String runnableID , text;
    public boolean click = false;
    public Button(String i, String n, String d,String runnableID,String text) {
        super(i, n, d, 60,20);
        this.runnableID = runnableID;
        this.text = text;
    }

    @Override
    public void render(int x, int y, int mX, int mY) {
        this.x = x;
        this.y = y;
        Helper2D.drawRoundedRectangle(x,y,width,height,4, click ? VeyzenClient.fg.getRGB() : VeyzenClient.bgcomp.getRGB(),0);
        GlyphPageFontRenderer font = VeyzenClient.INSTANCE.fontHelper.size20;
        int xPos = x + width/2 - font.getStringWidth(text) / 2;
        float yOffset = y + (height - font.getFontHeight()) / 2f;
        font.drawString(text, xPos, yOffset, VeyzenClient.text.getRGB());
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        click = false;
    }

    @Override
    public void mouseClick(int mouseX, int mouseY, int mouseButton) {
        if(isHovered(mouseX,mouseY)){
            click = true;
            System.out.println("Clicked");
            ModManager.runButton(runnableID);
        }
    }

    @Override
    public boolean isHovered(int mouseX, int mouseY) {
        if(mouseX > x && mouseX < x + width){
            if(mouseY > y && mouseY < y + height){
                return true;
            }
        }
        return false;
    }
}
