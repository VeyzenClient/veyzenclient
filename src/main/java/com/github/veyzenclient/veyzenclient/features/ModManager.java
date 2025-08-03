package com.github.veyzenclient.veyzenclient.features;

import com.github.veyzenclient.veyzenclient.config.settings.Setting;
import com.github.veyzenclient.veyzenclient.config.settings.impl.ColorPicker;
import com.github.veyzenclient.veyzenclient.config.settings.impl.Keybind;
import com.github.veyzenclient.veyzenclient.config.settings.impl.Switch;
import org.lwjgl.input.Keyboard;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ModManager {

    public static Map<String, Mod> mods = new HashMap<>();
    private static final String basePackage = "com.github.veyzenclient.veyzenclient";
    public static ArrayList<Setting> globalSettings = new ArrayList<>();
    public Mod getMod(String id) {
        return mods.get(id);
    }

    public static void addGlobalSettings(){
        globalSettings.add(new ColorPicker("bg","Background Color","background for veyzen theme","Theme",0f,0f,0.85f,1f));
        globalSettings.add(new ColorPicker("bgc","Background 2 Color","2nd background for veyzen theme","Theme",0f,0f,0.72f,1f));
        globalSettings.add(new ColorPicker("fg","Foreground Color","foreground for veyzen theme","Theme",0.64f,0.39f,1f,1f));
        globalSettings.add(new ColorPicker("tc","Text Color","text color for veyzen theme","Theme",0f,0f,0.5f,1f));
        globalSettings.add(new Keybind("menu","Config Key","Key to open config menu","General", Keyboard.KEY_RSHIFT));
    }

    public static void runButton(String runnableID){}

    public static ArrayList<Mod> getFavorites() {
        ArrayList<Mod> favorites = new ArrayList<>();
        for (Mod m : mods.values()) {
            if (m.favorite) {
                favorites.add(m);
            }
        }
        return favorites;
    }

    public static void drawMods(boolean dummy) {
        for (Map.Entry<String, Mod> m : mods.entrySet()) {
            if(!m.getValue().getSetting("enabled").getAsType(Switch.class).isEnabled()) continue;
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
