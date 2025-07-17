package com.github.veyzenclient.veyzenclient.config.settings;

import com.github.veyzenclient.veyzenclient.config.settings.impl.Switch;

public abstract class Setting {

    public String id,name,desc;
    public int width,height;
    public int x,y;
    public Setting(String i,String n,String d,int width,int height){
        id = i;
        name = n;
        desc = d;
        this.width = width;
        this.height = height;
    }

    public abstract void render(int x,int y,int mX,int mY);
    public void mouseClick(int mouseX,int mouseY,int mouseButton){}
    public void mouseReleased(int mouseX,int mouseY,int state){}
    public void keyTyped(char key,int keycode){}


    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y+ height;
    }

    public <T extends Setting> T getAsType(Class<T> type){
            return type.cast(this);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
