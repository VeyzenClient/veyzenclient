package com.github.veyzenclient.veyzenclient.config.pages;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.config.buttons.*;
import com.github.veyzenclient.veyzenclient.config.buttons.TextField;
import com.github.veyzenclient.veyzenclient.features.Mod;
import com.github.veyzenclient.veyzenclient.features.ModCategory;
import com.github.veyzenclient.veyzenclient.features.ModManager;
import com.github.veyzenclient.veyzenclient.font.GlyphPageFontRenderer;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static com.github.veyzenclient.veyzenclient.utils.ResolutionHelper.getXStatic;
import static com.github.veyzenclient.veyzenclient.utils.ResolutionHelper.getYStatic;

public class ConfigScreen extends GuiScreen {

    public ScaledResolution sr;
    public GlyphPageFontRenderer font;
    public TextField search;
    public float wWindow, hWindow,xWindow,yWindow;
    public float xSidebar,ySidebar,wSidebar,hSidebar;
    public float xCat,yCat,wCat,hCat;
    public float xSet,ySet,wSet,hSet;
    public float xHud,yHud,wHud,hHud;
    public float xMc,yMc,wMc,hMc;
    public float xLogo,yLogo,wLogo,hLogo;
    public float xSearch,ySearch,wSearch,hSearch;
    private float scrollOffset = 0;
    private float targetScrollOffset = 0;
    private float maxScrollOffset = 0;
    public static boolean favOnly = false;
    public CategoryButton selected;
    @Override
    public void initGui() {
        super.initGui();
        mc = Minecraft.getMinecraft();
        sr = new ScaledResolution(mc);
        font = VeyzenClient.INSTANCE.fontHelper.size40;
        loadConstants();
        search = new TextField((int) xSearch, (int) ySearch, (int) wSearch, (int) hSearch,(wSearch/500f),"",1000);
        loadCategories();
        loadMods();
        this.buttonList.add(new FavButton(1,(int)(xWindow + getXStatic(1090)),(int)(yWindow + getYStatic(18))));
        this.buttonList.add(new CloseButton(0,(int)(xWindow + getXStatic(1143)),(int)(yWindow + getYStatic(18))));
    }

