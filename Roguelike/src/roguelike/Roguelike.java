package roguelike;

import processing.core.PApplet;
import processing.core.PVector;
import processing.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Roguelike extends PApplet {
    private Player player;
    //private Camera camera;
    private Minimap minimap;
    private List<Sfondo> livelli;
    private Door nextLevelDoor;
    private Key nextLevelKey;
    public static int livello = 0;
    public static int levelMax = 5;
    public long movementDetector = System.currentTimeMillis();
    public long hitDetector = System.currentTimeMillis();
    private List<Enemy> enemies;
    public static void main(String[] args) {
        PApplet.main("roguelike.Roguelike");
    }

    @Override
    public void settings() {
        width = Sfondo.spritesPerLine * Sfondo.spriteDimension;
        height = (Sfondo.spritesPerColumn + 1) * Sfondo.spriteDimension;
        //width = 50 * 16;
        //height = 40 * 16;
    }

    @Override
    public void setup() {
        livelli = new ArrayList<>();
        for (int i = 0; i < levelMax + 1; i++) {
            var sprites = new ArrayList<Sprite>();
            int index = Sfondo.spritesPerLine*Sfondo.spritesPerColumn - 1;
            for (int j = 0; j < Sfondo.spritesPerColumn*Sfondo.spritesPerLine; j++) {
                sprites.add(new Sprite(loadImage("Tiles/tile_0040.png"), true));
            }
            sprites.get(index).setCollides(false);
            sprites.get(index).setImage(loadImage("Tiles/tile_0000.png"));

            var changedStateNumber = 1;
            while (changedStateNumber < 1000){
                int rand = (int) (Math.random() * 4);
                if (rand == 0){
                    index ++;
                    if (index < Sfondo.spritesPerLine*Sfondo.spritesPerColumn && index % Sfondo.spritesPerLine != 0){
                        if (sprites.get(index).isCollides()){
                            sprites.get(index).setCollides(false);
                            sprites.get(index).setImage(loadImage("Tiles/tile_0000.png"));
                            changedStateNumber ++;
                        }
                    }else {
                        index --;
                    }
                }else if (rand == 1){
                    index --;
                    if (index >= 0 && ((index % Sfondo.spritesPerLine) != (Sfondo.spritesPerLine -1))){
                        if (sprites.get(index).isCollides()){
                            sprites.get(index).setCollides(false);
                            sprites.get(index).setImage(loadImage("Tiles/tile_0000.png"));
                            changedStateNumber ++;
                        }
                    }else {
                        index ++;
                    }

                }else if (rand == 2){
                    index += Sfondo.spritesPerLine;
                    if (index < Sfondo.spritesPerLine*Sfondo.spritesPerColumn) {
                        if (sprites.get(index).isCollides()) {
                            sprites.get(index).setCollides(false);
                            sprites.get(index).setImage(loadImage("Tiles/tile_0000.png"));
                            changedStateNumber++;
                        }
                    }else {
                        index -= Sfondo.spritesPerLine;
                    }

                }else if (rand == 3){
                    index -= Sfondo.spritesPerLine;
                    if (index >= 0){
                        if (sprites.get(index).isCollides()){
                            sprites.get(index).setCollides(false);
                            sprites.get(index).setImage(loadImage("Tiles/tile_0000.png"));
                            changedStateNumber ++;
                        }
                    } else {
                        index += Sfondo.spritesPerLine;
                    }
                }
            }
            livelli.add(new Sfondo(getGraphics()));
            livelli.get(i).setSfondo(sprites);
        }

        //camera = new Camera(new PVector(Sfondo.spritesPerLine - 17,Sfondo.spritesPerColumn - 11),new PVector(Sfondo.spritesPerLine - 1,Sfondo.spritesPerColumn - 1), livelli.get(0).getTexture(), getGraphics());
        minimap = new Minimap(livelli.get(livello), getGraphics());

        var items = new ItemsContainer(getGraphics());
        for (int i = 0; i < 5; i++) {
            items.addItem(new Food(loadImage("Items/apple.png"),true, 10));
            items.addItem(new Poison(loadImage("Items/poison.png"), false, 20));
        }
        player = new Player(getGraphics(), loadImage("Tiles/tile_0098.png"), new PVector(Sfondo.spritesPerLine - 1,Sfondo.spritesPerColumn - 1), items);

        generateNewDoorPos();
        generateNewKeyPos();
        enemies = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            enemies.add(new Enemy(getGraphics(), loadImage("Tiles/tile_0121.png"), new PVector((int) (Math.random() * Sfondo.spritesPerLine), (int) (Math.random() * Sfondo.spritesPerColumn))));
        }
    }

    @Override
    public void draw() {
        livelli.get(livello).resize(Sfondo.spriteDimension);
        livelli.get(livello).draw();

        //livelli.get(livello).resize(50);
        //camera.draw(width);
        if (nextLevelKey != null){
            nextLevelKey.draw();
            if (player.getPos().equals(nextLevelKey.getPos())) {
                player.addkey(nextLevelKey);
                nextLevelKey = null;
            }
        }
        nextLevelDoor.draw();

        if (player.getPos().equals(nextLevelDoor.getPos()) && player.getKey() != null && livello + 1 <= levelMax){
            livello++;
            player.addkey(null);
            generateNewKeyPos();
            generateNewDoorPos();
            player.setPos(new PVector(Sfondo.spritesPerLine - 1,Sfondo.spritesPerColumn - 1));
            minimap.setSfondo(livelli.get(livello));
        }

        for (int i = 0; i < enemies.size(); i++){
            var e = enemies.get(i);
            e.draw();
            if (System.currentTimeMillis() - hitDetector > 1000 && e.getPos().equals(player.getPos())){
                hitDetector = System.currentTimeMillis();
                System.out.println("Hit");
                player.hit();
                if (player.dead){
                    livello = 0;
                    generateNewKeyPos();
                }
            }
            if (System.currentTimeMillis() - movementDetector > 1000){
                if (Math.random() > 0.5){
                    float objectSpeed = 2;
                    var pos = e.getPos();
                    pos.x += Math.cos(e.getObjectAngle()) * objectSpeed;
                    pos.y += Math.sin(e.getObjectAngle()) * objectSpeed;

                    if (pos.x < 0){
                        pos.x = 1;
                    } else if (pos.x >= width - 1){
                        pos.x = width - 1;
                    }

                    if (pos.y < 0){
                        pos.y = 1;
                    } else if (pos.y >= height - 1){
                        pos.y = height - 1;
                    }

                    if (Math.random() <= 0.5) {
                        e.setObjectAngle((float) (Math.random() * 180 - 90) + e.getObjectAngle());
                    }
                    e.move((int)pos.x, (int)pos.y);
                }
                if (i == enemies.size() - 1) movementDetector = System.currentTimeMillis();
            }

        }
        minimap.getSfondo().resize(Minimap.spriteDimension);
        if (nextLevelKey == null){
            minimap.draw(width, player.getPos(), nextLevelDoor.getPos(), null);
        } else {
            minimap.draw(width, player.getPos(), nextLevelDoor.getPos(), nextLevelKey.getPos());
        }

        player.draw();

    }

    private void generateNewKeyPos() {
        var keyPos = new PVector((int) (Math.random() * Sfondo.spritesPerLine), (int) (Math.random() * Sfondo.spritesPerColumn));
        while (livelli.get(livello).checkCollisioinFollowing(keyPos)){
            keyPos.x = (int) (Math.random() * Sfondo.spritesPerLine);
            keyPos.y = (int) (Math.random() * Sfondo.spritesPerColumn);
        }
        nextLevelKey = new Key(loadImage("Items/key.png"), keyPos, getGraphics());
    }

    private void generateNewDoorPos() {
        var doorPos = new PVector((int) (Math.random() * Sfondo.spritesPerLine), (int) (Math.random() * Sfondo.spritesPerColumn));
        while (livelli.get(livello).checkCollisioinFollowing(doorPos)){
            doorPos.x = (int) (Math.random() * Sfondo.spritesPerLine);
            doorPos.y = (int) (Math.random() * Sfondo.spritesPerColumn);
        }
        nextLevelDoor = new Door(getGraphics(), loadImage("Tiles/tile_0045.png"), doorPos);
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
            case 49, 50, 51, 52, 53, 54, 55, 56, 57:
                player.useItem(event.getKeyCode() - 49);
                break;
        }
        PVector newPos = new PVector(pos.x + movement.x, pos.y + movement.y);
        if (newPos.x < 0 || newPos.x > Sfondo.spritesPerLine - 1 || newPos.y < 0 || newPos.y > Sfondo.spritesPerColumn - 1) return;
        if (livelli.get(livello).checkCollisioinFollowing(newPos)) return;
        player.move((int) movement.x, (int) movement.y);
    }
}
