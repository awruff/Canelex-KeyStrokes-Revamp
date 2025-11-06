package dev.salmon.keystrokes.hud.api;

import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.utils.color.ColorUtils;
import dev.salmon.keystrokes.config.KeystrokesConfig;

public class KeyStyle {
    public static final KeyStyle INSTANCE = new KeyStyle();

    public int interpolateColor(int toColor, int fromColor, float percent) {
        float avgRed = (toColor >> 16 & 0xFF) * percent + (fromColor >> 16 & 0xFF) * (1.0F - percent);
        float avgGreen = (toColor >> 8 & 0xFF) * percent + (fromColor >> 8 & 0xFF) * (1.0F - percent);
        float avgBlue = (toColor & 0xFF) * percent + (fromColor & 0xFF) * (1.0F - percent);
        float avgAlpha = (toColor >> 24 & 0xFF) * percent + (fromColor >> 24 & 0xFF) * (1.0F - percent);
        return ColorUtils.getColor(avgRed / 255.0F, avgGreen / 255.0F, avgBlue / 255.0F, avgAlpha / 255.0F);
    }

    public int backgroundColor(boolean pressed, float fade) {
        int thisColor = (pressed ? getBgPressed() : getBgUnpressed()).getRGB();
        if (fade < 1.0F) {
            int lastColor = (pressed ? getBgUnpressed() : getBgPressed()).getRGB();
            return interpolateColor(thisColor, lastColor, fade);
        }
        return thisColor;
    }

    public int textColor(boolean pressed, float fade) {
        int thisColor = (pressed ? getTextPressed() : getTextUnpressed()).getRGB();
        if (fade < 1.0F) {
            int lastColor = (pressed ? getTextUnpressed() : getTextPressed()).getRGB();
            return interpolateColor(thisColor, lastColor, fade);
        }
        return thisColor;
    }

    public OneColor getBgUnpressed() {
        return KeystrokesConfig.keystrokesElement.bgUnpressed;
    }

    public OneColor getBgPressed() {
        return KeystrokesConfig.keystrokesElement.bgPressed;
    }

    public OneColor getTextUnpressed() {
        return KeystrokesConfig.keystrokesElement.textUnpressed;
    }

    public OneColor getTextPressed() {
        return KeystrokesConfig.keystrokesElement.textPressed;
    }

    public boolean isShadowIdle() {
        return KeystrokesConfig.keystrokesElement.shadowIdle;
    }

    public boolean isShadowActive() {
        return KeystrokesConfig.keystrokesElement.shadowActive;
    }

    public boolean isRounded() {
        return KeystrokesConfig.keystrokesElement.rounded;
    }

    public int getCornerRadius() {
        return KeystrokesConfig.keystrokesElement.cornerRadius;
    }

    public int getFadingTime() {
        return KeystrokesConfig.keystrokesElement.fadingTime;
    }
}