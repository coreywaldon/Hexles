package JACCGames.Hexles;

import JACCGames.Hexles.GameState.GameState;
import JACCGames.Hexles.GameState.MenuState;
import org.newdawn.slick.*;
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
        gc.setVSync(false);
        gc.setShowFPS(false);

        this.addState(new GameState());
        this.addState(new MenuState());
    }


}
