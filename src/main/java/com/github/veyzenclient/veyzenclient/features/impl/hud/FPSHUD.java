package com.github.veyzenclient.veyzenclient.features.impl.hud;

import com.github.veyzenclient.veyzenclient.VeyzenClient;
import com.github.veyzenclient.veyzenclient.features.ModCategory;
import com.github.veyzenclient.veyzenclient.features.ModHUD;
import com.github.veyzenclient.veyzenclient.features.Register;
import net.minecraft.util.ResourceLocation;

@Register
public class FPSHUD extends ModHUD {

    public FPSHUD() {
        super(15, 15, 60, 20, "fps", "FPS HUD", "Shows ur FPS in a hud", new ResourceLocation(VeyzenClient.NAMESPACE,"icons/mods/fps.png"), ModCategory.QOL);
    }

    @Override
    public void render() {

    }
}
