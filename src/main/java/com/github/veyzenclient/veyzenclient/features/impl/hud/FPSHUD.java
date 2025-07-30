package com.github.veyzenclient.veyzenclient.features.impl.hud;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.config.settings.impl.ColorPicker;
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
		super(15, 15, 60, 20, "fps", "FPS HUD", "Displays current FPS.",
		new ResourceLocation(VeyzenClient.NAMESPACE, "icons/mods/fps.png"), ModCategory.QOL
        );
		addSetting(new TextField("fpstextfield", "Prefix", "Text before FPS", "General", TextField.TextFilter.ALL, "FPS", 32));
	    addSetting(new TextField("fpstextfield2", "Separator", "FPS Separator", "General", TextField.TextFilter.ALL, ":", 32));
		addSetting(new ColorPicker("fpscolor", "Color", "Text color", "General", 1f, 1f, 1f, 1f));
    }

    @Override
	public void render() {
        Minecraft mc = Minecraft.getMinecraft();

        String prefix = ((TextField) getSetting("fpstextfield")).getText();
	    String separator = ((TextField) getSetting("fpstextfield2")).getText();
        Color rgba = ((ColorPicker) getSetting("fpscolor")).getColor();
		int color = (rgba.getAlpha() << 24) | (rgba.getRed() << 16) | (rgba.getGreen() << 8) | rgba.getBlue();

        String text = prefix + separator + " " + mc.getDebugFPS();
        int textWidth = mc.fontRendererObj.getStringWidth(text);
        int padding = 6;
        int w = textWidth + padding * 2;
        int h = 18;

        this.width = w;
        this.height = h;

        int x = getRenderX();
        int y = getRenderY();

        int glass = new Color(1f, 1f, 1f, 0.08f).getRGB();
        int gradTop = new Color(1f, 1f, 1f, 0.05f).getRGB();
        int gradBot = new Color(1f, 1f, 1f, 0.01f).getRGB();
        int outline = new Color(1f, 1f, 1f, 0.12f).getRGB();

        Helper2D.drawRoundedRectangle(x, y, w, h, 6, glass, 0);
        Helper2D.drawGradientRectangle(x, y, w, h, gradTop, gradBot);
        Helper2D.drawOutlinedRectangle(x, y, w, h, 1, outline);
        Helper2D.drawCenteredString(mc.fontRendererObj, text, x + w / 2, y + (h - 8) / 2, color);
    }
}