    private void loadMods() {
        this.buttonList.removeIf(b -> b instanceof ModButton);
        float xStart = xSidebar + wSidebar + getXStatic(13);
        float yStart = yWindow + getYStatic(70) + scrollOffset;
        int rows = (int)Math.ceil(getMods().size() / 3.0);
        maxScrollOffset = Math.max(0, rows * getYStatic(207) - (hWindow - getYStatic(100)));
        float xOffset = getXStatic(274);
        float yOffset = getYStatic(207);
        int xi = 0,yi = 0,i = 0;
        for(Mod m : getMods()){
            int x = (int)(xStart + (xi * xOffset));
            int y = (int)(yStart + (yi * xOffset));
            ModButton b = new ModButton(i + 100,x,y,m);
            if (y + b.height < yWindow + getYStatic(70) || y > yWindow + hWindow - getYStatic(30)) {
                b.enabled = false;
                b.visible = false;
            } else {
                b.enabled = true;
                b.visible = true;
            }
            this.buttonList.add(b);
            i++;
            xi++;
            if(xi % 3 == 0){
                xi = 0;
                yi++;
            }
        }
    }


    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        search.keyTyped(typedChar,keyCode);
        loadMods();
    }

    private void loadCategories() {
        this.buttonList.removeIf(b -> b instanceof CategoryButton);
        float xStart = xCat + getXStatic(5);
        float yStart = yCat + getXStatic(5);
        float yOffset = getXStatic(font.getFontHeight()) + getXStatic(3);
        float height = getYStatic(font.getFontHeight());
        int i = 0;
        for(ModCategory category : ModCategory.values()){
            CategoryButton b =
                    new CategoryButton(
                            i + 1000,
                            (int) xStart,
                            (int) (yStart + (i * yOffset)),
                            (int) getXStatic(80),
                            (int) height,
                            category
                    );
            if(category == ModCategory.ALL){
                selected = b;
                selected.selected = true;
            }
            this.buttonList.add(b);
            i++;
        }
    }

    private void loadConstants() {
        wWindow = getXStatic(1200);
        hWindow = getYStatic(700);
        xWindow = (float) (sr.getScaledWidth_double() / 2 - wWindow / 2);
        yWindow = (float) (sr.getScaledHeight_double() / 2 - hWindow / 2);

        xSidebar = xWindow + getXStatic(16);
        ySidebar = yWindow + getYStatic(18);
        wSidebar = getXStatic(266);
        hSidebar = getYStatic(666);

        xSearch = xSidebar + wSidebar + getXStatic(13);
        ySearch = yWindow + getYStatic(18);
        wSearch = getXStatic(500);
        hSearch = getYStatic(40);

        xCat = xSidebar + getXStatic(12);
        yCat = ySidebar + getYStatic(120);
        wCat = getXStatic(243);
        hCat = getYStatic(426);

        xSet = xSidebar + getXStatic(12);
        ySet = yCat + hCat + getYStatic(21);
        wSet = getXStatic(75);
        hSet = getYStatic(75);

        xHud = xSet + wSet + getXStatic(10);
        yHud = yCat + hCat + getYStatic(21);
        wHud = getXStatic(75);
        hHud = getYStatic(75);

        xMc = xHud + wHud + getXStatic(10);
        yMc = yCat + hCat + getYStatic(21);
        wMc = getXStatic(75);
        hMc = getYStatic(75);

        yLogo = ySidebar;
        wLogo = getXStatic(230);
        hLogo = getYStatic(130);
        xLogo = xSidebar + wSidebar / 2 - wLogo/2;

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
            boolean matchesCategory = selected.cat == ModCategory.ALL || m.categories.contains(selected.cat);

            if (matchesSearch && matchesCategory) {
                mods.add(m);
            }
        }
        return mods;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        scrollOffset += (targetScrollOffset - scrollOffset) * 0.15f;
        scrollOffset = Math.max(-maxScrollOffset, Math.min(0, scrollOffset));
        Helper2D.drawRoundedRectangle((int) xWindow, (int) yWindow, (int) wWindow, (int) hWindow,4, VeyzenClient.bg.getRGB(),0);
        Helper2D.drawRoundedRectangle((int)xSidebar,(int)ySidebar,(int)wSidebar,(int)hSidebar,4,VeyzenClient.bgcomp.getRGB(),0);
        Helper2D.drawRoundedRectangle((int)xCat,(int)yCat,(int)wCat,(int)hCat,4,VeyzenClient.bg.getRGB(),0);
        Helper2D.drawRoundedRectangle((int)xSet,(int)ySet,(int)wSet,(int)hSet,4,VeyzenClient.bg.getRGB(),0);
        Helper2D.drawRoundedRectangle((int)xHud,(int)yHud,(int)wHud,(int)hHud,4,VeyzenClient.bg.getRGB(),0);
        Helper2D.drawRoundedRectangle((int)xMc,(int)yMc,(int)wMc,(int)hMc,4,VeyzenClient.bg.getRGB(),0);
        Helper2D.drawPicture((int)xLogo,(int)yLogo,(int)wLogo,(int)hLogo, VeyzenClient.fg.getRGB(),new ResourceLocation("veyzen","veyzen_nobg.png"));
        Helper2D.drawPicture((int)xSet,(int)ySet,(int)wSet,(int)hSet,VeyzenClient.text.getRGB(),new ResourceLocation("veyzen","icons/settings.png"));
        Helper2D.drawPicture((int)xHud,(int)yHud,(int)wHud,(int)hHud,VeyzenClient.text.getRGB(),new ResourceLocation("veyzen","icons/hud.png"));
        Helper2D.drawPicture((int)xMc,(int)yMc,(int)wMc,(int)hMc,VeyzenClient.text.getRGB(),new ResourceLocation("veyzen","icons/mcsetting.png"));
        search.render(mouseX,mouseY,mc);
        int scissorX = (int) (xSidebar + wSidebar + getXStatic(13));
        int scissorY = (int) (sr.getScaledHeight_double() - (yWindow + hWindow - getYStatic(60))); // OpenGL bottom-left origin
        int scissorW = (int) getXStatic(850); // Width of mod area
        int scissorH = (int) getYStatic(590); // Height of mod area

        for (GuiButton guiButton : this.buttonList) {
            if (!(guiButton instanceof ModButton)) {
                guiButton.drawButton(this.mc, mouseX, mouseY);
            }
        }
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(scissorX * sr.getScaleFactor(), scissorY * sr.getScaleFactor(), scissorW * sr.getScaleFactor(), scissorH * sr.getScaleFactor());
        for (GuiButton guiButton : this.buttonList) {
            if (guiButton instanceof ModButton) {
                guiButton.drawButton(this.mc, mouseX, mouseY);
            }
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);

    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int dWheel = org.lwjgl.input.Mouse.getDWheel();
        if (dWheel != 0) {
            float scrollSpeed = 25f;
            targetScrollOffset += (dWheel > 0 ? scrollSpeed : -scrollSpeed);
            targetScrollOffset = Math.max(-maxScrollOffset, Math.min(0, targetScrollOffset));
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if(mouseX > xSet && mouseX < xSet + wSet){
            if(mouseY > ySet && mouseY < ySet + hSet){
                Minecraft.getMinecraft().displayGuiScreen(new GlobalSettings());
                mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            }
        }
        if(mouseX > xHud && mouseX < xHud + wHud){
            if(mouseY > yHud && mouseY < yHud + hHud){
                System.out.println("HUD");
                mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            }
        }
        if(mouseX > xMc && mouseX < xMc + wMc){
            if(mouseY > yMc && mouseY < yMc + hMc){
                mc.displayGuiScreen(new GuiOptions(this,mc.gameSettings));
                mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            }
        }
        search.mouseClick(mouseX,mouseY,mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if(button instanceof CategoryButton){
            selected.selected = false;
            selected = (CategoryButton) button;
            selected.selected = true;
            loadMods();
        }
        if(button instanceof ModButton){
            ((ModButton)button).process();
        }
        if(button instanceof FavButton){
            favOnly = !favOnly;
            loadMods();
        }
        if(button instanceof CloseButton){
            ((CloseButton)button).process();
        }
    }
}
