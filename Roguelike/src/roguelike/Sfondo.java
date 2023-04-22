package roguelike;

import processing.core.PGraphics;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Sfondo {
    private List<Sprite> texture;
    private final PGraphics gfx;
    private static final int spritesPerLine = 25;
    private static final int spritesPerColumn = 20;
    public static final int spriteDimension = 32;

    public Sfondo( PGraphics gfx) {
        texture = new ArrayList<>();
        this.gfx = gfx;
    }

    public void setSfondo(ArrayList<Sprite> sprites){
        texture = sprites;
    }

    public void draw() {
        var x = 0;
        var y = 0;
        for (var s : texture){
            s.draw(gfx, x, y);
            x += spriteDimension;
            if (x%(spritesPerLine*spriteDimension) == 0){
                x = 0;
                y += spriteDimension;
            }
        }
    }

    public boolean checkCollisioinFollowing(PVector pos){
        int position = (int) (pos.x + pos.y * spritesPerLine);
        return texture.get(position).collides();
    }
}
