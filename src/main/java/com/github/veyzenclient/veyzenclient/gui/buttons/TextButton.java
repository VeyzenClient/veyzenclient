package com.github.veyzenclient.veyzenclient.gui.buttons;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;
import com.github.veyzenclient.veyzenclient.utils.MathHelper;
import com.github.veyzenclient.veyzenclient.utils.animation.Animate;
import com.github.veyzenclient.veyzenclient.utils.animation.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import org.lwjgl.input.*;
import net.minecraft.client.audio.*;

public class TextButton {

    private final Animate animate = new Animate();
    private final String text;
    private int x, y;
    private final int w, h;
    private boolean isHoveredLast = false;

    public TextButton(String text, int x, int y) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.w = 150;
        this.h = 20;
        animate.setEase(Easing.LINEAR).setMin(0).setMax(25).setSpeed(200);
    }

    public void renderButton(int x, int y, int mouseX, int mouseY) {
        this.x = x;
        this.y = y;

        boolean isHovered = isHovered(mouseX, mouseY);
        animate.update().setReversed(!isHovered);

        if (isHovered && Mouse.isButtonDown(0)) {
            playSound(Minecraft.getMinecraft().getSoundHandler());
        }

        isHoveredLast = isHovered;

        Helper2D.drawRoundedRectangle(x, y, w, h, 8,
									  isHovered ? new Color(70, 70, 70, 180).getRGB() : new Color(50, 50, 50, 120).getRGB(),
									  isHovered ? new Color(255, 255, 255, 40).getRGB() : 0
									  );

        VeyzenClient.INSTANCE.fontHelper.size20.drawString(
			text,
			x + w / 2f - VeyzenClient.INSTANCE.fontHelper.size20.getStringWidth(text) / 2f,
			y + h / 2f - 4,
			0xffffffff
        );
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return MathHelper.withinBox(x, y, w, h, mouseX, mouseY);
    }

    public void playSound(SoundHandler soundHandler) {
        soundHandler.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getText() {
        return text;
    }
}
