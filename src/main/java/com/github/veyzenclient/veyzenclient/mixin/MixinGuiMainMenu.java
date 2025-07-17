package com.github.veyzenclient.veyzenclient.mixin;

import com.github.veyzenclient.veyzenclient.gui.TitleScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public abstract class MixinGuiMainMenu {

    @Inject(method = "initGui", at = @At("HEAD"))
    public void initGui(CallbackInfo ci) {
        Minecraft.getMinecraft().updateDisplay();
        Minecraft.getMinecraft().displayGuiScreen(new TitleScreen());
    }
}
