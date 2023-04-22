package roguelike;

import processing.core.PGraphics;
import processing.core.PImage;

abstract public class Item {
    protected final PImage sprite;
    protected final boolean upgrades;
    protected final int value;

    protected Item(PImage sprite, boolean upgrades, int value) {
        sprite.resize(Sfondo.spriteDimension, Sfondo.spriteDimension);
        this.sprite = sprite;
        this.upgrades = upgrades;
        this.value = value;
    }


    public boolean isUpgrades() {
        return upgrades;
    }

    public int getValue() {
        return value;
    }

    public PImage getSprite() {
        return sprite;
    }
}
