package JACCGames.Hexles;

import JACCGames.Hexles.GameState.GameState;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.io.File;

/**
 * Created by corey on 2/18/2016.
 */
public class Main extends StateBasedGame {

    public static boolean _APPLET = true;

    public Main(){super("Hexles");}
    public static void main(String[] args){
        _APPLET=false;

        File f = new File("natives");
        if(f.exists()) System.setProperty("org.lwjgl.librarypath", f.getAbsolutePath());
        //Create AppGameContainer
        try{
            AppGameContainer game = new AppGameContainer(new Main());
            game.setDisplayMode(Window.WIDTH, Window.HEIGHT, false);
            game.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        gc.setTargetFrameRate(60);
        gc.setAlwaysRender(true);
        gc.setMaximumLogicUpdateInterval(60);
        gc.setVSync(true);
        gc.setShowFPS(true);

        this.addState(new GameState());
    }


}
