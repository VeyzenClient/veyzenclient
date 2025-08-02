package com.github.veyzenclient.veyzenclient.config.pages;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import static com.github.veyzenclient.veyzenclient.utils.ResolutionHelper.getXStatic;
import static com.github.veyzenclient.veyzenclient.utils.ResolutionHelper.getYStatic;

public class GlobalSettings extends GuiScreen {

    public ScaledResolution sr;
    public float wWindow, hWindow,xWindow,yWindow;

    @Override
    public void initGui() {
        super.initGui();
        mc = Minecraft.getMinecraft();
        sr = new ScaledResolution(mc);
        loadConstants();
    }

    private void loadConstants(){
        wWindow = getXStatic(1200);
        hWindow = getYStatic(700);
        xWindow = (float) (sr.getScaledWidth_double() / 2 - wWindow / 2);
        yWindow = (float) (sr.getScaledHeight_double() / 2 - hWindow / 2);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Helper2D.drawRoundedRectangle((int) xWindow, (int) yWindow, (int) wWindow, (int) hWindow,4, VeyzenClient.bg.getRGB(),0);
        super.drawScreen(mouseX, mouseY, partialTicks);

    }
}
