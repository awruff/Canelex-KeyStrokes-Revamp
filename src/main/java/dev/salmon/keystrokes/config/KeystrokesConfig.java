package dev.salmon.keystrokes.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.HUD;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import dev.salmon.keystrokes.Keystrokes;
import dev.salmon.keystrokes.hud.KeystrokesElement;

public class KeystrokesConfig extends Config {
    private transient final int CB_HEIGHT = 16;
    private transient final int NORMAL_HEIGHT = 19;

    @Switch(
            name = "Early 2017 CB Keys",
            description = "Makes the WASD keys rectangles to replicate the look of an early 2017 CB Keystrokes Mod.",
            size = 2,
            subcategory = "Keystrokes"
    )
    public static boolean cbKeyRects = false;

    @HUD(name = "Keystrokes")
    public static KeystrokesElement keystrokesElement = new KeystrokesElement();

    private transient final int[][] CB_LAYOUT = {
            {CB_HEIGHT, 6},   // W
            {CB_HEIGHT, 23},  // A
            {CB_HEIGHT, 23},  // S
            {CB_HEIGHT, 23}   // D
    };

    private transient final int[][] NORMAL_LAYOUT = {
            {NORMAL_HEIGHT, 0},   // W
            {NORMAL_HEIGHT, 20},  // A
            {NORMAL_HEIGHT, 20},  // S
            {NORMAL_HEIGHT, 20}   // D
    };

    public KeystrokesConfig() {
        super(new Mod(Keystrokes.NAME, ModType.HUD, "/keystrokesrevamp_dark.svg"), "keystrokes.json");
        initialize();
        updateKeys();
        addListener("cbKeyRects", this::updateKeys);
    }

    public void updateKeys() {
        int[][] layout = cbKeyRects ? CB_LAYOUT : NORMAL_LAYOUT;
        for (int i = 0; i < keystrokesElement.movementKeys.length; i++) {
            keystrokesElement.movementKeys[i].setLayout(layout[i][0], layout[i][1]);
        }
    }
}