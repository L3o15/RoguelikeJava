package roguelike;

import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

public class Player {

    private final PGraphics gfx;
    private final PImage image;
    private PVector pos;
    public boolean dead = false;
    private float energy = 100;
    private final PVector initialPos;
    private final ItemsContainer items;
    private Key key;

    public Player(PGraphics graphics, PImage image, PVector pos, ItemsContainer items) {
        key = null;
        this.gfx = graphics;
        this.image = image;
        this.pos = pos;
        this.items = items;
        image.resize(Camera.spriteDimension, Camera.spriteDimension);
        initialPos = new PVector(pos.x, pos.y);
    }

    public void draw(PVector drawingPos) {
        gfx.textSize(20);
        gfx.color(0,0,0);
        //gfx.image(image, 50 * Sfondo.spriteDimension - Camera.spriteDimension * 5, 40 * Sfondo.spriteDimension - Camera.spriteDimension * 4);
        gfx.image(image, drawingPos.x * Camera.spriteDimension, drawingPos.y * Camera.spriteDimension);
        items.draw();
        gfx.fill(255,255,255,255);
        var energyString = String.format("%.1f", energy);
        gfx.text(energyString + "%", 40,15);
        gfx.strokeWeight(12);
        gfx.stroke(255,255,0);
        gfx.line(16,25,16 + energy,25);
        gfx.fill(0,0,0,0);
        gfx.stroke(0,0,0);
        gfx.strokeWeight(5);
        gfx.rect(10,20, 110, 13 );
        gfx.fill(0,0,0,0);
        gfx.square(49 * Sfondo.spriteDimension, 40 * Sfondo.spriteDimension, Sfondo.spriteDimension);
        if (key != null){
            key.getSprite().resize(Sfondo.spriteDimension, Sfondo.spriteDimension);
            gfx.image(key.getSprite(), 49 * Sfondo.spriteDimension, 40 * Sfondo.spriteDimension);
        }
    }

    public void move(int x, int y){
        pos.x += x;
        pos.y += y;
    }

    public PVector getPos() {
        return pos;
    }

    public void useItem(int index){
        energy = energy * ( 1 + (items.getItemValue(index) / 100.0f));
        if (energy > 100){
            energy = 100;
        }
    }

    public void addkey(Key key){
        this.key = key;
    }

    public PImage getImage() {
        return image;
    }

    public float getEnergy() {
        return energy;
    }

    public ItemsContainer getItems() {
        return items;
    }

    public Key getKey() {
        return key;
    }

    public void hit(){
        energy -= Roguelike.livello  + 1;
        if (energy <= 0){
            Roguelike.livello = 0;
            key = null;
            energy = 100;
            dead = true;
        }
    }

    public void resetPos(){
        pos = initialPos;
    }

    public void setPos(PVector pos) {
        this.pos = pos;
    }
}
