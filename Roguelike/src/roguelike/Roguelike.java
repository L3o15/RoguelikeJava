package roguelike;

import processing.core.PApplet;
import processing.core.PVector;
import processing.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Roguelike extends PApplet {
    private Player player;
    private List<MapItem> mapItems;
    private Camera camera;
    private Minimap minimap;
    private List<Sfondo> livelli;
    private Door nextLevelDoor;
    private Key nextLevelKey;
    public static int livello = 0;
    public static int levelMax = 12;
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

    }

    @Override
    public void setup() {
        var items = new ItemsContainer(getGraphics());
        for (int i = 0; i < 1; i++) {
            items.addItem(new Food(loadImage("Items/apple.png"),true, 10));
            items.addItem(new Poison(loadImage("Items/poison.png"), false, 20));
        }
        player = new Player(getGraphics(), loadImage("Tiles/tile_0098.png"), new PVector(10,10), items);
        livelli = new ArrayList<>();
        for (int i = 0; i < levelMax + 1; i++) {
            var sprites = new ArrayList<Sprite>();
            int index = (int) (player.getPos().x + player.getPos().y * Sfondo.spritesPerLine);
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

        camera = new Camera(new PVector(player.getPos().x - 4, player.getPos().y - 4),new PVector(player.getPos().x + 6, player.getPos().y + 4), livelli.get(0).getTexture(), getGraphics());
        minimap = new Minimap(livelli.get(livello), getGraphics(), loadImage("Items/compass.png"));

        generateNewDoorPos();
        generateNewKeyPos();
        enemies = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            enemies.add(new Enemy(getGraphics(), loadImage("Tiles/tile_0121.png"), new PVector((int) (Math.random() * Sfondo.spritesPerLine), (int) (Math.random() * Sfondo.spritesPerColumn))));
        }
        generateNewMapItems();
    }

    @Override
    public void draw() {
        livelli.get(livello).resize(80);
        camera.draw(width);

        if (nextLevelKey != null){
            nextLevelKey.draw(camera.getDrawingPosition((int) nextLevelKey.getPos().x, (int) nextLevelKey.getPos().y));
            if (player.getPos().equals(nextLevelKey.getPos())) {
                player.addkey(nextLevelKey);
                nextLevelKey = null;
            }
        }
        nextLevelDoor.draw(camera.getDrawingPosition((int) nextLevelDoor.getPos().x, (int) nextLevelDoor.getPos().y));

        if (player.getPos().equals(nextLevelDoor.getPos()) && player.getKey() != null && livello + 1 <= levelMax){
            livello++;
            player.addkey(null);
            generateNewKeyPos();
            generateNewDoorPos();
            player.setPos(new PVector(10,10));
            minimap.setSfondo(livelli.get(livello));
            camera.setTexture(livelli.get(livello).getTexture());
            camera.setPrimoPunto(new PVector(player.getPos().x - 4, player.getPos().y - 4));
            camera.setSecondoPunto(new PVector(player.getPos().x + 6, player.getPos().y + 4));
            generateNewMapItems();
        }

        for (int i = 0; i < mapItems.size(); i++) {
            var it = mapItems.get(i);
            it.draw(camera.getDrawingPosition((int) it.getPos().x, (int) it.getPos().y));
            if (it.getPos().equals(player.getPos())){
                player.getItems().addItem(it.getItem());
                mapItems.remove(it);
            }
        }

        for (int i = 0; i < enemies.size(); i++){
            var e = enemies.get(i);
            e.draw(camera.getDrawingPosition((int) e.getPos().x, (int) e.getPos().y));
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

        player.draw(camera.getDrawingPosition((int) player.getPos().x, (int) player.getPos().y));
    }

    private void generateNewKeyPos() {
        var keyPos = new PVector((int) (Math.random() * (Sfondo.spritesPerLine - 2)), (int) (Math.random() * (Sfondo.spritesPerColumn - 2)));
        while (livelli.get(livello).checkCollisioinFollowing(keyPos)){
            keyPos.x = (int) (Math.random() * (Sfondo.spritesPerLine - 2));
            keyPos.y = (int) (Math.random() * (Sfondo.spritesPerColumn - 2));
        }
        nextLevelKey = new Key(loadImage("Items/key.png"), keyPos, getGraphics());
    }

    private void generateNewDoorPos() {
        var doorPos = new PVector((int) (Math.random() * (Sfondo.spritesPerLine - 2)), (int) (Math.random() * (Sfondo.spritesPerColumn - 2)));
        while (livelli.get(livello).checkCollisioinFollowing(doorPos)){
            doorPos.x = (int) (Math.random() * (Sfondo.spritesPerLine - 2));
            doorPos.y = (int) (Math.random() * (Sfondo.spritesPerColumn - 2));
        }
        nextLevelDoor = new Door(getGraphics(), loadImage("Tiles/tile_0045.png"), doorPos);
    }

    private void generateNewMapItems() {
        mapItems = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            var itemPos = new PVector((int) (Math.random() * (Sfondo.spritesPerLine - 2)), (int) (Math.random() * (Sfondo.spritesPerColumn - 2)));
            while (livelli.get(livello).checkCollisioinFollowing(itemPos)){
                itemPos.x = (int) (Math.random() * (Sfondo.spritesPerLine - 2));
                itemPos.y = (int) (Math.random() * (Sfondo.spritesPerColumn - 2));
            }
            if (i%2 == 0){
                mapItems.add(new MapItem(new Poison(loadImage("Items/poison.png"), false, 20), itemPos, getGraphics()));
            }else{
                mapItems.add(new MapItem(new Food(loadImage("Items/apple.png"), true, 10), itemPos, getGraphics()));
            }


        }
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
        if (newPos.x < 0 || newPos.x >= Sfondo.spritesPerLine - 1 || newPos.y < 0 || newPos.y >= Sfondo.spritesPerColumn - 1) return;
        if (livelli.get(livello).checkCollisioinFollowing(newPos)) return;
        player.move((int) movement.x, (int) movement.y);
        camera.move((int) movement.x, (int) movement.y);
        camera.reset(player.getPos());
    }
}
