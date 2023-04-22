package roguelike;

import processing.core.PGraphics;
import processing.core.PImage;

public class Sprite {
    private final PImage image;
    private final boolean collides;

    public Sprite(PImage image, boolean collides) {
        this.image = image;
        this.collides = collides;
        image.resize(Sfondo.spriteDimension, Sfondo.spriteDimension);
    }

    public void draw(PGraphics gfx, int x, int y){
        gfx.image(image, x, y);
    }

    public boolean collides() {
        return collides;
    }
}
