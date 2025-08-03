package com.github.veyzenclient.veyzenclient.features.impl.hud;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.config.settings.impl.ColorPicker;
import com.github.veyzenclient.veyzenclient.config.settings.impl.Switch;
import com.github.veyzenclient.veyzenclient.config.settings.impl.TextField;
import com.github.veyzenclient.veyzenclient.features.ModCategory;
import com.github.veyzenclient.veyzenclient.features.ModHUD;
import com.github.veyzenclient.veyzenclient.features.Register;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

@Register
public class FPSHUD extends ModHUD {

    public FPSHUD() {
        super(15, 15, 60, 20, "fps", "FPS HUD", "Displays current FPS.", new ResourceLocation(VeyzenClient.NAMESPACE, "icons/mods/fps.png"), ModCategory.QOL);

        addSetting(new Switch(true, "enabled", "Enabled", "Toggle FPS HUD", "General"));
        addSetting(new TextField("fpstextfield", "Prefix", "Text before FPS", "General", TextField.TextFilter.ALL, "FPS", 32));
        addSetting(new TextField("fpstextfield2", "Separator", "FPS Separator", "General", TextField.TextFilter.ALL, ":", 32));
        addSetting(new ColorPicker("fpscolor", "Text Color", "Color of the FPS text", "General", 1f, 1f, 1f, 1f));
        addSetting(new Switch(true, "showbg", "Show Background", "Toggle background rectangle", "General"));
        addSetting(new ColorPicker("bgcolor", "Background Color", "Solid background color", "General", 0f, 0f, 0f, 0.5f));
    }

    @Override
    public void render() {

        Minecraft mc = Minecraft.getMinecraft();

        String prefix = ((TextField) getSetting("fpstextfield")).getText();
        String separator = ((TextField) getSetting("fpstextfield2")).getText();
        Color textColor = ((ColorPicker) getSetting("fpscolor")).getColor();
        boolean showBackground = getSetting("showbg").getAsType(Switch.class).isEnabled();
        Color backgroundColor = ((ColorPicker) getSetting("bgcolor")).getColor();

        String text = prefix + separator + " " + Minecraft.getDebugFPS();
        int textWidth = mc.fontRendererObj.getStringWidth(text);
        int padding = 6;
        int w = textWidth + padding * 2;
        int h = 20;

        this.width = w;
        this.height = h;

        int x = getRenderX();
        int y = getRenderY();

        if (showBackground) {
            int bgColor = (backgroundColor.getAlpha() << 24)
                    | (backgroundColor.getRed() << 16)
                    | (backgroundColor.getGreen() << 8)
                    | backgroundColor.getBlue();
            Helper2D.drawRoundedRectangle(x, y, w, h, 4, bgColor, 0);
        }

        int color = (textColor.getAlpha() << 24)
			| (textColor.getRed() << 16)
			| (textColor.getGreen() << 8)
			| textColor.getBlue();

        VeyzenClient.INSTANCE.fontHelper.size20.drawString(text, x + w / 2f - VeyzenClient.INSTANCE.fontHelper.size20.getStringWidth(text) / 2f, y + (h - 8) / 2f, color);
    }
}
