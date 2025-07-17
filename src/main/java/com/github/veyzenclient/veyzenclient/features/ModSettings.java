package com.github.veyzenclient.veyzenclient.features;

import com.github.veyzenclient.veyzenclient.config.settings.Setting;

import java.util.ArrayList;

public class ModSettings {

    public ArrayList<Setting> settings = new ArrayList<>();

    public void addSetting(Setting s) {
        settings.add(s);
    }

    public Setting getSetting(String id) {
        for (Setting s : settings) {
            if (s.id.equals(id)) return s;
        }
        return null;
    }

}
