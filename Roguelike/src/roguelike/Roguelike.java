package roguelike;

import processing.core.PApplet;
import processing.core.PVector;
import processing.event.KeyEvent;

import java.util.ArrayList;

public class Roguelike extends PApplet {
    private Player player;
    private Sfondo sfondo;

    public static void main(String[] args) {
        PApplet.main("roguelike.Roguelike");
    }

    @Override
    public void settings() {
        width = 25 * 32;
        height = 20 * 32;
    }

    @Override
    public void setup() {
        sfondo = new Sfondo(getGraphics());
        var sprites = new ArrayList<Sprite>();
        for (int i = 0; i < 25*10; i++) {
            sprites.add(new Sprite(loadImage("Tiles/tile_0005.png"), false));
        }
        for (int i = 0; i < 25*10; i++) {
            sprites.add(new Sprite(loadImage("Tiles/tile_0000.png"), true));
        }
        sfondo.setSfondo(sprites);
        player = new Player(getGraphics(), loadImage("Tiles/tile_0098.png"), new PVector(0,0));
    }

    @Override
    public void draw() {
        sfondo.draw();
        player.draw();

    }

    @Override
    public void keyPressed(KeyEvent event) {
        var pos = player.getPos();

        var movement = new PVector(0,0);
        switch (event.getKeyCode()) {
            case 65:
                movement.x = -1.0f;
                movement.y = 0.0f;
                break;
            case 68:
                movement.x = 1.0f;
                movement.y = 0.0f;
                break;
            case 87:
                movement.x = 0.0f;
                movement.y = -1.0f;
                break;
            case 83:
                movement.x = 0;
                movement.y = 1.0f;
                break;
        }
        PVector newPos = new PVector(pos.x + movement.x, pos.y + movement.y);
        //System.out.println(sfondo.checkCollisioinFollowing(newPos));
        if (sfondo.checkCollisioinFollowing(newPos)) return;
        player.move((int) movement.x, (int) movement.y);
    }
}
