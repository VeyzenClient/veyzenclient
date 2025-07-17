package com.github.veyzenclient.veyzenclient;

import com.github.veyzenclient.veyzenclient.config.ConfigGUI;
import com.github.veyzenclient.veyzenclient.features.ModHUD;
import com.github.veyzenclient.veyzenclient.features.ModManager;
import com.github.veyzenclient.veyzenclient.font.FontHelper;
import com.github.veyzenclient.veyzenclient.utils.FontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.Map;

@Mod(modid = "veyzenclient", useMetadata=true)
public class VeyzenClient {

    @Mod.Instance()
    public static VeyzenClient INSTANCE;

    public static final String NAMESPACE = "veyzen";
    public static final String modID = "veyzen";
    public static final String modName = "Veyzen";
    public static final String modVersion = "1.0-beta [1.8.9]";

    public static Color bg = new Color(13,13,13), bgcomp = new Color(26,26,26),fg = new Color(155,170,255), text = new Color(239,240,244);
    public FontHelper fontHelper;
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e){
        FontRenderer.initFont();
        fontHelper = new FontHelper();
        fontHelper.init();
    }

    @Mod.EventHandler
    public void preInit(FMLInitializationEvent e){
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(fontHelper);
        ModManager.register();
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e){
        if(e.message.getUnformattedText().contains("test")){
            Minecraft.getMinecraft().displayGuiScreen(new ConfigGUI());
        }
    }

    @SubscribeEvent
    public void onDraw(RenderGameOverlayEvent.Post e){
        ModManager.drawMods(false);
    }

}
