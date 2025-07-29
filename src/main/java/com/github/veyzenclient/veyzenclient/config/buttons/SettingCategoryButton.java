package com.github.veyzenclient.veyzenclient.config.buttons;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.config.ModConfigGUI;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import java.util.Objects;

public class SettingCategoryButton extends GuiButton {

    public boolean click = false, selected = false;
    public SettingCategoryButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y,75,25, buttonText);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(enabled && visible){
            if(Objects.equals(ModConfigGUI.selectedCategory, displayString)){
                selected = true;
            }else {
                selected = false;
            }
            Helper2D.drawRoundedRectangle(xPosition,yPosition,width,height,4, click || selected? VeyzenClient.fg.getRGB() : VeyzenClient.bgcomp.getRGB(),0);
            hovered = mouseX > xPosition && mouseX < xPosition + width && mouseY > yPosition && mouseY < yPosition + height;
            VeyzenClient.INSTANCE.fontHelper.size25.drawString(
                    displayString,
                    xPosition + (float) width / 2 - (float) VeyzenClient.INSTANCE.fontHelper.size25.getStringWidth(displayString) / 2,
                    yPosition + (float) VeyzenClient.INSTANCE.fontHelper.size25.getFontHeight() /2,
                    VeyzenClient.text.getRGB()
            );
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if(hovered){
            click = true;
        }
        return super.mousePressed(mc, mouseX, mouseY);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        super.mouseReleased(mouseX, mouseY);
        click = false;
    }
}
