package org.polyfrost.keystrokes.hud.api

import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.utils.color.ColorUtils
import org.polyfrost.keystrokes.config.KeystrokesConfig

object KeyStyle {
    fun interpolateColor(toColor: Int, fromColor: Int, percent: Float): Int {
        val avgRed = (toColor shr 16 and 0xFF) * percent + (fromColor shr 16 and 0xFF) * (1.0f - percent)
        val avgGreen = (toColor shr 8 and 0xFF) * percent + (fromColor shr 8 and 0xFF) * (1.0f - percent)
        val avgBlue = (toColor and 0xFF) * percent + (fromColor and 0xFF) * (1.0f - percent)
        val avgAlpha = (toColor shr 24 and 0xFF) * percent + (fromColor shr 24 and 0xFF) * (1.0f - percent)
        return ColorUtils.getColor(avgRed / 255.0f, avgGreen / 255.0f, avgBlue / 255.0f, avgAlpha / 255.0f)
    }

    fun backgroundColor(pressed: Boolean, fade: Float): Int {
        val thisColor = (if (pressed) this.bgPressed else this.bgUnpressed).getRGB()
        if (fade < 1.0f) {
            val lastColor = (if (pressed) this.bgUnpressed else this.bgPressed).getRGB()
            return interpolateColor(thisColor, lastColor, fade)
        }
        return thisColor
    }

    fun textColor(pressed: Boolean, fade: Float): Int {
        val thisColor = (if (pressed) this.textPressed else this.textUnpressed).getRGB()
        if (fade < 1.0f) {
            val lastColor = (if (pressed) this.textUnpressed else this.textPressed).getRGB()
            return interpolateColor(thisColor, lastColor, fade)
        }
        return thisColor
    }

    val bgUnpressed: OneColor
        get() = KeystrokesConfig.keystrokesElement.bgUnpressed

    val bgPressed: OneColor
        get() = KeystrokesConfig.keystrokesElement.bgPressed

    val textUnpressed: OneColor
        get() = KeystrokesConfig.keystrokesElement.textUnpressed

    val textPressed: OneColor
        get() = KeystrokesConfig.keystrokesElement.textPressed

    val isShadowIdle: Boolean
        get() = KeystrokesConfig.keystrokesElement.shadowIdle

    val isShadowActive: Boolean
        get() = KeystrokesConfig.keystrokesElement.shadowActive

    val isRounded: Boolean
        get() = KeystrokesConfig.keystrokesElement.rounded

    val cornerRadius: Int
        get() = KeystrokesConfig.keystrokesElement.cornerRadius

    val fadingTime: Int
        get() = KeystrokesConfig.keystrokesElement.fadingTime
}