package com.github.veyzenclient.veyzenclient.gui;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.font.GlyphPageFontRenderer;
import com.github.veyzenclient.veyzenclient.gui.buttons.IconButton;
import com.github.veyzenclient.veyzenclient.gui.buttons.TextButton;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;

public class TitleScreen extends Panorama {

    private final ArrayList<TextButton> textButtons = new ArrayList<>();
    private final ArrayList<IconButton> iconButtons = new ArrayList<>();

    private float logoScale = 1.0f;
    private long initTime;
    private float uiScale = 1.0f;

    public TitleScreen() {
        initTime = System.currentTimeMillis();

        ScaledResolution sr = new ScaledResolution(mc);
        uiScale = Math.max(0.8f, Math.min(2.0f, sr.getScaleFactor() / 2.0f));

        textButtons.add(new TextButton("Singleplayer", 0, 0));
        textButtons.add(new TextButton("Multiplayer", 0, 0));
        textButtons.add(new TextButton("Options", 0, 0));

        iconButtons.add(new IconButton("cross", width - 25, 5));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        updateAnimations();
        drawLogo();
        drawTextButtons(mouseX, mouseY);
        drawIconButtons(mouseX, mouseY);
        drawCopyright();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (TextButton textButton : textButtons) {
            if (textButton.isHovered(mouseX, mouseY)) {
                switch (textButton.getText()) {
                    case "Singleplayer":
                        mc.displayGuiScreen(new GuiSelectWorld(this));
                        break;
                    case "Multiplayer":
                        mc.displayGuiScreen(new GuiMultiplayer(this));
                        break;
                    case "Options":
                        mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                        break;
                }
                return;
            }
        }

        for (IconButton iconButton : iconButtons) {
            if (iconButton.isHovered(mouseX, mouseY)) {
                if (iconButton.getIcon().equals("cross")) {
                    mc.shutdown();
                }
                return;
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private void updateAnimations() {
        long currentTime = System.currentTimeMillis();
        float timeElapsed = (currentTime - initTime) / 1000.0f;
        logoScale = 1.0f + 0.05f * (float)Math.sin(timeElapsed * 0.8f);
    }

    private void drawLogo() {
        float logoX = width / 2f;
        float logoY = height * 0.3f;

        GL11.glPushMatrix();
        GL11.glTranslatef(logoX, logoY, 0);
        GL11.glScalef(logoScale * uiScale, logoScale * uiScale, 1.0f);

        int vLogoSize = (int)(80 * uiScale);
        Helper2D.drawPicture(-vLogoSize/2, -vLogoSize/2, vLogoSize, vLogoSize, 0xffffffff, 
							 new ResourceLocation("veyzen", "logo.png"));

        GL11.glPopMatrix();

        GlyphPageFontRenderer subtitleFont = VeyzenClient.INSTANCE.fontHelper.size20;
        String clientText = "Veyzen Client";
        float clientWidth = subtitleFont.getStringWidth(clientText);
        subtitleFont.drawString(clientText, logoX - clientWidth/2f, logoY + 60, 0xffffffff);
    }

    private void drawTextButtons(int mouseX, int mouseY) {
        int startY = (int)(height * 0.55f);
        int buttonSpacing = 35;
        int buttonX = width / 2 - 75;

        for (int i = 0; i < textButtons.size(); i++) {
            TextButton button = textButtons.get(i);
            int buttonY = startY + i * buttonSpacing;
            button.renderButton(buttonX, buttonY, mouseX, mouseY);
        }
    }

    private void drawIconButtons(int mouseX, int mouseY) {
        for (IconButton iconButton : iconButtons) {
            if (iconButton.getIcon().equals("cross")) {
                int buttonX = width - iconButton.getW() - 10;
                int buttonY = 10;
                iconButton.renderButton(buttonX, buttonY, mouseX, mouseY);
            }
        }
    }

    private void drawCopyright() {
        GlyphPageFontRenderer fontRenderer = VeyzenClient.INSTANCE.fontHelper.size20;

        String copyright = "Copyright Mojang. Do not distribute!";
        float rightMargin = 10 * uiScale;
        float bottomMargin = 10 * uiScale;
        fontRenderer.drawString(copyright, 
								width - fontRenderer.getStringWidth(copyright) - rightMargin, 
								height - fontRenderer.getFontHeight() - bottomMargin, 
								0xffffffff);

        String version = "VeyzenClient v" + VeyzenClient.modVersion + ", by Arctyll.";
        fontRenderer.drawString(version, rightMargin, 
								height - fontRenderer.getFontHeight() - bottomMargin, 
								0xffffffff);
    }


}
