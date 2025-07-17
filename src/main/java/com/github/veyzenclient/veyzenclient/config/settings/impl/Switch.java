package com.github.veyzenclient.veyzenclient.config.settings.impl;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.config.settings.Setting;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;

public class Switch extends Setting {

    private boolean enabled;
    public Switch(boolean en,String i, String n, String d) {
        super(i, n, d,40,20);
        this.enabled = en;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public void setEnabled(boolean en){
        this.enabled = en;
    }

    public void toggle(){
        this.enabled = !this.enabled;
    }

    @Override
    public void render(int x, int y, int mX, int mY) {
        this.x = x;
        this.y = y;
        Helper2D.drawRoundedRectangle(x,y,width,height,10,VeyzenClient.bg.getRGB(),0);
        int xCircle = isEnabled() ? x + width / 2 + 2 : x + 2;
        Helper2D.drawCircle(xCircle + 8,y+ (float) height /2,8,0,360,VeyzenClient.fg.getRGB());
    }

    @Override
    public void mouseClick(int mouseX, int mouseY, int mouseButton) {
        if(isHovered(mouseX,mouseY)){
            this.toggle();
        }
    }

}
