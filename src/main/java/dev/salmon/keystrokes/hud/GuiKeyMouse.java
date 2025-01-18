package dev.salmon.keystrokes.hud;

import cc.polyfrost.oneconfig.libs.universal.UGraphics;
import dev.salmon.keystrokes.config.KeystrokesConfig;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;

import java.util.LinkedList;
import java.util.Queue;

public class GuiKeyMouse extends GuiKey {
    private boolean pressed;
    private final Queue<Long> clicks = new LinkedList<>();

    public GuiKeyMouse(float x, float y, float width, float height, KeyBinding keyBinding) {
        super(x, y, width, height, keyBinding);
    }

    public void drawCps(float x, float y, float scale) {
        String cpsText = getCps() + " CPS";
        x += this.relX + (this.width / 2 - (float) this.fr.getStringWidth(cpsText) / 4);
        y += this.relY + (this.height - this.fr.FONT_HEIGHT * scale) - 2;

        UGraphics.GL.pushMatrix();
        UGraphics.GL.translate(-x * (scale - 1.0F), -y * (scale - 1.0F), 0.0F);
        UGraphics.GL.scale(scale, scale, 1.0F);

        GlStateManager.enableBlend();
        this.fr.drawString(cpsText, x, y, getTextColor(), KeystrokesConfig.keystrokesElement.shadow);
        GlStateManager.disableBlend();

        UGraphics.GL.popMatrix();
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

    private int getCps() {
        long time = System.currentTimeMillis();

        while (!clicks.isEmpty() && clicks.peek() < time) {
            clicks.remove();
        }

        return clicks.size();
    }
}
