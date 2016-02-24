package JACCGames.Hexles.Resources;

/**
 * Created by corey on 2/19/2016.
 */

import org.newdawn.slick.*;

import java.util.HashMap;
import java.util.Map;

public class Resources implements Runnable {
    public static Map<String, Image> images;
    public static Map<String, SpriteSheet> sprites;
    public static Map<String, Sound> sounds;
    public static AngelCodeFont HexFont;

    static {
        try {
            HexFont = new AngelCodeFont("res/HexFont.fnt", "res/HexFont_0.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public Resources(){
        Thread thread = new Thread(this);
        thread.run();
    }

    private Image loadImage(String path) throws SlickException{
        return new Image(path, false, Image.FILTER_NEAREST);
    }

    private Sound loadSound(String path) throws SlickException{
        return new Sound(path);
    }

    @Override
    public void run() {
        images = new HashMap<String, Image>();
        sprites = new HashMap<String, SpriteSheet>();
        sounds = new HashMap<String, Sound>();
        try {
            images.put("hex", loadImage("res/Hex.png"));
            images.put("deadHex", loadImage("res/DeadHex.png"));
            images.put("midHex", loadImage("res/MidHex.png"));
            images.put("bg", loadImage("res/bg.png"));
            images.put("logo", loadImage("res/Logo.png"));
            sounds.put("click", loadSound("res/Click.ogg"));
            sounds.put("endgame", loadSound("res/Victory.ogg"));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }


}
