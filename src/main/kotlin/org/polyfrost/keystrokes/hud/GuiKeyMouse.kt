package org.polyfrost.keystrokes.hud

import net.minecraft.client.settings.KeyBinding
import org.polyfrost.keystrokes.config.KeystrokesConfig.keystrokesElement
import org.polyfrost.keystrokes.hud.api.KeyRenderer.drawBackground
import org.polyfrost.keystrokes.hud.api.KeyRenderer.drawCenteredText
import java.util.*

class GuiKeyMouse(x: Float, y: Float, width: Float, height: Float, keyBinding: KeyBinding) : GuiKey(x, y, width, height, keyBinding) {
    private var pressed = false
    private val clicks: Queue<Long?> = LinkedList<Long?>()

    override fun render(baseX: Float, baseY: Float, scale: Float) {
        val x = baseX + relX * scale
        val y = baseY + relY * scale
        val w = width * scale
        val h = height * scale

        drawBackground(x, y, w, h, style, isPressed, percentFaded, scale)

        if (keystrokesElement.mouseCPS && this.cps > 0) {
            drawCenteredText(fr, this.cps.toString() + "", x, y, w, h, style, isPressed, percentFaded)
        } else {
            drawCenteredText(fr, keyName, x, y, w, h, style, isPressed, percentFaded)
        }
    }

    override fun isKeyDown(code: Int): Boolean {
        val down = super.isKeyDown(code) || pressed
        if (pressed) pressed = false
        return down
    }

    fun pressed(code: Int) {
        if (code != this.keyBinding.keyCode) return
        clicks.add(System.currentTimeMillis() + 1000L)
        pressed = true
    }

    val cps: Int
        get() {
            val time = System.currentTimeMillis()

            while (!clicks.isEmpty() && clicks.peek()!! < time) {
                clicks.remove()
            }

            return clicks.size
        }
}
