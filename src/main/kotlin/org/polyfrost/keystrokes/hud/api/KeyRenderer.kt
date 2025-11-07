package org.polyfrost.keystrokes.hud.api

import cc.polyfrost.oneconfig.platform.Platform
import cc.polyfrost.oneconfig.renderer.NanoVGHelper
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.renderer.GlStateManager

object KeyRenderer {
    @JvmStatic
    fun drawBackground(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        style: KeyStyle,
        pressed: Boolean,
        fade: Float,
        scale: Float
    ) {
        val color = style.backgroundColor(pressed, fade)

        if (style.isRounded) {
            val radius = style.cornerRadius * scale
            NanoVGHelper.INSTANCE.setupAndDraw(
                true
            ) { vg: Long ->
                NanoVGHelper.INSTANCE.drawRoundedRect(
                    vg,
                    x,
                    y,
                    width,
                    height,
                    color,
                    radius
                )
            }
        } else {
            Platform.getGLPlatform().drawRect(x, y, x + width, y + height, color)
        }
    }

    @JvmStatic
    fun drawCenteredText(
        fr: FontRenderer,
        text: String?,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        style: KeyStyle,
        pressed: Boolean,
        fade: Float
    ) {
        val color = style.textColor(pressed, fade)
        val shadow = if (pressed) style.isShadowActive else style.isShadowIdle

        val textX = x + (width - fr.getStringWidth(text) + 1) / 2f
        val textY = y + (height - fr.FONT_HEIGHT + 2) / 2f

        GlStateManager.enableBlend()
        fr.drawString(text, textX, textY, color, shadow)
        GlStateManager.disableBlend()
    }

    @JvmStatic
    fun drawSpaceGlyph(x: Float, y: Float, width: Float, height: Float, color: Int, shadow: Boolean) {
        var color = color
        val cx = x + width / 2f
        val cy = y + height / 2f

        drawHorizontalLine(cx - 6, cx + 5, cy - 0.5f, color)

        if (shadow) {
            if ((color and -0x4000000) == 0) color = color or -0x1000000
            val shadowColor = (color and 0xFCFCFC) shr 2 or (color and -0x1000000)
            drawHorizontalLine(cx - 5, cx + 6, cy + 0.5f, shadowColor)
        }
    }

    private fun drawHorizontalLine(startX: Float, endX: Float, y: Float, color: Int) {
        var startX = startX
        var endX = endX
        if (endX < startX) {
            val tmp = startX
            startX = endX
            endX = tmp
        }
        Platform.getGLPlatform().drawRect(startX, y, endX + 1, y + 1, color)
    }
}