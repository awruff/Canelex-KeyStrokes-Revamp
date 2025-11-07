package org.polyfrost.keystrokes.command

import cc.polyfrost.oneconfig.utils.commands.annotations.Command
import cc.polyfrost.oneconfig.utils.commands.annotations.Main
import org.polyfrost.keystrokes.Keystrokes
import org.polyfrost.keystrokes.config.KeystrokesConfig

@Command(value = Keystrokes.ID, aliases = ["keystrokes", "keys", "keygui"])
object KeystrokesCommand {
    @Main
    fun execute() {
        KeystrokesConfig.openGui()
    }
}