package dev.salmon.keystrokes.hud.api;

import cc.polyfrost.oneconfig.platform.Platform;
import cc.polyfrost.oneconfig.renderer.NanoVGHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;

public final class KeyRenderer {
    private KeyRenderer() {}

    public static void drawBackground(float x, float y, float width, float height, KeyStyle style, boolean pressed, float fade, float scale) {
        int color = style.backgroundColor(pressed, fade);

        if (style.isRounded()) {
            float radius = style.getCornerRadius() * scale;
            NanoVGHelper.INSTANCE.setupAndDraw(true, vg ->
                    NanoVGHelper.INSTANCE.drawRoundedRect(vg, x, y, width, height, color, radius)
            );
        } else {
            Platform.getGLPlatform().drawRect(x, y, x + width, y + height, color);
        }
    }

    public static void drawCenteredText(FontRenderer fr, String text, float x, float y, float width, float height, KeyStyle style, boolean pressed, float fade) {
        int color = style.textColor(pressed, fade);
        boolean shadow = pressed ? style.isShadowActive() : style.isShadowIdle();

        float textX = x + (width - fr.getStringWidth(text) + 1) / 2f;
        float textY = y + (height - fr.FONT_HEIGHT + 2) / 2f;

        GlStateManager.enableBlend();
        fr.drawString(text, textX, textY, color, shadow);
        GlStateManager.disableBlend();
    }

    public static void drawSpaceGlyph(float x, float y, float width, float height, int color, boolean shadow) {
        float cx = x + width / 2f;
        float cy = y + height / 2f;

        drawHorizontalLine(cx - 6, cx + 5, cy - 0.5f, color);

        if (shadow) {
            if ((color & 0xFC000000) == 0) color |= 0xFF000000;
            int shadowColor = (color & 0xFCFCFC) >> 2 | (color & 0xFF000000);
            drawHorizontalLine(cx - 5, cx + 6, cy + 0.5f, shadowColor);
        }
    }

    private static void drawHorizontalLine(float startX, float endX, float y, int color) {
        if (endX < startX) {
            float tmp = startX; startX = endX; endX = tmp;
        }
        Platform.getGLPlatform().drawRect(startX, y, endX + 1, y + 1, color);
    }
}