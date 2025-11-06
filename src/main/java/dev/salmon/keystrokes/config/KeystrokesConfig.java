package dev.salmon.keystrokes.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.HUD;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import dev.salmon.keystrokes.Keystrokes;
import dev.salmon.keystrokes.hud.KeystrokesElement;

public class KeystrokesConfig extends Config {
    private static final int CB_HEIGHT = 16;
    private static final int NORMAL_HEIGHT = 19;

    @Switch(
            name = "Early 2017 CB Keys",
            description = "Makes the WASD keys rectangles to replicate the look of an early 2017 CB Keystrokes Mod.",
            size = 2,
            subcategory = "Keystrokes"
    )
    public static boolean cbKeyRects = false;

    @HUD(
            name = "Keystrokes"
    )
    public static KeystrokesElement keystrokesElement = new KeystrokesElement();

    public KeystrokesConfig() {
        super(new Mod(Keystrokes.NAME, ModType.HUD, "/keystrokesrevamp_dark.svg"), "keystrokes.json");
        initialize();
        updateKeys();

        addListener("cbKeyRects", KeystrokesConfig::updateKeys);
    }

    // TODO: Improve Me!
    // These are constant values.. there is no need for it to be done this way
    public static void updateKeys() {
        if (cbKeyRects) {
            keystrokesElement.movementKeys[0].height = CB_HEIGHT;
            keystrokesElement.movementKeys[0].relY = 6;
            keystrokesElement.movementKeys[1].height = CB_HEIGHT;
            keystrokesElement.movementKeys[1].relY = 23;
            keystrokesElement.movementKeys[2].height = CB_HEIGHT;
            keystrokesElement.movementKeys[2].relY = 23;
            keystrokesElement.movementKeys[3].height = CB_HEIGHT;
            keystrokesElement.movementKeys[3].relY = 23;
        } else {
            keystrokesElement.movementKeys[0].height = NORMAL_HEIGHT;
            keystrokesElement.movementKeys[0].relY = 0;
            keystrokesElement.movementKeys[1].height = NORMAL_HEIGHT;
            keystrokesElement.movementKeys[1].relY = 20;
            keystrokesElement.movementKeys[2].height = NORMAL_HEIGHT;
            keystrokesElement.movementKeys[2].relY = 20;
            keystrokesElement.movementKeys[3].height = NORMAL_HEIGHT;
            keystrokesElement.movementKeys[3].relY = 20;
        }
    }
}
