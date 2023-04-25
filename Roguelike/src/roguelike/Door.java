package roguelike;

import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class Door {
    private final PGraphics gfx;
    private final PImage image;
    private final PVector pos;

    public Door(PGraphics gfx, PImage image, PVector pos) {
        image.resize(Camera.spriteDimension, Camera.spriteDimension);
        this.gfx = gfx;
        this.image = image;
        this.pos = pos;
    }


    public PImage getImage() {
        return image;
    }

    public PVector getPos() {
        return pos;
    }


    public void draw(PVector drawingPosition){
        gfx.image(image, drawingPosition.x * Camera.spriteDimension,drawingPosition.y * Camera.spriteDimension);
    }
}
