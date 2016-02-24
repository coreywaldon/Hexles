package JACCGames.Hexles.GameState;

import JACCGames.Hexles.Resources.Resources;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by corey on 2/19/2016.
 */
public class MenuState extends BasicGameState {
    public float resMult = 1f;

    @Override
    public int getID() {
        return States.MENU;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame s) throws SlickException {

    }

    @Override
    public void render(GameContainer gc, StateBasedGame s, Graphics g) throws SlickException {
        Resources rc = new Resources();
        Image bg = Resources.images.get("bg");
        Image logo = Resources.images.get("logo");
        logo.setFilter(Image.FILTER_LINEAR);
        bg.draw(0, 0, resMult);
        logo.draw((gc.getWidth() / 2) - (logo.getWidth() * 0.15f), gc.getHeight() * 0.05f, 0.3f);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame s, int delta) throws SlickException {
        if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) s.enterState(States.GAME);
    }
}
