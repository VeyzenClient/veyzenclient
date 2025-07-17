package com.github.veyzenclient.veyzenclient.config.buttons;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.features.ModCategory;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class CategoryButton extends GuiButton {

    public ModCategory category;
    public boolean click = false, selected = false;
    public CategoryButton(int buttonId, int x, int y, ModCategory category) {
        super(buttonId, x, y,75,25, category.name);
        this.category = category;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(enabled && visible){
            Helper2D.drawRoundedRectangle(xPosition,yPosition,width,height,4, click || selected? VeyzenClient.fg.getRGB() : VeyzenClient.bgcomp.getRGB(),0);
            hovered = mouseX > xPosition && mouseX < xPosition + width && mouseY > yPosition && mouseY < yPosition + height;
            VeyzenClient.INSTANCE.fontHelper.size25.drawString(
                    category.name,
                    xPosition + (float) width / 2 - (float) VeyzenClient.INSTANCE.fontHelper.size25.getStringWidth(category.name) / 2,
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
