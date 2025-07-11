package com.github.veyzenclient.veyzenclient.mixin;

import net.minecraftforge.fml.client.SplashProgress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(value = SplashProgress.class,remap = false)
public class MixinSplashProgress {

    @Inject(method = "start",at = @At("HEAD"), cancellable = true)
    private static void start(CallbackInfo ci){
        ci.cancel();
    }
}
