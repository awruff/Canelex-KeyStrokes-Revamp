package dev.salmon.keystrokes.hud;

import dev.salmon.keystrokes.hud.api.KeyRenderer;
import net.minecraft.client.settings.KeyBinding;

public class GuiKeySpace extends GuiKey {
    public GuiKeySpace(float x, float y, float width, float height, KeyBinding keyBinding) {
        super(x, y, width, height, keyBinding);
    }

    @Override
    public void render(float baseX, float baseY, float scale) {
        float x = baseX + relX * scale;
        float y = baseY + relY * scale;
        float w = width * scale;
        float h = height * scale;

        KeyRenderer.drawBackground(x, y, w, h, style, isPressed, percentFaded, scale);

        int color = style.textColor(isPressed, percentFaded);
        boolean shadow = isPressed ? style.isShadowActive() : style.isShadowIdle();
        KeyRenderer.drawSpaceGlyph(x, y, w, h, color, shadow);
    }

    @Override
    protected String getKeyName() {
        return ""; // not used for space rendering
    }
}
