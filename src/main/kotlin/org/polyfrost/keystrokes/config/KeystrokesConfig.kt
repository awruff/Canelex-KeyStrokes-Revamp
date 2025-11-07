package org.polyfrost.keystrokes.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.HUD
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import org.polyfrost.keystrokes.hud.KeystrokesElement
import org.polyfrost.keystrokes.CB_LAYOUT
import org.polyfrost.keystrokes.Keystrokes
import org.polyfrost.keystrokes.NORMAL_LAYOUT

object KeystrokesConfig : Config(Mod(Keystrokes.NAME, ModType.HUD, "/keystrokesrevamp_dark.svg"), "keystrokes.json") {
    @Switch(
        name = "Early 2017 CB Keys",
        description = "Makes the WASD keys rectangles like CB in 2017.",
        size = 2,
        subcategory = "Keystrokes"
    )
    var cbKeys: Boolean = false

    @HUD(name = "Keystrokes")
    var keystrokesElement: KeystrokesElement = KeystrokesElement()

    init {
        initialize()
        updateKeys()
        addListener("cbKeys") { this.updateKeys() }
    }

    private fun updateKeys() {
        val layout = if (cbKeys) CB_LAYOUT else NORMAL_LAYOUT
        for (i in keystrokesElement.movementKeys.indices) {
            keystrokesElement.movementKeys[i].setLayout(layout[i][0].toFloat(), layout[i][1].toFloat())
        }
    }
}