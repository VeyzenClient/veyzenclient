package com.github.veyzenclient.veyzenclient.config.buttons;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.config.ConfigGUI;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class FavButton extends GuiButton {

    public ResourceLocation icon;
    public FavButton(int buttonId, int x, int y, int widthIn, int heightIn, ResourceLocation rl) {
        super(buttonId, x, y, widthIn, heightIn, "");
        icon = rl;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(enabled && visible){
            int x = xPosition + width / 2 - getImageWidth() / 2;
            int y = yPosition + height / 2 - getImageWidth() / 2;
            Helper2D.drawRoundedRectangle(xPosition,yPosition,width,height,4, VeyzenClient.bgcomp.getRGB(),0);
            Minecraft.getMinecraft().getTextureManager().bindTexture(icon);
            if(ConfigGUI.favOnly){
                GlStateManager.color((float) 155 /255,(float) 170 /255,1f);
            }else {
                GlStateManager.color((float) 13 /255,(float) 13 /255,(float) 13 /255);
            }
            drawModalRectWithCustomSizedTexture(x,y,0f,0f,getImageWidth(),getImageWidth(),getImageWidth(),getImageWidth());
        }
    }

    public int getImageWidth(){
        try {
            InputStream stream = Minecraft.getMinecraft().getResourceManager().getResource(icon).getInputStream();
            BufferedImage img = ImageIO.read(stream);
            return img.getWidth();
        }catch(IOException e){
            e.printStackTrace();
        }
        return -1;
    }
}
