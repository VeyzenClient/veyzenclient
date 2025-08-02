package com.github.veyzenclient.veyzenclient.gui;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.font.GlyphPageFontRenderer;
import com.github.veyzenclient.veyzenclient.gui.buttons.IconButton;
import com.github.veyzenclient.veyzenclient.gui.buttons.TextButton;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.ArrayList;

public class TitleScreen extends Panorama {

    private final ArrayList<TextButton> textButtons = new ArrayList<>();
    private final ArrayList<IconButton> iconButtons = new ArrayList<>();

    public TitleScreen() {
        textButtons.add(new TextButton("Singleplayer", width / 2 - 75, height / 2));
        textButtons.add(new TextButton("Multiplayer", width / 2 - 75, height / 2 + 25));
        textButtons.add(new TextButton("Settings", width / 2 - 75, height / 2 + 50));
        iconButtons.add(new IconButton("cross", width - 25, 5));
    }

    /**
     * Renders button text and logos on the screen
     *
     * @param mouseX       The current X position of the mouse
     * @param mouseY       The current Y position of the mouse
     * @param partialTicks The partial ticks used for rendering
     */

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        int y = 0;
        for (TextButton textButton : textButtons) {
            textButton.renderButton(width / 2 - 75, height / 2 + y * 25, mouseX, mouseY);
            y++;
        }

        for (IconButton iconButton : iconButtons) {
            if (iconButton.getIcon().equals("cross")) {
                iconButton.renderButton(width - 25, 5, mouseX, mouseY);
            }
        }

        drawLogo();
        drawCopyright();
    }

    /**
     * Is called when any mouse button is pressed. Adds functionality to every button on screen
     *
     * @param mouseX      The current X position of the mouse
     * @param mouseY      The current Y position of the mouse
     * @param mouseButton The current mouse button which is pressed
     */

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
                    case "Settings":
                        mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                        break;
                }
            }
        }

        for (IconButton iconButton : iconButtons) {
            if (iconButton.isHovered(mouseX, mouseY)) {
                if (iconButton.getIcon().equals("cross")) {
                    mc.shutdown();
                }
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Draws the main "Veyzen" Text and the Logo in the middle
     */

    private void drawLogo() {
        GlyphPageFontRenderer fontRenderer = VeyzenClient.INSTANCE.fontHelper.size40;
        fontRenderer.drawString("Veyzen", width / 2f - fontRenderer.getStringWidth(VeyzenClient.modName) / 2f, height / 2f - 27.5f, 0xffffffff);
        Helper2D.drawPicture(width / 2 - 30, height / 2 - 80, 60, 60, 0xffffffff, new ResourceLocation("veyzen","logo.png"));
    }

    /**
     * Draws the "Cloud Client" Text and Mojang Copyright Notice on the bottom
     */

    private void drawCopyright() {
        GlyphPageFontRenderer fontRenderer = VeyzenClient.INSTANCE.fontHelper.size20;
        String copyright = "Copyright Mojang Studios. Do not distribute!";
        String text = "Veyzen" + " Client " + VeyzenClient.modVersion;
        fontRenderer.drawString(copyright, width - fontRenderer.getStringWidth(copyright) - 2, height - fontRenderer.getFontHeight(), 0xffffffff);
        fontRenderer.drawString(text, 4, height - fontRenderer.getFontHeight(), 0xffffffff);
    }
}
