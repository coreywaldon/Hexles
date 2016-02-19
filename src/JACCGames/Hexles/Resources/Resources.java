package JACCGames.Hexles.Resources;

/**
 * Created by corey on 2/19/2016.
 */
import java.util.*;
import org.newdawn.slick.*;
public class Resources {
    private static Map<String, Image> images;
    private static Map<String, SpriteSheet> sprites;
    private static Map<String, Sound> sounds;
    public Resources(){
        images = new HashMap<String, Image>();
        sprites = new HashMap<String, SpriteSheet>();
        sounds = new HashMap<String, Sound>();

        try {
            images.put("bg", loadImage("res/bg.png"));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    private Image loadImage(String path) throws SlickException{
        return new Image(path, false, Image.FILTER_NEAREST);
    }


}
