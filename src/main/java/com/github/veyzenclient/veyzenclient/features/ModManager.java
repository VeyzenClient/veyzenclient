package com.github.veyzenclient.veyzenclient.features;

import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModManager {

    public static Map<String,Mod> mods = new HashMap<>();
    private static String basePackage = "com.github.veyzenclient.veyzenclient";

    public Mod getMod(String id){
        return mods.get(id);
    }

    public static void runButton(String runnableID){

    }

    public static ArrayList<Mod> getFavorites(){
        ArrayList<Mod> favorites = new ArrayList<>();
        for(Mod m : mods.values()){
            if(m.favorite) favorites.add(m);
        }
        return favorites;
    }

    public static void drawMods(boolean dummy){
        for(Map.Entry<String, com.github.veyzenclient.veyzenclient.features.Mod> m : ModManager.mods.entrySet()){
            if(m.getValue() instanceof ModHUD){
                ModHUD mh = (ModHUD) m.getValue();
                if(dummy){
                    mh.renderDummy();
                }else {
                    mh.render();
                }
            }
        }
    }

    public static void register() {
        new Reflections(basePackage)
                .getTypesAnnotatedWith(Register.class)
                .forEach(clazz -> {
                    if (Mod.class.isAssignableFrom(clazz)) {
                        try {
                            Mod m = (Mod) clazz.newInstance(); // or clazz.getDeclaredConstructor().newInstance() for modern Java
                            mods.put(m.id, m);
                            System.out.println("Registered Mod: " + m.name);
                        } catch (InstantiationException | IllegalAccessException e) {
                            throw new RuntimeException("Failed to instantiate mod: " + clazz.getName(), e);
                        }
                    }
                });
    }

}
