package com.github.veyzenclient.veyzenclient.config.pages;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.config.buttons.CloseButton;
import com.github.veyzenclient.veyzenclient.config.buttons.TextField;
import com.github.veyzenclient.veyzenclient.config.settings.Setting;
import com.github.veyzenclient.veyzenclient.config.settings.impl.ColorPicker;
import com.github.veyzenclient.veyzenclient.config.settings.impl.Keybind;
import com.github.veyzenclient.veyzenclient.config.settings.impl.Slider;
import com.github.veyzenclient.veyzenclient.config.settings.impl.Text;
import com.github.veyzenclient.veyzenclient.features.ModManager;
import com.github.veyzenclient.veyzenclient.font.GlyphPageFontRenderer;
import com.github.veyzenclient.veyzenclient.utils.Helper2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.github.veyzenclient.veyzenclient.utils.ResolutionHelper.getXStatic;
import static com.github.veyzenclient.veyzenclient.utils.ResolutionHelper.getYStatic;

public class GlobalSettings extends GuiScreen {

    public ScaledResolution sr;
    public TextField search;
    private boolean click;
    public float wWindow, hWindow,xWindow,yWindow;
    public float wLogo, hLogo,xLogo,yLogo;
    public float xSearch,ySearch,wSearch,hSearch;
    public float xSetting,wSetting,hSetting;
    public Setting focused;

    public void drawSetting(Setting s, int y, int mouseX, int mouseY) {
        Helper2D.drawRoundedRectangle((int)xSetting,y,(int)wSetting ,(int)hSetting ,4,VeyzenClient.bgcomp.getRGB(),0);
        GlyphPageFontRenderer font = VeyzenClient.INSTANCE.fontHelper.size40;
        float scale = getXStatic(1);
        float xPos = xSetting + getXStatic(6);
        if(!(s instanceof Text)) {
            font.drawScaledString(s.name, xPos, y + hSetting / 8, scale, VeyzenClient.text.getRGB());
            GlyphPageFontRenderer font1 = VeyzenClient.INSTANCE.fontHelper.size35;
            font1.drawScaledString(s.desc, xPos, y + hSetting / 2, scale, VeyzenClient.fg.getRGB());
        }
        if(s instanceof ColorPicker){
            float xPosition = xSetting + wSetting - getXStatic(163);
            float yPosition = y + getYStatic(10);
            float w = getXStatic(150);
            float h = getYStatic(50);
            Helper2D.drawRoundedRectangle((int)(xPosition),(int)(yPosition),(int)w,(int)h,4,VeyzenClient.bg.getRGB(),0);
            if(mouseX > xPosition && mouseX < xPosition + w){
                if(mouseY > yPosition && mouseY < yPosition + h){
                    boolean press = Mouse.isButtonDown(0);
                    if(!click && press){
                        focused = s;
                    }
                    click = press;
                }
            }
        }else if(s instanceof Text) {
            s.render((int) (xSetting + (int)getXStatic(5)), (int) (y + hSetting / 4),mouseX,mouseY);
        }else{
                float xPosition = xSetting + wSetting - getXStatic(10 + s.width);
                float settingY = y + hSetting / 2 - getYStatic(s.height)/2;
                if(s instanceof Slider){
                    settingY = y + hSetting/2;
                }
                s.render((int) xPosition, (int) settingY,mouseX,mouseY);
            }
        }

    @Override
    public void initGui() {
        super.initGui();
        mc = Minecraft.getMinecraft();
        sr = new ScaledResolution(mc);
        loadConstants();
        search = new TextField((int) xSearch, (int) ySearch, (int) wSearch, (int) hSearch,(hSearch/40f),"",1000);
        this.buttonList.add(new CloseButton(1,(int)(xWindow + getXStatic(1139)),(int)(yWindow + getYStatic(18))));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if(button instanceof CloseButton){
            Minecraft.getMinecraft().displayGuiScreen(new ConfigScreen());
        }
    }

    private void loadConstants(){
        wWindow = getXStatic(1200);
        hWindow = getYStatic(700);
        xWindow = (float) (sr.getScaledWidth_double() / 2 - wWindow / 2);
        yWindow = (float) (sr.getScaledHeight_double() / 2 - hWindow / 2);

        yLogo = yWindow + getYStatic(16);
        xLogo = xWindow + getXStatic(18);
        wLogo = getXStatic(250);
        hLogo = getYStatic(80);

        xSearch = xWindow + getXStatic(324);
        ySearch = yWindow + getYStatic(18);
        wSearch = getXStatic(700);
        hSearch = getYStatic(40);

        xSetting = xWindow + getXStatic(18);
        wSetting = getXStatic(1160);
        hSetting = getYStatic(70);

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Helper2D.drawRoundedRectangle((int) xWindow, (int) yWindow, (int) wWindow, (int) hWindow,4, VeyzenClient.bg.getRGB(),0);
        Helper2D.drawRoundedRectangle((int) xLogo, (int) yLogo, (int) wLogo, (int) hLogo,4, VeyzenClient.bgcomp.getRGB(),0);
        Helper2D.drawPicture((int)(xLogo + wLogo / 2 - getXStatic(106)), (int) yLogo - (int)(getYStatic(20)), (int) getXStatic(212), (int) getYStatic(120),VeyzenClient.fg.getRGB(),new ResourceLocation("veyzen","veyzen_nobg.png"));

        search.render(mouseX,mouseY,mc);
        int yOff = 0;
        int yStart = (int)(yWindow + getYStatic(105));
        for(Setting s : ModManager.globalSettings){
            drawSetting(s,yStart + yOff,mouseX,mouseY);
            yOff += (int)(hSetting + 2);
        }
        if(focused != null){
            Helper2D.drawRoundedRectangle((int) (xSetting + wSetting + getXStatic(30)),
                    (int) yWindow - (int)getYStatic(10),
                    (int) getXStatic(210),
                    (int) getYStatic(250),
                    4,
                    VeyzenClient.bg.getRGB(),
                    0
                    );
            focused.render((int)(xSetting + wSetting + getXStatic(40)),(int)yWindow,mouseX,mouseY);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);

    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        search.mouseClick(mouseX,mouseY,mouseButton);



        ModManager.globalSettings.forEach(s -> s.mouseClick(mouseX,mouseY,mouseButton));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        search.keyTyped(typedChar, keyCode);
        ModManager.globalSettings.forEach(s -> {
            s.keyTyped(typedChar,keyCode);
            if(Objects.equals(s.id, "menu")){
                VeyzenClient.configKey.setKeyCode(s.getAsType(Keybind.class).keycode);
            }
        });
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        ModManager.globalSettings.forEach(s -> s.mouseReleased(mouseX,mouseY,state));
        if(focused != null){
            if(Objects.equals(focused.id, "bg")){
                VeyzenClient.bg = focused.getAsType(ColorPicker.class).getColor();
            }
            if(Objects.equals(focused.id, "bgc")){
                VeyzenClient.bgcomp = focused.getAsType(ColorPicker.class).getColor();
            }
            if(Objects.equals(focused.id, "fg")){
                VeyzenClient.fg = focused.getAsType(ColorPicker.class).getColor();
            }
            if(Objects.equals(focused.id, "tc")){
                VeyzenClient.text = focused.getAsType(ColorPicker.class).getColor();
            }
        }
    }
}
