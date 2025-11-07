package org.polyfrost.keystrokes

import cc.polyfrost.oneconfig.events.EventManager
import cc.polyfrost.oneconfig.events.event.KeyInputEvent
import cc.polyfrost.oneconfig.events.event.MouseInputEvent
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe
import cc.polyfrost.oneconfig.utils.commands.CommandManager
import org.polyfrost.keystrokes.command.KeystrokesCommand
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import org.polyfrost.keystrokes.config.KeystrokesConfig

@Mod(
    modid = Keystrokes.ID,
    name = Keystrokes.NAME,
    version = Keystrokes.VERSION,
    modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter"
)
object Keystrokes {
    const val ID = "@MOD_ID@"
    const val NAME = "@MOD_NAME@"
    const val VERSION = "@MOD_VERSION@"

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        EventManager.INSTANCE.register(this)
        CommandManager.INSTANCE.registerCommand(KeystrokesCommand)
        KeystrokesConfig.preload()
    }

    @Subscribe
    fun keyboardInput(event: KeyInputEvent) {
        if (Keyboard.getEventKeyState()) {
            KeystrokesConfig.keystrokesElement.pressed(Keyboard.getEventKey())
        }
    }

    @Subscribe
    fun mouseInput(event: MouseInputEvent) {
        if (Mouse.getEventButtonState()) {
            KeystrokesConfig.keystrokesElement.pressed(mapMouseButton(Mouse.getEventButton()))
        }
    }

    private fun mapMouseButton(button: Int) = button - 100

}
