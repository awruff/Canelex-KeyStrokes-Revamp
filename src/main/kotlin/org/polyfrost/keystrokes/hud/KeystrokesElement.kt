package org.polyfrost.keystrokes.hud

import cc.polyfrost.oneconfig.config.annotations.Color
import cc.polyfrost.oneconfig.config.annotations.Slider
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.hud.Hud
import cc.polyfrost.oneconfig.libs.universal.UGraphics
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack
import net.minecraft.client.Minecraft

class KeystrokesElement : Hud(true, 0f, 0f, 1f) {
    @Switch(name = "Enable Mouse Keystrokes")
    var mouseKeystrokes: Boolean = true

    @Switch(name = "Enable Mouse CPS")
    var mouseCPS: Boolean = false

    @Switch(name = "Jump (Space) Keystrokes")
    var jumpKeystrokes: Boolean = false

    @Switch(name = "Arrow Keystrokes")
    var arrows: Boolean = false

    @Color(name = "Unpressed Background Color")
    var bgUnpressed: OneColor = OneColor(-922746880)

    @Color(name = "Unpressed Text Color")
    var textUnpressed: OneColor = OneColor(-1)

    @Color(name = "Pressed Background Color")
    var bgPressed: OneColor = OneColor(-905969665)

    @Color(name = "Pressed Text Color")
    var textPressed: OneColor = OneColor(-16777216)

    @Slider(name = "Fading Time", min = 1f, max = 250f, step = 1)
    var fadingTime: Int = 100

    @Switch(name = "Shadow while Idle")
    var shadowIdle: Boolean = false

    @Switch(name = "Shadow while Pressed")
    var shadowActive: Boolean = false

    @Switch(name = "Rounded Corners")
    var rounded: Boolean = false

    @Slider(name = "Corner radius", min = 0f, max = 10f)
    var cornerRadius: Int = 2

    @Transient
    val movementKeys: Array<GuiKey>

    @Transient
    private val mouseKeys: Array<GuiKeyMouse>

    @Transient
    private val jumpKey: GuiKey

    init {
        val gs = Minecraft.getMinecraft().gameSettings

        this.movementKeys = arrayOf<GuiKey>(
            GuiKey(20f, 0f, 19f, 19f, gs.keyBindForward), // W
            GuiKey(0f, 20f, 19f, 19f, gs.keyBindLeft),    // A
            GuiKey(20f, 20f, 19f, 19f, gs.keyBindBack),   // S
            GuiKey(40f, 20f, 19f, 19f, gs.keyBindRight),  // D
        )

        this.mouseKeys = arrayOf<GuiKeyMouse>(
            GuiKeyMouse(0f, 40f, 29f, 19f, gs.keyBindAttack),   // LMB
            GuiKeyMouse(30f, 40f, 29f, 19f, gs.keyBindUseItem), // RMB
        )

        this.jumpKey = GuiKeySpace(0f, 60f, 59f, 11f, gs.keyBindJump) // space
    }

    override fun draw(matrices: UMatrixStack?, x: Float, y: Float, scale: Float, example: Boolean) {
        UGraphics.GL.pushMatrix()
        UGraphics.GL.translate(-x * (scale - 1.0f), -y * (scale - 1.0f), 0.0f)
        UGraphics.GL.scale(scale, scale, 1.0f)

        for (key in this.movementKeys) {
            key.updateState()
            key.render(x, y, scale)
        }

        if (mouseKeystrokes) {
            for (key in this.mouseKeys) {
                key.updateState()
                key.render(x, y, scale)
            }
        }

        if (jumpKeystrokes) {
            jumpKey.relY = (if (mouseKeystrokes) 60 else 40).toFloat()
            jumpKey.updateState()
            jumpKey.render(x, y, scale)
        }

        UGraphics.GL.popMatrix()
    }

    public override fun getWidth(scale: Float, example: Boolean): Float {
        return (59 * scale)
    }

    // FIXME: Inaccurate with cbKeyRects
    public override fun getHeight(scale: Float, example: Boolean): Float {
        var height = 39
        if (mouseKeystrokes) height += 20
        if (jumpKeystrokes) height += 12
        return (height * scale)
    }

    fun pressed(keycode: Int) {
        for (mouseKey in mouseKeys) {
            mouseKey.pressed(keycode)
        }
    }
}
