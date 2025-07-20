package com.github.veyzenclient.veyzenclient.config;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.config.settings.impl.Switch;
import com.github.veyzenclient.veyzenclient.features.Mod;
import com.github.veyzenclient.veyzenclient.font.GlyphPageFontRenderer;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class ConfigMod extends GuiButton {

    public Mod m;
    private int mouseX,mouseY;

    int logoX;
    int logoY;

    int settingX;
    int settingY;
    int settingW, settingH ;

    int toggleX;
    int toggleY;
    int toggleWidth;
    int toggleHeight;

    int favPosX;
    int favPosY;
    int favW;

    public ConfigMod(int buttonId, int x, int y, Mod mod) {
        super(buttonId, x, y,200,125, mod.name);
        m=mod;
        init();
    }

    public void init(){
        logoX = xPosition + width / 2 - 24;
        logoY = yPosition + 15;

        settingX = xPosition + 5;
        settingY = yPosition + 80;
        settingW = 36;
        settingH = 36;

        toggleX = settingX + settingW + 5;
        toggleY = settingY;
        toggleWidth = 140;
        toggleHeight = 36;

        favPosX = xPosition + width - 34;
        favPosY = yPosition + 2;
        favW = 32;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        if (enabled && visible) {
            Helper2D.drawRoundedRectangle(xPosition - 1, yPosition - 1, width + 2, height + 2, 4, VeyzenClient.bgcomp.getRGB(), 0);
            Helper2D.drawRoundedRectangle(xPosition + 2, yPosition + 2, width - 4, height - 4, 4, VeyzenClient.bg.getRGB(), 0);
            this.hovered = mouseX > xPosition && mouseX < xPosition + width && mouseY > yPosition && mouseY < yPosition + height;
            if(this.hovered){
                Color start = new Color(102,102,102,52), end = new Color(8,8,8,52);
                GlStateManager.enableBlend();
                GlStateManager.enableAlpha();
                Helper2D.drawGradientRectangle(xPosition,yPosition, width,height,start.getRGB(),end.getRGB());
                GlStateManager.disableBlend();
                GlStateManager.disableAlpha();
            }
            GlyphPageFontRenderer font = VeyzenClient.INSTANCE.fontHelper.size30;
            font.drawString(m.name,xPosition + 5,yPosition + 5,VeyzenClient.text.getRGB());

           boolean enabled = m.getSetting("enabled").getAsType(Switch.class).isEnabled();
            int color = enabled ? new Color(25,68,27).getRGB() : VeyzenClient.bgcomp.getRGB();
            String text = enabled ? "Enabled" : "Disabled";
            float xPos = toggleX + (float) toggleWidth / 2 - (float) font.getStringWidth(text) / 2;


            Minecraft.getMinecraft().getTextureManager().bindTexture(m.logo);
            drawModalRectWithCustomSizedTexture(logoX,logoY,0f,0f,48,48,48,48);
            Helper2D.drawRoundedRectangle(settingX,settingY,settingW,settingH,4,VeyzenClient.bgcomp.getRGB(),0);

            Helper2D.drawRoundedRectangle(favPosX,favPosY,favW,favW,4,VeyzenClient.bgcomp.getRGB(),0);
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("veyzen","icons/fav.png"));
            if(m.favorite){
                GlStateManager.color((float) 155 /255,(float) 170 /255,1f);
            }else {
                GlStateManager.color((float) 13 /255,(float) 13 /255,(float) 13 /255);
            }
            drawModalRectWithCustomSizedTexture(favPosX + 4,favPosY + 4,0f,0f,24,24,24,24);
            GlStateManager.resetColor();
            Helper2D.drawRoundedRectangle(toggleX,toggleY,toggleWidth,toggleHeight,4,color,0);
            font.drawString(text,xPos,toggleY + (float) font.getFontHeight() / 2,VeyzenClient.text.getRGB());

            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(VeyzenClient.NAMESPACE,"icons/settings.png"));
            drawModalRectWithCustomSizedTexture(settingX ,settingY,0f,0f,36,36,settingW,settingH);
        }
    }

    public void process(){
        if (mouseX >= settingX && mouseX <= settingX + settingW &&
                mouseY >= settingY && mouseY <= settingY + settingH) {
            System.out.println("Settings");
        }

        if(mouseX >= favPosX && mouseX <= favPosX + favW && mouseY >= favPosY && mouseY <= favPosY + favW){
            m.setFavorite(!m.favorite);
        }

        if (mouseX >= toggleX && mouseX <= toggleX + toggleWidth &&
                mouseY >= toggleY && mouseY <= toggleY + toggleHeight) {
            m.getSetting("enabled").getAsType(Switch.class).toggle();
            System.out.println("Toggled: " + m.getSetting("enabled").getAsType(Switch.class).isEnabled());
        }
    }

}
