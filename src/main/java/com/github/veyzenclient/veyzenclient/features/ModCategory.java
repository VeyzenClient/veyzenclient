package com.github.veyzenclient.veyzenclient.features;


public enum ModCategory {

    ALL("All"),
    PVP("PvP"),
    HUD("HUD"),
    QOL("QoL"),
    SB("Skyblock");

    public String name;
    ModCategory(String name) {
        this.name = name;
    }

}
