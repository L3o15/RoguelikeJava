package roguelike;

import processing.core.PGraphics;
import processing.core.PImage;

public class Food extends Item{
    protected Food(PImage sprite, boolean upgrades, int value) {
        super(sprite, upgrades, value);
    }
}
