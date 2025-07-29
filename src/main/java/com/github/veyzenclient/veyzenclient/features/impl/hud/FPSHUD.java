package com.github.veyzenclient.veyzenclient.features.impl.hud;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.config.settings.impl.*;
import com.github.veyzenclient.veyzenclient.features.ModCategory;
import com.github.veyzenclient.veyzenclient.features.ModHUD;
import com.github.veyzenclient.veyzenclient.features.Register;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

@Register
public class FPSHUD extends ModHUD {

    public FPSHUD() {
        super(15, 15, 60, 20, "fps", "FPS HUD", "Shows ur FPS in a hud", new ResourceLocation(VeyzenClient.NAMESPACE,"icons/mods/fps.png"), ModCategory.QOL);
        addSetting(new Slider("slider","Slider","Test Slider","General",1f,10f,5f));
        addSetting(new TextField("textfield","Text Field","custom text field","Test Parent", TextField.TextFilter.NUMERIC,"123",64));
        addSetting(new Keybind("keybind","Key Binding","Custom key binding","Test Parent", Keyboard.KEY_P));
        addSetting(new ColorPicker("color","Color Picker","Custom color picker","Test Parent 2",1f,1f,1f,1f));
        addSetting(new Button("testbutton","Button","Custom button","Test Parent 2","run","Run"));
        addSetting(new Text("text","Text","General",VeyzenClient.text,"Text Test"));
    }

    @Override
    public void render() {

    }
}
