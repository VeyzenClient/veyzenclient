package com.github.veyzenclient.veyzenclient.mixin;

import com.github.veyzenclient.veyzenclient.splash.CustomSplash;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Shadow public abstract TextureManager getTextureManager();

    @Redirect(remap = false,method = "startGame",at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/client/SplashProgress;drawVanillaScreen(Lnet/minecraft/client/renderer/texture/TextureManager;)V"))
    public void drawSplash(TextureManager tm){
        CustomSplash.draw();
    }

    @Inject(method = "drawSplashScreen", at = @At("HEAD"), cancellable = true)
    public void overrideSplash(TextureManager textureManagerInstance, CallbackInfo ci) {
        ci.cancel();
    }

    @Redirect(remap = false,method = "startGame",at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/client/SplashProgress;clearVanillaResources(Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/util/ResourceLocation;)V"))
    public void clearVanillaResources(TextureManager tm, ResourceLocation rl){

    }

    @Inject(method = "startGame",at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;initStream()V"))
    public void start(CallbackInfo ci){
        CustomSplash.update(1,"Initialising");
    }
    @Inject(method = "startGame",at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;"))
    public void startGame(CallbackInfo ci){
        CustomSplash.update(2,"Loading Helpers");
    }

    @Inject(method = "startGame",at = @At(value = "INVOKE", target = "Lnet/minecraft/stats/Achievement;setStatStringFormatter(Lnet/minecraft/stats/IStatStringFormat;)Lnet/minecraft/stats/Achievement;"))
    public void update1(CallbackInfo ci){
        CustomSplash.update(3,"Initialising Skins");
    }

    @Inject(method = "startGame",at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;enableTexture2D()V"))
    public void update2(CallbackInfo ci){
        CustomSplash.update(4,"Initialising OpenGL");
    }

    @Inject(method = "startGame",at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;matrixMode(I)V"))
    public void update3(CallbackInfo ci){
        CustomSplash.update(5,"Initialising OpenGL");
    }

    @Inject(method = "startGame",at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;renderGlobal:Lnet/minecraft/client/renderer/RenderGlobal;"))
    public void updateLast(CallbackInfo ci){
        CustomSplash.update(6,"Finishing Up");
    }

}
