package roguelike;

import processing.core.PGraphics;
import processing.core.PVector;

public class MapItem {
    private final Item item;
    private final PVector pos;
    private final PGraphics gfx;

    public MapItem(Item item, PVector pos, PGraphics gfx) {
        this.gfx = gfx;
        item.getSprite().resize(Camera.spriteDimension, Camera.spriteDimension);
        this.item = item;
        this.pos = pos;
    }

    public Item getItem() {
        return item;
    }

    public PVector getPos() {
        return pos;
    }

    public void draw(PVector drawingPosition){
        gfx.image(item.getSprite(), drawingPosition.x * Camera.spriteDimension,drawingPosition.y * Camera.spriteDimension);
    }
}
