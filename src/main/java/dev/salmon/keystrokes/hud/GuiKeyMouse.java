package dev.salmon.keystrokes.hud;

import dev.salmon.keystrokes.config.KeystrokesConfig;
import dev.salmon.keystrokes.hud.api.KeyRenderer;
import net.minecraft.client.settings.KeyBinding;

import java.util.LinkedList;
import java.util.Queue;

public class GuiKeyMouse extends GuiKey {
    private boolean pressed;
    private final Queue<Long> clicks = new LinkedList<>();

    public GuiKeyMouse(float x, float y, float width, float height, KeyBinding keyBinding) {
        super(x, y, width, height, keyBinding);
    }

    @Override
    public void render(float baseX, float baseY, float scale) {
        float x = baseX + relX * scale;
        float y = baseY + relY * scale;
        float w = width * scale;
        float h = height * scale;

        KeyRenderer.drawBackground(x, y, w, h, style, isPressed, percentFaded, scale);

        if (KeystrokesConfig.keystrokesElement.mouseCPS && getCps() > 0) {
                KeyRenderer.drawCenteredText(fr, this.getCps() + "", x, y, w, h, style, isPressed, percentFaded);
        } else {
            KeyRenderer.drawCenteredText(fr, getKeyName(), x, y, w, h, style, isPressed, percentFaded);
        }
    }

    @Override
    protected boolean isKeyDown(int code) {
        boolean down = super.isKeyDown(code) || pressed;
        if (pressed) pressed = false;
        return down;
    }

    public void pressed(int code) {
        if (code != this.keyBinding.getKeyCode()) return;
        clicks.add(System.currentTimeMillis() + 1000L);
        pressed = true;
    }

    public int getCps() {
        long time = System.currentTimeMillis();

        while (!clicks.isEmpty() && clicks.peek() < time) {
            clicks.remove();
        }

        return clicks.size();
    }
}
