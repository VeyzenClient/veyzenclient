package com.github.veyzenclient.veyzenclient.config.buttons;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.config.settings.impl.Switch;
import com.github.veyzenclient.veyzenclient.features.Mod;
import com.github.veyzenclient.veyzenclient.font.GlyphPageFontRenderer;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;
import com.github.veyzenclient.veyzenclient.utils.ResolutionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class ModButton extends GuiButton {

    public Mod m;
    public ModButton(int buttonId, int x, int y, Mod m) {
        super(buttonId, x, y, (int) ResolutionHelper.getXStatic(267), (int) ResolutionHelper.getYStatic(200), m.name);
        this.m = m;
    }

    private float toggleX,toggleY,toggleW,toggleH;
    private float favX,favY,favW,favH;
    private int mX,mY;

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(this.enabled && visible){
            mX = mouseX;
            mY = mouseY;
            Helper2D.drawRoundedRectangle(xPosition,yPosition,width,height,4, VeyzenClient.bgcomp.getRGB(),0);
            GlyphPageFontRenderer font = VeyzenClient.INSTANCE.fontHelper.size40;
            float xPos = xPosition + ResolutionHelper.getXStatic(8);
            float yPos = yPosition + ResolutionHelper.getYStatic(12);
            float scale = width/267f;
            font.drawScaledString(m.name,xPos,yPos,scale,VeyzenClient.text.getRGB());
            float logoX = xPosition + ResolutionHelper.getXStatic(85);
            float logoY = yPosition + ResolutionHelper.getYStatic(46);
            float logoW = ResolutionHelper.getXStatic(96);
            Helper2D.drawPicture((int) logoX, (int) logoY, (int) logoW, (int) logoW,VeyzenClient.fg.getRGB(),m.logo);
            toggleX = xPosition + ResolutionHelper.getXStatic(6);
            toggleY = yPosition + ResolutionHelper.getYStatic(147);
            toggleW = ResolutionHelper.getXStatic(200);
            toggleH = ResolutionHelper.getYStatic(50);
            int c = m.settings.getSetting("enabled").getAsType(Switch.class).isEnabled() ? VeyzenClient.fg.getRGB() : VeyzenClient.bg.getRGB();
            String text = m.settings.getSetting("enabled").getAsType(Switch.class).isEnabled() ? "Enabled" : "Disabled";

            Helper2D.drawRoundedRectangle((int) toggleX, (int) toggleY, (int) toggleW, (int) toggleH,4,c,0);
            int xText = (int) (toggleX + toggleW / 2 - ((font.getStringWidth(text) * scale) / 2));
            font.drawScaledString(text,xText,toggleY + toggleH / 4,scale,VeyzenClient.text.getRGB());
            favX = toggleX + toggleW + ResolutionHelper.getXStatic(6);
            favY = yPosition + ResolutionHelper.getYStatic(147);
            favH = ResolutionHelper.getYStatic(50);
            favW = ResolutionHelper.getXStatic(50);
            Helper2D.drawRoundedRectangle((int) favX, (int) favY, (int) favW, (int) favH,4,VeyzenClient.bg.getRGB(),0);
            int c1 = m.favorite ? VeyzenClient.fg.getRGB() : VeyzenClient.bgcomp.getRGB();
            Helper2D.drawPicture((int) favX, (int) favY, (int) favW, (int) favH,c1,new ResourceLocation("veyzen","icons/fav.png"));
        }
    }

    public void process(){
        if(mX > toggleX && mX < toggleX + toggleW){
            if(mY > toggleY && mY < toggleY + toggleH){
                this.m.getSetting("enabled").getAsType(Switch.class).toggle();
            }
        }
        if(mX > favX && mX < favX + favW){
            if(mY > favY && mY < favY + favH){
                m.setFavorite(!m.favorite);
            }
        }
    }
}
