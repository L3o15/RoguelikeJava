package roguelike;

import processing.core.PGraphics;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Sfondo {
    private List<Sprite> texture;
    private final PGraphics gfx;
    public static final int spritesPerLine = 50;
    public static final int spritesPerColumn = 40;
    public static final int spriteDimension = 16;

    public Sfondo( PGraphics gfx) {
        texture = new ArrayList<>();
        this.gfx = gfx;
    }

    public void setSfondo(ArrayList<Sprite> sprites){
        sprites.forEach(x -> x.getImage().resize(spriteDimension,spriteDimension));
        texture = sprites;
    }

    public void draw() {
        //var x = (int)widthScreen - spriteDimension * (1 + spritesPerLine);
        var x = 0;
        var y = 0;
        for (var s : texture){
            s.draw(gfx, x, y);
            x += spriteDimension;
            if (x%(spritesPerLine*spriteDimension) == 0){
                //x = (int)widthScreen - spriteDimension * (1 + spritesPerLine);
                x = 0;
                y += spriteDimension;
            }
        }
    }

    public boolean checkCollisioinFollowing(PVector pos){
        int position = (int) (pos.x + (pos.y * spritesPerLine));
        if (texture.get(position) != null) return texture.get(position).collides();
        return true;
    }

    public ArrayList<Sprite> getTexture() {
        return (ArrayList<Sprite>) texture;
    }

    public void setTexture(List<Sprite> texture) {
        this.texture = texture;
    }

    public void resize(int dimension){
        texture.forEach(x -> x.getImage().resize(dimension,dimension));
    }
}
