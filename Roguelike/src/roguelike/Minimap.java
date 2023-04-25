package roguelike;

import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class Minimap {
    private Sfondo sfondo;
    public static final int spritesPerLine = 50;
    public static final int spritesPerColumn = 40;
    public static final int spriteDimension = 2;
    private final PImage compassImage;
    private final PGraphics gfx;

    public Minimap(Sfondo sfondo, PGraphics gfx, PImage compassImage) {
        compassImage.resize(50, 50);
        this.sfondo = new Sfondo(gfx);
        this.compassImage = compassImage;
        this.sfondo.setSfondo(sfondo.getTexture());
        sfondo.resize(spriteDimension);
        this.gfx = gfx;
    }

    public void draw(float widthScreen, PVector playerPos, PVector doorPos, PVector keyPos){

        var x = (int)widthScreen - spriteDimension * (1 + spritesPerLine);
        var y = 0;
        gfx.image(compassImage, x - 55, y + 15);
        var xPos = 0;
        var yPos = 0;

        var playerDrawPosition = new PVector();
        var doorDrawPosition = new PVector();
        var keyDrawPosition = new PVector();

        if (keyPos == null){
            keyPos = new PVector(-1,-1);
        }

        for (var s : sfondo.getTexture()){
            s.draw(gfx, x, y);
            x += spriteDimension;
            xPos += 1;
            if (x == widthScreen-spriteDimension){
                x = (int)widthScreen - spriteDimension * (1 + spritesPerLine);
                xPos = 0;
                y += spriteDimension;
                yPos ++;
                if (y >= spriteDimension * spritesPerColumn) break;
            }

            if (playerPos.x == xPos && playerPos.y == yPos){
                playerDrawPosition.x = x;
                playerDrawPosition.y = y;
            }
            if (doorPos.x == xPos && doorPos.y == yPos){
                doorDrawPosition.x = x;
                doorDrawPosition.y = y;
            }
            if (keyPos.x == xPos && keyPos.y == yPos){
                keyDrawPosition.x = x;
                keyDrawPosition.y = y;
            }
        }
        gfx.stroke(0);
        gfx.rect((int)widthScreen - spriteDimension * (1 + spritesPerLine), 0, spritesPerLine*spriteDimension, spritesPerColumn*spriteDimension);
        gfx.stroke(255, 255, 255);
        gfx.circle(playerDrawPosition.x + 1, playerDrawPosition.y + 1, 2);
        gfx.stroke(0, 255, 255);
        gfx.circle(doorDrawPosition.x + 1, doorDrawPosition.y + 1, 2);
        gfx.stroke(255, 255, 0);
        gfx.circle(keyDrawPosition.x + 1, keyDrawPosition.y + 1, 2);
    }

    public Sfondo getSfondo() {
        return sfondo;
    }

    public void setSfondo(Sfondo sfondo) {
        this.sfondo = new Sfondo(gfx);
        this.sfondo.setSfondo(sfondo.getTexture());
        sfondo.resize(spriteDimension);
    }
}
