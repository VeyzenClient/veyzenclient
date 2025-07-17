package com.github.veyzenclient.veyzenclient.features;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public abstract class ModHUD extends Mod{

    public float xPercentage,yPercentage;
    public int width,height;
    public ModHUD(float xPer,float yPer,int w,int h,String id, String name, String desc, ResourceLocation logo, ModCategory... categories) {
        super(id, name, desc, logo, categories);
        this.xPercentage = xPer;
        this.yPercentage = yPer;
        this.width = w;
        this.height = h;
    }

    public int getRenderX(){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        return (int) (sr.getScaledWidth_double() * (xPercentage/100));
    }

    public int getRenderY(){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        return (int) (sr.getScaledHeight() * (yPercentage/100));
    }

    public void setPosition(int xReal,int yReal){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        this.xPercentage = (float) ((float)xReal / sr.getScaledWidth_double());
        this.yPercentage = (float) ((float)yReal / sr.getScaledHeight_double());
    }

    public abstract void render();

    public void renderDummy() {
        render();
    }

}
