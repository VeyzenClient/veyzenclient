package com.github.veyzenclient.veyzenclient.utils.animation;

public class DeltaTime {

    private static int deltaTime = 0;

    public static int getDeltaTime() {
        return deltaTime;
    }

    public static void setDeltaTime(int deltaTime) {
        DeltaTime.deltaTime = deltaTime;
    }
}
