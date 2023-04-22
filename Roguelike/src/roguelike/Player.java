package roguelike;

import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class Player {

    private final PGraphics gfx;
    private final PImage image;
    private PVector pos;

    public Player(PGraphics graphics, PImage image, PVector pos) {
        this.gfx = graphics;
        this.image = image;
        this.pos = pos;
        image.resize(Sfondo.spriteDimension, Sfondo.spriteDimension);
    }

    public void draw() {
        gfx.image(image, pos.x * Sfondo.spriteDimension, pos.y * Sfondo.spriteDimension);
    }

    public void move(int x, int y){
        pos.x += x;
        pos.y += y;
    }

    public PVector getPos() {
        return pos;
    }
}
