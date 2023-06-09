package roguelike;

import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class Key {
    private final PImage sprite;
    private final PVector pos;
    private final PGraphics gfx;


    public Key(PImage sprite, PVector pos, PGraphics gfx) {
        sprite.resize(Camera.spriteDimension, Camera.spriteDimension);
        this.sprite = sprite;
        this.pos = pos;
        this.gfx = gfx;
    }

    public PImage getSprite() {
        return sprite;
    }

    public PVector getPos() {
        return pos;
    }

    public PGraphics getGfx() {
        return gfx;
    }

    public void draw(PVector drawingPosition){
        gfx.image(sprite, drawingPosition.x * Camera.spriteDimension,drawingPosition.y * Camera.spriteDimension);
    }
}
