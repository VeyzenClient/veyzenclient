package com.github.veyzenclient.veyzenclient.utils;

import net.minecraft.util.ResourceLocation;

public class AnimatedResourceLocation {

    private final String basePath;
    private final int frameCount;
    private final int fps;
    private final ResourceLocation[] frames;

    private int currentFrame = 0;
    private long lastFrameTime = System.currentTimeMillis();
    private boolean looping = true;
    private boolean finished = false;

    public AnimatedResourceLocation(String basePath, int frameCount, int fps) {
        this.basePath = basePath;
        this.frameCount = frameCount;
        this.fps = fps;
        this.frames = new ResourceLocation[frameCount];

        for (int i = 0; i < frameCount; i++) {
            this.frames[i] = new ResourceLocation(basePath + i + ".png");
        }
    }

    public void update() {
        if (finished) return;

        long now = System.currentTimeMillis();
        long frameDuration = 1000L / fps;

        if (now - lastFrameTime >= frameDuration) {
            currentFrame++;
            lastFrameTime = now;

            if (currentFrame >= frameCount) {
                if (looping) {
                    currentFrame = 0;
                } else {
                    currentFrame = frameCount - 1;
                    finished = true;
                }
            }
        }
    }

    public ResourceLocation getCurTexture() {
        return frames[Math.max(0, Math.min(currentFrame, frameCount - 1))];
    }

    public void reset() {
        currentFrame = 0;
        finished = false;
        lastFrameTime = System.currentTimeMillis();
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    public boolean isLooping() {
        return looping;
    }

    public boolean isFinished() {
        return finished;
    }
}