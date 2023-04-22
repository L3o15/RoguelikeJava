package roguelike;

import processing.core.PGraphics;

import java.util.ArrayList;
import java.util.List;

public class ItemsContainer {
    private final List<Item> items;
    protected final PGraphics gfx;

    public ItemsContainer(PGraphics gfx) {
        this.gfx = gfx;
        items = new ArrayList<>();
    }

    public void removeItem(int index){
        if (index >= items.size()) return;
        items.remove(index);
    }

    public void addItem(Item item){
        items.add(item);
    }

    public int getItemValue(int index){
        if (index >= items.size()) return 0;
        var item = items.get(index);
        items.remove(item);
        if (!item.isUpgrades()) return item.getValue() * (-1);
        return item.getValue();
    }

    public void draw(){
        gfx.fill(0,0,255,255);
        gfx.strokeWeight(0);
        gfx.rect(0, 40 * Sfondo.spriteDimension, 50*Sfondo.spriteDimension, 41 * Sfondo.spriteDimension);
        gfx.stroke(0,0,0);
        gfx.strokeWeight(5);

        for (int i = 0; i < items.size(); i++) {
            if (i + 1 == 10) break;
            gfx.fill(0,0,0,0);
            gfx.square((i) * Sfondo.spriteDimension, 40 * Sfondo.spriteDimension, Sfondo.spriteDimension);
            gfx.fill(255,255,255,255);
            gfx.text(i+1,(i) * Sfondo.spriteDimension, 40 * Sfondo.spriteDimension );
            gfx.image(items.get(i).getSprite(), (i) * Sfondo.spriteDimension, 40 * Sfondo.spriteDimension);
        }
    }
}
