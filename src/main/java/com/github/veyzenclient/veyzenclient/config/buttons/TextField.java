package com.github.veyzenclient.veyzenclient.config.buttons;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.font.GlyphPageFontRenderer;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class TextField {

    public String text;
    public int maxLength;
    private boolean focused = false;
    private int cursorPosition;
    private long lastBlink = 0;
    private boolean blink = false;
    private int repeatKey = -1;
    private long repeatStart = 0;
    private static final long INITIAL_REPEAT_DELAY = 400;
    private static final long REPEAT_INTERVAL = 50;
    public int xPos,yPos,width,height;
    public float scale;
    public TextField(int xPosition,int yPosition,int width,int height, float scale,String text, int maxLength) {
        xPos = xPosition;
        yPos = yPosition;
        this.scale = scale;
        this.width = width;
        this.height = height;
        this.text = text;
        this.maxLength = maxLength;
        this.cursorPosition = text.length();
    }

    public void render(int mouseX, int mouseY, Minecraft mc){
        GlyphPageFontRenderer font = VeyzenClient.INSTANCE.fontHelper.size40;

        Helper2D.drawRoundedRectangle(xPos,yPos,width,height,4,VeyzenClient.bgcomp.getRGB(),0);
        int maxTextWidth = width - 8;
        int scrollOffset = 0;
        while ((font.getStringWidth(text.substring(scrollOffset, cursorPosition)) * scale) > maxTextWidth && scrollOffset < cursorPosition) {
            scrollOffset++;
        }

        String beforeCursor = text.substring(scrollOffset, cursorPosition);
        String afterCursor = cursorPosition < text.length() ? text.substring(cursorPosition) : "";

        int textX = xPos + 4;
        int textY = (int) (yPos + (height - (font.getFontHeight() * scale)) / 2) + 1;

        int cursorX = textX + (int)(font.getStringWidth(beforeCursor) * scale);

        font.drawScaledString(beforeCursor,textX,textY,scale,VeyzenClient.text.getRGB());
        if (focused) {
            if (System.currentTimeMillis() - lastBlink > 500) {
                blink = !blink;
                lastBlink = System.currentTimeMillis();
            }
            if (blink) {
                Helper2D.drawRectangle(cursorX, textY, 1, (int)(font.getFontHeight() * scale), VeyzenClient.fg.getRGB());
            }
        }
        font.drawScaledString(afterCursor,cursorX + 1, textY,scale,VeyzenClient.text.getRGB());
        update();
    }

    public void update() {
        if (focused && Keyboard.isCreated()) {
            if (repeatKey != -1 && Keyboard.isKeyDown(repeatKey)) {
                long now = System.currentTimeMillis();
                if (now - repeatStart > INITIAL_REPEAT_DELAY) {
                    handleKeyInput(repeatKey);
                    repeatStart = now - (INITIAL_REPEAT_DELAY - REPEAT_INTERVAL);
                }
            }
        }
    }

    public void keyTyped(char key, int keycode) {
        if (!focused) return;

        if (Keyboard.getEventKeyState()) {
            repeatKey = keycode;
            repeatStart = System.currentTimeMillis();
            handleKeyInput(keycode);
        } else if (keycode == repeatKey) {
            repeatKey = -1;
        }
    }

    private void handleKeyInput(int key) {
        if (key == Keyboard.KEY_BACK) {
            if (cursorPosition > 0 && !text.isEmpty()) {
                text = text.substring(0, cursorPosition - 1) + text.substring(cursorPosition);
                cursorPosition--;
            }
        } else if (key == Keyboard.KEY_LEFT) {
            cursorPosition = Math.max(0, cursorPosition - 1);
        } else if (key == Keyboard.KEY_RIGHT) {
            cursorPosition = Math.min(text.length(), cursorPosition + 1);
        } else if (key == Keyboard.KEY_RETURN || key == Keyboard.KEY_ESCAPE) {
            focused = false;
        } else {
            char c = Keyboard.getEventCharacter();
            if (Character.isDefined(c) && !Character.isISOControl(c)) {
                if (text.length() < maxLength) {
                    text = text.substring(0, cursorPosition) + c + text.substring(cursorPosition);
                    cursorPosition++;
                }
            }
        }
    }

    public void mouseClick(int mouseX, int mouseY, int button) {
        boolean wasFocused = focused;
        focused = mouseX >= xPos && mouseX <= xPos + width && mouseY >= yPos && mouseY <= yPos + height;
        if (!wasFocused && focused) {
            cursorPosition = text.length();
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String newText) {
        this.text = newText;
        this.cursorPosition = Math.min(newText.length(), cursorPosition);
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }


}
