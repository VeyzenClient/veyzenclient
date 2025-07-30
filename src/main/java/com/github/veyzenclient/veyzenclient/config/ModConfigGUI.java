package com.github.veyzenclient.veyzenclient.config;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.config.buttons.SettingCategoryButton;
import com.github.veyzenclient.veyzenclient.config.settings.Setting;
import com.github.veyzenclient.veyzenclient.config.settings.impl.Slider;
import com.github.veyzenclient.veyzenclient.config.settings.impl.Text;
import com.github.veyzenclient.veyzenclient.features.Mod;
import com.github.veyzenclient.veyzenclient.font.GlyphPageFontRenderer;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModConfigGUI extends GuiScreen {

    public int menuWidth = 840;
    public int menuHeight = 480;
    public Map<String, List<Setting>> modSettings = new HashMap<>();
    public List<Setting> renderSettings = new ArrayList<>();
    public Mod mod;
    public ScaledResolution sr;
    public static String selectedCategory = "";
    public int scale;
    public ModConfigGUI(Mod m){
        mod = m;
        this.scale = Minecraft.getMinecraft().gameSettings.guiScale;
        Minecraft.getMinecraft().gameSettings.guiScale = 1;
    }

    @Override
    public void onGuiClosed() {
        Minecraft.getMinecraft().gameSettings.guiScale = this.scale;
    }

    @Override
    public void initGui() {
        mc = Minecraft.getMinecraft();
        sr = new ScaledResolution(mc);
        super.initGui();
        int i = 0;
        int xStart = sr.getScaledWidth() / 2 - menuWidth / 2 + 10;
        int yStart = sr.getScaledHeight() / 2 - menuHeight / 2 + 10;
        int xOffset = 85;
        for(Setting s : mod.getSettings()){
            if(!modSettings.containsKey(s.category)){
                i++;
                ArrayList<Setting> list = new ArrayList<>();
                list.add(s);
                modSettings.put(s.category,list);
                this.buttonList.add(new SettingCategoryButton(500 + i,xStart + (i * xOffset),yStart,s.category));
            }else {
                List<Setting> list = modSettings.get(s.category);
                list.add(s);
                modSettings.replace(s.category,list);
            }
        }
        updateModSettings();
    }

    private void updateModSettings() {
        renderSettings.clear();
        if(selectedCategory.isEmpty()){
            selectedCategory = modSettings.entrySet().stream().findFirst().get().getKey();
        }

        renderSettings.addAll(modSettings.get(selectedCategory));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.pushMatrix();
        int xCenter = sr.getScaledWidth() / 2;
        int yCenter = sr.getScaledHeight() / 2;
        Helper2D.drawRoundedRectangle(xCenter - menuWidth / 2,yCenter- menuHeight / 2,menuWidth,menuHeight,4,VeyzenClient.bg.getRGB(),0);
        int yOff = 0;
        int xStart = sr.getScaledWidth() / 2 - menuWidth / 2 + 10;
        int yStart = sr.getScaledHeight() / 2 - menuHeight / 2 + 45;
        for(Setting s : renderSettings){
            int height = Math.max(s.height,30);
            drawSetting(s,xStart,yStart + yOff,menuWidth - 20,height,mouseX,mouseY);
            yOff += height + 10;
        }
        GlStateManager.popMatrix();
        super.drawScreen(mouseX,mouseY,partialTicks);
    }

    public void drawSetting(Setting s,int x,int y,int w,int h,int mouseX,int mouseY) {
        Helper2D.drawRoundedRectangle(x, y, w, h, 4, VeyzenClient.bgcomp.getRGB(), 0);
        GlyphPageFontRenderer font = VeyzenClient.INSTANCE.fontHelper.size25;
        GlyphPageFontRenderer font1 = VeyzenClient.INSTANCE.fontHelper.size20;
        int settingY = y + h / 2 - (s.height / 2);
        if (s instanceof Text) {
            s.render(x, settingY, mouseX, mouseY);
        } else {
            int nameX = x + 5;
            font.drawString(s.name, nameX, y + 10, VeyzenClient.text.getRGB());
            int descX = nameX + font.getStringWidth(s.name) + 5;
            font1.drawString(s.desc, descX, y + h - font1.getFontHeight() - 3, VeyzenClient.fg.getRGB());
            int settingX = x + w - (s.width + 5);
            if (s instanceof Slider) {
                settingY = y + h / 2;
            }
            s.render(settingX, settingY, mouseX, mouseY);
        }
    }

    @Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		for (int i = 0; i < renderSettings.size(); i++) {
			Setting s = renderSettings.get(i);
			s.mouseClick(mouseX, mouseY, mouseButton);
		}
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
		for (int i = 0; i < renderSettings.size(); i++) {
			Setting s = renderSettings.get(i);
			s.mouseReleased(mouseX, mouseY, state);
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		for (int i = 0; i < renderSettings.size(); i++) {
			Setting s = renderSettings.get(i);
			s.keyTyped(typedChar, keyCode);
		}
	}

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if(button instanceof SettingCategoryButton){
            selectedCategory = button.displayString;
            updateModSettings();
        }
    }
}
