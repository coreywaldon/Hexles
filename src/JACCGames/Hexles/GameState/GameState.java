package JACCGames.Hexles.GameState;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by corey on 2/19/2016.
 */
public class GameState extends BasicGameState {
    @Override
    public int getID() {
        return States.GAME;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame s) throws SlickException {

    }

    @Override
    public void render(GameContainer gc, StateBasedGame s, Graphics g) throws SlickException {
        g.drawString("Game state, yay", 50, 50);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame s, int delta) throws SlickException {
        if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) s.enterState(States.MENU);
    }
}
