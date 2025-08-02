package com.github.veyzenclient.veyzenclient.config.buttons;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;
import com.github.veyzenclient.veyzenclient.utils.ResolutionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class CloseButton extends GuiButton {

    public CloseButton(int buttonId, int x, int y) {
        super(buttonId, x, y,(int) ResolutionHelper.getXStatic(40), (int) ResolutionHelper.getXStatic(40), "");
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(enabled && visible){
            Helper2D.drawRoundedRectangle(xPosition,yPosition,width,height,2, VeyzenClient.bgcomp.getRGB(),0);
            int c = VeyzenClient.bg.getRGB();
            Helper2D.drawPicture(xPosition,yPosition,width,height,c,new ResourceLocation("veyzen","icons/cross.png"));
        }
    }

    public void process(){
        Minecraft.getMinecraft().displayGuiScreen(null);
    }
}
