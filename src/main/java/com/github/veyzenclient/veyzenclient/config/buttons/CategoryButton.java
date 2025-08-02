package com.github.veyzenclient.veyzenclient.config.buttons;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.features.ModCategory;
import com.github.veyzenclient.veyzenclient.font.GlyphPageFontRenderer;
import com.github.veyzenclient.veyzenclient.utils.ResolutionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class CategoryButton extends GuiButton {

    public ModCategory cat;
    public boolean selected = false;
    public CategoryButton(int buttonId, int x, int y,int w,int h, ModCategory cat) {
        super(buttonId, x, y, w,h,cat.name);
        this.cat = cat;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(visible && enabled){
            GlStateManager.resetColor();
            GlStateManager.color(1f,1f,1f,1f);
            float scale = (float)width/80;
            GlyphPageFontRenderer font = VeyzenClient.INSTANCE.fontHelper.size40;
            float xPos = (xPosition + ResolutionHelper.getXStatic(10));
            float yPos = (yPosition + (float)height / 2 - ((font.getFontHeight() * scale) / 2));
            font.drawScaledString(displayString,xPos,yPos,scale,selected ? VeyzenClient.fg.getRGB() : VeyzenClient.text.getRGB());
        }
    }
}
