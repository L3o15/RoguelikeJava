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
    public static final int spriteDimension = 80;
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
        if (primoPunto.x < 0){
            primoPunto.x = 0;
            secondoPunto.x -= x;
        }
        if (primoPunto.y < 0){
            primoPunto.y = 0 ;
            secondoPunto.y -= y;
        }
        if (primoPunto.x >= Sfondo.spritesPerLine - 10){
            primoPunto.x = Sfondo.spritesPerLine - 11 ;
            secondoPunto.x -= x;
        }
        if (primoPunto.y >= Sfondo.spritesPerColumn - 8){
            primoPunto.y = Sfondo.spritesPerColumn - 9 ;
            secondoPunto.y -= y;
        }
    }

    public boolean isInside(int x, int y){
        return x > primoPunto.x && x <= secondoPunto.x && y >= primoPunto.y && y <= secondoPunto.y;
    }

    public PVector getDrawingPosition(int x, int y){
        //if (!isInside(x,y)) return null;
        return new PVector(x - primoPunto.x, y - primoPunto.y);
    }

    public void draw(float width){
        var x = 0;
        var y = 0;
        var yCam = 0;
        var xCam = 0;
        for (var s : texture){
            s.getImage().resize(spriteDimension, spriteDimension);
            x += 1;
            if (x >= Sfondo.spritesPerLine){
                y++;
                x = 0;
            }
            if (x > primoPunto.x && x <= secondoPunto.x && y >= primoPunto.y && y <= secondoPunto.y){
                s.draw(gfx, xCam, yCam);
                //gfx.square(xCam, yCam, spriteDimension);
                xCam += spriteDimension;
                if (xCam >= width){
                    xCam = 0;
                    yCam += spriteDimension;
                }
            }
        }
    }

    public List<Sprite> getTexture() {
        return texture;
    }

    public void setTexture(List<Sprite> texture) {
        for (var t : texture){
            t.getImage().resize(spriteDimension, spriteDimension);
        }
        this.texture = texture;
    }

    public void reset(PVector posPlayer){
        if (posPlayer.x - 4 >= 0 && posPlayer.y - 4 >= 0 && posPlayer.x + 6 < Sfondo.spritesPerLine - 1 && posPlayer.y + 4 < Sfondo.spritesPerColumn - 1){
            primoPunto = new PVector(posPlayer.x - 4, posPlayer.y - 4);
            secondoPunto = new PVector(posPlayer.x + 6, posPlayer.y + 4);
        }
    }
}
