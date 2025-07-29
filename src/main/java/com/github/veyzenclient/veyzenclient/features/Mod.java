package com.github.veyzenclient.veyzenclient.features;

import com.github.veyzenclient.veyzenclient.config.settings.Setting;
import com.github.veyzenclient.veyzenclient.config.settings.impl.Switch;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;

public class Mod {

    public String id,name,description;
    public ResourceLocation logo;
    public ModSettings settings = new ModSettings();
    public ArrayList<ModCategory> categories = new ArrayList<ModCategory>();
    public boolean favorite = false;

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Mod(String id, String name, String desc, ResourceLocation logo, ModCategory... categories){
        this.id = id;
        this.name = name;
        this.description = desc;
        this.logo = logo;
        this.categories.addAll(Arrays.asList(categories));
        this.categories.add(ModCategory.ALL);
        this.settings.addSetting(new Switch(true,"enabled","Enabled","Toggle Whether the Mod is Enabled/Disabled","General"));
    }

    public Setting getSetting(String id){
        return this.settings.getSetting(id);
    }
    protected void addSetting(Setting s){
        settings.addSetting(s);
    }
    public  ArrayList<Setting> getSettings(){
        return settings.settings;
    }

    public void setSettings(ModSettings setting) {
        settings = setting;
    }



}
