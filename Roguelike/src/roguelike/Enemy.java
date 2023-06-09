package roguelike;

import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class Enemy {
    private final PGraphics gfx;
    private final PImage image;
    private PVector pos;
    public float objectAngle = (float) (Math.random() * 360);

    public Enemy(PGraphics gfx, PImage image, PVector pos) {
        image.resize(Camera.spriteDimension, Camera.spriteDimension);
        this.gfx = gfx;
        this.image = image;
        this.pos = pos;
    }

    public PGraphics getGfx() {
        return gfx;
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

    public void move(int x, int y){
        pos.x = x;
        pos.y = y;
    }

    public float getObjectAngle() {
        return objectAngle;
    }

    public void setObjectAngle(float objectAngle) {
        this.objectAngle = objectAngle;
    }
}
