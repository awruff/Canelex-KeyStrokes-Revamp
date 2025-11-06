package dev.salmon.keystrokes.hud;

import dev.salmon.keystrokes.config.KeystrokesConfig;
import dev.salmon.keystrokes.hud.api.KeyRenderer;
import dev.salmon.keystrokes.hud.api.KeyStyle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiKey extends Gui {
    protected final KeyStyle style = KeyStyle.INSTANCE;

    protected final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
    protected final KeyBinding keyBinding;

    protected float relX, relY, width, height;
    protected boolean isPressed;
    protected float percentFaded;
    private long lastPress;

    public GuiKey(float x, float y, float width, float height, KeyBinding keyBinding) {
        this.relX = x;
        this.relY = y;
        this.width = width;
        this.height = height;
        this.keyBinding = keyBinding;
        this.percentFaded = 1.0F;
        this.lastPress = System.currentTimeMillis();
    }

    public void setLayout(float height, float relY) {
        this.height = height;
        this.relY = relY;
    }

    public void updateState() {
        boolean nowDown = isKeyDown(keyBinding.getKeyCode());
        if (nowDown != isPressed) {
            isPressed = nowDown;
            lastPress = System.currentTimeMillis();
            percentFaded = 0.0F;
        }
        long dt = System.currentTimeMillis() - lastPress;
        percentFaded = Math.min(1.0F, dt / (float) style.getFadingTime());
    }

    public void render(float baseX, float baseY, float scale) {
        float x = baseX + relX * scale;
        float y = baseY + relY * scale;
        float w = width * scale;
        float h = height * scale;

        KeyRenderer.drawBackground(x, y, w, h, style, isPressed, percentFaded, scale);
        KeyRenderer.drawCenteredText(fr, getKeyName(), x, y, w, h, style, isPressed, percentFaded);
    }

    protected boolean isKeyDown(int code) {
        return (code < 0) ? Mouse.isButtonDown(code + 100) : Keyboard.isKeyDown(code);
    }

    protected String getKeyName() {
        if (KeystrokesConfig.keystrokesElement.arrows) {
            GameSettings gs = Minecraft.getMinecraft().gameSettings;
            if (keyBinding == gs.keyBindForward) return "▲";
            if (keyBinding == gs.keyBindBack)    return "▼";
            if (keyBinding == gs.keyBindLeft)    return "◀";
            if (keyBinding == gs.keyBindRight)   return "▶";
        }
        int code = this.keyBinding.getKeyCode();
        switch (code) {
            case -100: return "LMB";
            case -99:  return "RMB";
            case -98:  return "MMB";
            case 200:  return "U";
            case 203:  return "L";
            case 205:  return "R";
            case 208:  return "D";
            case 210:  return "INS";
            case 29:   return "LCTRL";
            case 157:  return "RCTRL";
            case 56:   return "LALT";
            case 184:  return "RALT";
        }
        if (code >= 0 && code <= 223) return Keyboard.getKeyName(code);
        if (code >= -100 && code <= -84) return Mouse.getButtonName(code + 100);
        return "[]";
    }
}
