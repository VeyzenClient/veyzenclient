package com.github.veyzenclient.veyzenclient.config.settings.impl;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.config.settings.Setting;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class TextField extends Setting {

    public enum TextFilter {
        ALL, NUMERIC, LETTERS
    }

    public String text;
    public TextFilter filter;
    public int maxLength;

    private boolean focused = false;
    private int cursorPosition;
    private long lastBlink = 0;
    private boolean blink = false;
    private int repeatKey = -1;
    private long repeatStart = 0;
    private static final long INITIAL_REPEAT_DELAY = 400;
    private static final long REPEAT_INTERVAL = 50;



    public TextField(String id, String name, String desc, String parent,TextFilter filter, String text, int maxLength) {
        super(id, name, desc, 150, 25,parent);
        this.text = text;
        this.filter = filter;
        this.maxLength = maxLength;
        this.cursorPosition = text.length();
    }

    @Override
    public void render(int x, int y, int mouseX, int mouseY) {
        this.x = x;
        this.y = y;

        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

        Helper2D.drawRectangle(x, y, width, height, VeyzenClient.bg.getRGB());

        // Border
        Helper2D.drawRectangle(x - 1, y - 1, width + 2, 1, VeyzenClient.fg.getRGB());
        Helper2D.drawRectangle(x - 1, y + height, width + 2, 1, VeyzenClient.fg.getRGB());
        Helper2D.drawRectangle(x - 1, y, 1, height, VeyzenClient.fg.getRGB());
        Helper2D.drawRectangle(x + width, y, 1, height, VeyzenClient.fg.getRGB());

        // Scroll logic
        int maxTextWidth = width - 8;
        int scrollOffset = 0;
        while (Minecraft.getMinecraft().fontRendererObj.getStringWidth(text.substring(scrollOffset, cursorPosition)) > maxTextWidth && scrollOffset < cursorPosition) {
            scrollOffset++;
        }

        String beforeCursor = text.substring(scrollOffset, cursorPosition);
        String afterCursor = cursorPosition < text.length() ? text.substring(cursorPosition) : "";

        int textX = x + 4;
        int textY = y + (height - fr.FONT_HEIGHT) / 2;

        fr.drawString(beforeCursor, textX, textY, VeyzenClient.fg.getRGB());
        int cursorX = textX + fr.getStringWidth(beforeCursor);

        if (focused) {
            if (System.currentTimeMillis() - lastBlink > 500) {
                blink = !blink;
                lastBlink = System.currentTimeMillis();
            }
            if (blink) {
                Helper2D.drawRectangle(cursorX, textY, 1, fr.FONT_HEIGHT, VeyzenClient.fg.getRGB());
            }
        }

        fr.drawString(afterCursor, cursorX + 1, textY, VeyzenClient.fg.getRGB());
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

    @Override
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
                if (text.length() < maxLength && passesFilter(c)) {
                    text = text.substring(0, cursorPosition) + c + text.substring(cursorPosition);
                    cursorPosition++;
                }
            }
        }
    }

    private boolean passesFilter(char c) {
        switch (filter) {
            case NUMERIC: return Character.isDigit(c);
            case LETTERS: return Character.isLetter(c);
            case ALL:
            default: return true;
        }
    }

    @Override
    public void mouseClick(int mouseX, int mouseY, int button) {
        boolean wasFocused = focused;
        focused = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
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