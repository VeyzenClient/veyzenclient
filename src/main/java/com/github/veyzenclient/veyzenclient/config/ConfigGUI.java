package com.github.veyzenclient.veyzenclient.config;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.config.buttons.CategoryButton;
import com.github.veyzenclient.veyzenclient.config.buttons.FavButton;
import com.github.veyzenclient.veyzenclient.features.Mod;
import com.github.veyzenclient.veyzenclient.features.ModCategory;
import com.github.veyzenclient.veyzenclient.features.ModManager;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;

public class ConfigGUI extends GuiScreen {

    public int menuWidth = 840;
    public int menuHeight = 480;
    public int scrollOffset = 0;
    public CategoryButton select;
    public int scale;
    public GuiTextField search;
    public static boolean favOnly;

    public static ConfigGUI getInstance(){
        return new ConfigGUI();
    }

    public ConfigGUI(){
		this.scale = Minecraft.getMinecraft().gameSettings.guiScale;
        Minecraft.getMinecraft().gameSettings.guiScale = 1;
        this.search = new GuiTextField(1001, Minecraft.getMinecraft().fontRendererObj, 0, 0, 100, 20);
    }


    @Override
    public void onGuiClosed() {
        Minecraft.getMinecraft().gameSettings.guiScale = this.scale;
    }

    public void openModConfig(Mod m) {
        Minecraft.getMinecraft().gameSettings.guiScale = this.scale;
        Minecraft.getMinecraft().displayGuiScreen(new ModConfigGUI(m));
    }
    @Override
    public void initGui() {
        int xi = 0;
        mc = Minecraft.getMinecraft();
        ScaledResolution sr = new ScaledResolution(mc);
        int xStart = sr.getScaledWidth() / 2 - menuWidth / 2 + 10;
        int yStart = sr.getScaledHeight() / 2 - menuHeight / 2 + 10;
        int xOffset = 80;
        for(ModCategory category : ModCategory.values()){
            CategoryButton button = new CategoryButton(xi,xStart + (xOffset * xi),yStart,category);
            if(category == ModCategory.ALL){
                button.selected = true;
                this.select = button;
            }
            this.buttonList.add(button);
            xi++;
        }

        updateModButtons();
        search.xPosition = sr.getScaledWidth() / 2 + menuWidth / 2 - 297;
        search.yPosition = yStart + 10;
        search.setEnableBackgroundDrawing(false);
        search.setTextColor(VeyzenClient.text.getRGB());
        this.buttonList.add(new FavButton(599,sr.getScaledWidth() / 2 + menuWidth / 2 - 90,yStart - 2,32,32,new ResourceLocation("veyzen","icons/fav.png")));
    }

    private void updateModButtons() {
		for (int i = 0; i < buttonList.size(); i++) {
			GuiButton b = buttonList.get(i);
			if (b instanceof ConfigMod) {
				buttonList.remove(i);
				i--;
			}
		}

		ArrayList<Mod> mods = getMods();
		int totalMods = mods.size();
		int rows = (int) Math.ceil(totalMods / 4.0);
		int maxScroll = Math.max(0, rows - 3);

		scrollOffset = Math.max(0, Math.min(scrollOffset, maxScroll));
		ScaledResolution sr = new ScaledResolution(mc);
		int xStart = sr.getScaledWidth() / 2 - menuWidth / 2 + 10;
		int yStart = sr.getScaledHeight() / 2 - menuHeight / 2 + 45;
		int xOffset = 200;
		int yOffset = 130;
		int i = 0, xI = 0, yI = 0;

		for (Mod m : mods) {
			if (i % 4 == 0 && i != 0) {
				xI = 0;
				yI++;
			}

			if (yI < scrollOffset) {
				i++;
				xI++;
				continue;
			}

			int x = xStart + (xOffset * xI);
			int y = yStart + (yOffset * (yI - scrollOffset));
			if (x < sr.getScaledWidth() / 2 + menuWidth / 2 && y < sr.getScaledHeight() / 2 + menuHeight / 2) {
				this.buttonList.add(new ConfigMod(i, x, y, m));
			}

			i++;
			xI++;
		}
	}

    private ArrayList<Mod> getMods() {
            ArrayList<Mod> mods = new ArrayList<>();
            ArrayList<Mod> modsToCheck = new ArrayList<>();
            if(favOnly){
                modsToCheck.addAll(ModManager.getFavorites());
            }else {
                modsToCheck.addAll(ModManager.mods.values());
            }
            for (Mod m : modsToCheck) {
                boolean matchesSearch = search.getText().isEmpty() || m.name.toLowerCase().startsWith(search.getText().toLowerCase());
                boolean matchesCategory = select.category == ModCategory.ALL || m.categories.contains(select.category);

                if (matchesSearch && matchesCategory) {
                    mods.add(m);
                }
            }
            return mods;
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int wheel = Mouse.getDWheel();
        if(wheel > 0){
            scrollOffset--;
        }else {
            scrollOffset++;
        }
        updateModButtons();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int xCenter = sr.getScaledWidth() / 2;
        int yCenter = sr.getScaledHeight() / 2;
        Helper2D.drawRoundedRectangle(xCenter - menuWidth / 2,yCenter- menuHeight / 2,menuWidth,menuHeight,4,VeyzenClient.bg.getRGB(),0);
        int xPos = sr.getScaledWidth() / 2 + menuWidth / 2 - 300;
        int yStart = sr.getScaledHeight() / 2 - menuHeight / 2 + 10;
        Helper2D.drawRoundedRectangle(xPos, yStart, 200,25,4,VeyzenClient.bgcomp.getRGB(),0);
        if (search != null) {
            GlStateManager.pushMatrix();
            int xTemp = search.xPosition;
            int yTemp = search.yPosition;
            search.xPosition = 0;
            search.yPosition = 0;
            GlStateManager.translate(xTemp, yTemp, 0);
            GlStateManager.scale(2f, 2f, 2f);
            search.drawTextBox();
            search.xPosition = xTemp;
            search.yPosition = yTemp;
            GlStateManager.popMatrix();
        }
        for (GuiButton guiButton : this.buttonList) {
            if (guiButton instanceof ConfigMod) continue;
            guiButton.drawButton(this.mc, mouseX, mouseY);
        }
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        int xCStart = sr.getScaledWidth() / 2 - menuWidth / 2;
        int yCStart = sr.getScaledHeight() / 2 - menuHeight / 2;
        GL11.glScissor(xCStart,yCStart,menuWidth,menuHeight);
        for(GuiButton b : buttonList){
            if(b instanceof ConfigMod){
                b.drawButton(mc,mouseX,mouseY);
            }
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);


        for (GuiLabel guiLabel : this.labelList) {
            guiLabel.drawLabel(this.mc, mouseX, mouseY);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        search.mouseClicked(mouseX,mouseY,mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if(search.isFocused()) {
            if(keyCode == Keyboard.KEY_ESCAPE) {
                search.setFocused(false);
            }
        }
        search.textboxKeyTyped(typedChar, keyCode);
        updateModButtons();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button instanceof CategoryButton){
            ((CategoryButton)button).selected = true;
            select.selected = false;
            select = (CategoryButton) button;
        }else if(button instanceof ConfigMod){
            ((ConfigMod)button).process();
        }else if(button.id == 599){
            favOnly = !favOnly;
        }
        updateModButtons();
    }
}
