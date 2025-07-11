package com.github.veyzenclient.veyzenclient;

import com.github.veyzenclient.veyzenclient.utils.FontRenderer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.awt.*;

@Mod(modid = "veyzenclient", useMetadata=true)
public class VeyzenClient {

    public static Color bg = new Color(17,17,17),fg = new Color(155,170,255), text = new Color(239,240,244);
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e){
        FontRenderer.initFont();
    }
}
