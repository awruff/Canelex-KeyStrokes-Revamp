package org.polyfrost.keystrokes.hud

import net.minecraft.client.settings.KeyBinding
import org.polyfrost.keystrokes.hud.api.KeyRenderer.drawBackground
import org.polyfrost.keystrokes.hud.api.KeyRenderer.drawSpaceGlyph

class GuiKeySpace(x: Float, y: Float, width: Float, height: Float, keyBinding: KeyBinding) : GuiKey(x, y, width, height, keyBinding) {
    override fun render(baseX: Float, baseY: Float, scale: Float) {
        val x = baseX + relX * scale
        val y = baseY + relY * scale
        val w = width * scale
        val h = height * scale

        drawBackground(x, y, w, h, style, isPressed, percentFaded, scale)

        val color = style.textColor(isPressed, percentFaded)
        val shadow = if (isPressed) style.isShadowActive else style.isShadowIdle
        drawSpaceGlyph(x, y, w, h, color, shadow)
    }

    override val keyName: String
        get() = "" // not used for space rendering
}
