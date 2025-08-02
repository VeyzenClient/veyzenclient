package com.github.veyzenclient.veyzenclient.features;

import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ModManager {

    public static Map<String, Mod> mods = new HashMap<>();
    private static String basePackage = "com.github.veyzenclient.veyzenclient";

    public Mod getMod(String id) {
        return mods.get(id);
    }

    public static void runButton(String runnableID){}

    public static ArrayList<Mod> getFavorites() {
        ArrayList<Mod> favorites = new ArrayList<Mod>();
        for (Mod m : mods.values()) {
            if (m.favorite) {
                favorites.add(m);
            }
        }
        return favorites;
    }

    public static void drawMods(boolean dummy) {
        for (Map.Entry<String, Mod> m : mods.entrySet()) {
            if (m.getValue() instanceof ModHUD) {
                ModHUD mh = (ModHUD) m.getValue();
                if (dummy) {
                    mh.renderDummy();
                } else {
                    mh.render();
                }
            }
        }
    }

    public static void register() {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Register.class);

        for (Class<?> clazz : annotated) {
            if (Mod.class.isAssignableFrom(clazz)) {
                try {
                    Mod m = (Mod) clazz.newInstance();
                    mods.put(m.id, m);
                    System.out.println("Registered Mod: " + m.name);
                } catch (InstantiationException e) {
                    throw new RuntimeException("Failed to instantiate mod: " + clazz.getName(), e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Illegal access for mod: " + clazz.getName(), e);
                }
            }
        }
    }
}
