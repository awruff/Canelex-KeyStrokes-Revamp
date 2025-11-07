package org.polyfrost.keystrokes.hud

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.gui.Gui
import net.minecraft.client.settings.KeyBinding
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import org.polyfrost.keystrokes.config.KeystrokesConfig
import org.polyfrost.keystrokes.hud.api.KeyRenderer
import org.polyfrost.keystrokes.hud.api.KeyStyle
import kotlin.math.min

open class GuiKey(
    protected var relX: Float,
    var relY: Float,
    protected var width: Float,
    protected var height: Float,
    protected val keyBinding: KeyBinding
) : Gui() {
    protected val style: KeyStyle = KeyStyle

    protected val fr: FontRenderer = Minecraft.getMinecraft().fontRendererObj

    protected var isPressed: Boolean = false

    protected var percentFaded: Float = 1.0f
    private var lastPress: Long

    init {
        this.lastPress = System.currentTimeMillis()
    }

    fun setLayout(height: Float, relY: Float) {
        this.height = height
        this.relY = relY
    }

    fun updateState() {
        val nowDown = isKeyDown(keyBinding.keyCode)
        if (nowDown != isPressed) {
            isPressed = nowDown
            lastPress = System.currentTimeMillis()
            percentFaded = 0.0f
        }
        val dt = System.currentTimeMillis() - lastPress
        percentFaded = min(1.0f, dt / style.fadingTime.toFloat())
    }

    open fun render(baseX: Float, baseY: Float, scale: Float) {
        val x = baseX + relX * scale
        val y = baseY + relY * scale
        val w = width * scale
        val h = height * scale

        KeyRenderer.drawBackground(x, y, w, h, style, isPressed, percentFaded, scale)
        KeyRenderer.drawCenteredText(fr, this.keyName, x, y, w, h, style, isPressed, percentFaded)
    }

    protected open fun isKeyDown(code: Int): Boolean {
        return if (code < 0) Mouse.isButtonDown(code + 100) else Keyboard.isKeyDown(code)
    }

    private val keysMap = mapOf(
        -100 to "LMB",
        -99 to "RMB",
        -98 to "MMB",
        200 to "U",
        203 to "L",
        205 to "R",
        208 to "D",
        210 to "INS",
        29 to "LCTRL",
        157 to "RCTRL",
        56 to "LALT",
        184 to "RALT"
    )

    protected open val keyName: String?
        get() {
            val gs = Minecraft.getMinecraft().gameSettings

            if (KeystrokesConfig.keystrokesElement.arrows) {
                return when (keyBinding) {
                    gs.keyBindForward -> "▲"
                    gs.keyBindBack -> "▼"
                    gs.keyBindLeft -> "◀"
                    gs.keyBindRight -> "▶"
                    else -> null
                }
            }

            val code = keyBinding.keyCode

            keysMap[code]?.let { return it }

            return when (code) {
                in 0..223 -> Keyboard.getKeyName(code)
                in -100..-84 -> Mouse.getButtonName(code + 100)
                else -> "[]"
            }
        }
}