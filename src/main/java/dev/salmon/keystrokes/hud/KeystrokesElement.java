package dev.salmon.keystrokes.hud;

import cc.polyfrost.oneconfig.config.annotations.Color;
import cc.polyfrost.oneconfig.config.annotations.Slider;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UGraphics;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

public class KeystrokesElement extends Hud {

    @Switch(name = "Enable Mouse Keystrokes")
    public boolean mouseKeystrokes = true;

    @Switch(name = "Enable Mouse CPS")
    public boolean mouseCPS = false;

    @Switch(name = "Jump (Space) Keystrokes")
    public boolean jumpKeystrokes = false;

    @Switch(name = "Arrow Keystrokes")
    public boolean arrows = false;

    @Color(name = "Unpressed Background Color")
    public OneColor bgUnpressed = new OneColor(-922746880);

    @Color(name = "Unpressed Text Color")
    public OneColor textUnpressed = new OneColor(-1);

    @Color(name = "Pressed Background Color")
    public OneColor bgPressed = new OneColor(-905969665);

    @Color(name = "Pressed Text Color")
    public OneColor textPressed = new OneColor(-16777216);

    @Slider(name = "Fading Time", min = 1F, max = 250F, step = 1)
    public int fadingTime = 100;

    @Switch(name = "Shadow while Idle")
    public boolean shadowIdle = false;

    @Switch(name = "Shadow while Pressed")
    public boolean shadowActive = false;

    @Switch(name = "Rounded Corners")
    public boolean rounded;

    @Slider(name = "Corner radius", min = 0f, max = 10f)
    public int cornerRadius = 2;

    public transient final GuiKey[] movementKeys;
    private transient final GuiKeyMouse[] mouseKeys;
    private transient final GuiKey jumpKey;

    public KeystrokesElement() {
        super(true, 0, 0, 1);
        GameSettings gs = Minecraft.getMinecraft().gameSettings;

        this.movementKeys = new GuiKey[4];
        this.movementKeys[0] = new GuiKey(20, 0, 19, 19, gs.keyBindForward); // W
        this.movementKeys[1] = new GuiKey(0, 20, 19, 19, gs.keyBindLeft);    // A
        this.movementKeys[2] = new GuiKey(20, 20, 19, 19, gs.keyBindBack);   // S
        this.movementKeys[3] = new GuiKey(40, 20, 19, 19, gs.keyBindRight);  // D

        this.mouseKeys = new GuiKeyMouse[2];
        this.mouseKeys[0] = new GuiKeyMouse(0, 40, 29, 19, gs.keyBindAttack);   // LMB
        this.mouseKeys[1] = new GuiKeyMouse(30, 40, 29, 19, gs.keyBindUseItem); // RMB

        this.jumpKey = new GuiKeySpace(0, 60, 59, 11, gs.keyBindJump); // space
    }

    @Override
    protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
        UGraphics.GL.pushMatrix();
        UGraphics.GL.translate(-x * (scale - 1.0F), -y * (scale - 1.0F), 0.0F);
        UGraphics.GL.scale(scale, scale, 1.0F);

        for (GuiKey key : this.movementKeys) {
            key.updateState();
            key.render(x, y, scale);
        }

        if (mouseKeystrokes) {
            for (GuiKeyMouse key : this.mouseKeys) {
                key.updateState();
                key.render(x, y, scale);
            }
        }

        if (jumpKeystrokes) {
            jumpKey.relY = mouseKeystrokes ? 60 : 40;
            jumpKey.updateState();
            jumpKey.render(x, y, scale);
        }

        UGraphics.GL.popMatrix();
    }

    @Override
    public float getWidth(float scale, boolean example) {
        return (59 * scale);
    }

    // FIXME: Inaccurate with cbKeyRects
    @Override
    public float getHeight(float scale, boolean example) {
        int height = 39;
        if (mouseKeystrokes)
            height += 20;
        if (jumpKeystrokes)
            height += 12;
        return (height * scale);
    }

    public void pressed(int keycode) {
        for (GuiKeyMouse mouseKey : mouseKeys) {
            mouseKey.pressed(keycode);
        }
    }
}
