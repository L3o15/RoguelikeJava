package roguelike;

import processing.core.PGraphics;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Camera {
    private PVector primoPunto;
    private PVector secondoPunto;
    private List<Sprite> texture;
    private final PGraphics gfx;
    public static final int spriteDimension = 50;
    public static int cameraDimensionLine = Sfondo.spritesPerLine * Sfondo.spriteDimension / spriteDimension;
    public static int cameraDimensionColumn = Sfondo.spritesPerColumn * Sfondo.spriteDimension / spriteDimension;

    public Camera(PVector primoPunto, PVector secondoPunto, List<Sprite> texture, PGraphics gfx) {
        this.primoPunto = primoPunto;
        this.secondoPunto = secondoPunto;
        for (var t : texture){
            t.getImage().resize(spriteDimension, spriteDimension);
        }
        this.texture = texture;

        this.gfx = gfx;
    }

    public PVector getPrimoPunto() {
        return primoPunto;
    }

    public void setPrimoPunto(PVector primoPunto) {
        this.primoPunto = primoPunto;
    }

    public PVector getSecondoPunto() {
        return secondoPunto;
    }

    public void setSecondoPunto(PVector secondoPunto) {
        this.secondoPunto = secondoPunto;
    }

    public void move(int x, int y){
        primoPunto.x += x;
        secondoPunto.x += x;
        primoPunto.y += y;
        secondoPunto.y += y;
    }

    public void draw(float width){
        var x = 0;
        var y = 0;
        var yCam = 0;
        var xCam = 0;
        for (var s : texture){

            x += 1;
            if (x >= Sfondo.spritesPerLine){
                y++;
                x = 0;
            }
            if (x > primoPunto.x && x < secondoPunto.x && y > primoPunto.y && y < secondoPunto.y){
                s.draw(gfx, xCam, yCam);
                xCam += spriteDimension;
                if (xCam >= width){
                    xCam = 0;
                    yCam += spriteDimension;
                }
            }
        }
    }
}
