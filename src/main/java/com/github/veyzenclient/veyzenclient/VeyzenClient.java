package com.github.veyzenclient.veyzenclient;

import com.github.veyzenclient.veyzenclient.config.pages.ConfigScreen;
import com.github.veyzenclient.veyzenclient.features.ModManager;
import com.github.veyzenclient.veyzenclient.font.FontHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

@Mod(modid = "veyzenclient", useMetadata=true)
public class VeyzenClient {

    @Mod.Instance()
    public static VeyzenClient INSTANCE;

    public static final String NAMESPACE = "veyzen";
    public static final String modName = "Veyzen";
    public static final String modVersion = "1.0-beta [1.8.9]";
    public static KeyBinding configKey;
    public static Color bg = new Color(217,217,217), bgcomp = new Color(183,183,183),fg = new Color(155,170,255), text = new Color(12,12,12);
    public FontHelper fontHelper;
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e){
        fontHelper = new FontHelper();
        fontHelper.init();
        configKey = new KeyBinding("Open Config Menu", Keyboard.KEY_RSHIFT,"Veyzen Client");
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent e){
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(fontHelper);
        ModManager.register();
        ClientRegistry.registerKeyBinding(configKey);
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if(Keyboard.isKeyDown(configKey.getKeyCode()))
            Minecraft.getMinecraft().displayGuiScreen(new ConfigScreen());
    }

    @SubscribeEvent
    public void onDraw(RenderGameOverlayEvent.Post e){
        ModManager.drawMods(false);
    }

}
