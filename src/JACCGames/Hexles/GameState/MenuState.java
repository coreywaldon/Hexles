package JACCGames.Hexles.GameState;

import JACCGames.Hexles.Enums.ButtonType;
import JACCGames.Hexles.Objects.Button;
import JACCGames.Hexles.Resources.Resources;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

public class MenuState extends BasicGameState implements org.newdawn.slick.state.GameState {
    public static float resMult = 1f;

    public static GameContainer pGC;
    public static StateBasedGame sBG;

    public static ArrayList<Button> buttons = new ArrayList<>();
    public static ArrayList<Boolean> buttonPressed = new ArrayList<>();
    public static ArrayList<Boolean> buttonHighlight = new ArrayList<>();


    Resources rc = new Resources();
    Image button = Resources.images.get("button");
    Image buttonHi = Resources.images.get("buttonHi");
    Image buttonPress = Resources.images.get("buttonPress");
    int i = 0;

    public static void gameStart() {
        GameState.startLoad = true;
        GameState.localGame = true;
        GameState.canPlay = true;
        sBG.enterState(States.GAME);
    }

    public static int getButtonIndex(Button btn) {
        return buttons.indexOf(btn);
    }

    public static String getButtonString(Button btn) {
        switch (btn.type) {
            case BUTTON_LOCALGAME:
                return "Local Game";
            case BUTTON_FINDMATCH:
                return "Find Match";
            case BUTTON_OPTIONSMENU:
                return "Options";
            case BUTTON_EXITMENU:
                return "Exit Game";
            case BUTTON_SOLO:
                return "Solo";
            case BUTTON_PVPLOCAL:
                return "PVP Local";
            default:
                return "";
        }
    }

    @Override
    public int getID() {
        return States.MENU;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame s) throws SlickException {
        sBG = s;
        pGC = gc;
        for (int i = 1; i < 5; i++) {
            buttons.add(new Button((int) ((float) (gc.getWidth() / 2) - (button.getWidth() * 0.125f)),
                    300 + 75 * i, (int) (button.getWidth() * 0.125f),
                    (int) (button.getHeight() * 0.125f), resMult, getButtonType()));
            buttonHighlight.add(false);
        }
    }

    public ButtonType getButtonType() {
        i++;
        switch (i) {
            case 1:
                return ButtonType.BUTTON_LOCALGAME;
            case 2:
                return ButtonType.BUTTON_FINDMATCH;
            case 3:
                return ButtonType.BUTTON_OPTIONSMENU;
            case 4:
                return ButtonType.BUTTON_EXITMENU;
            case 5:
                return ButtonType.BUTTON_SOLO;
            case 6:
                return ButtonType.BUTTON_PVPLOCAL;
            default:
                return null;
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame s, Graphics g) throws SlickException {
        Image bg = Resources.images.get("bg");
        Image logo = Resources.images.get("logo");
        logo.setFilter(Image.FILTER_LINEAR);
        bg.draw(0, 0, resMult);
        logo.draw((gc.getWidth() / 2) - (logo.getWidth() * 0.15f * resMult), gc.getHeight() * 0.05f, 0.3f * resMult);
        for (int i = 0; i < buttons.size(); i++) {
            if (buttonHighlight.get(i))
                buttonHi.draw(buttons.get(i).x, buttons.get(i).y, 0.25f * resMult);
            else
                button.draw(buttons.get(i).x, buttons.get(i).y, 0.25f * resMult);

            AngelCodeFont HexFont = Resources.HexFont;
            HexFont.drawString(buttons.get(i).x + ((button.getWidth() * resMult) / 16), buttons.get(i).y + ((button.getHeight() * resMult) / 16), getButtonString(buttons.get(i)), Color.white);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame s, int delta) throws SlickException {
        if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) s.enterState(States.GAME);
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        Rectangle rect = new Rectangle(x, y, 1, 1);
        buttons.stream().filter(btn -> btn.collider.intersects(rect)).forEach(btn -> {
            try {
                btn.isClicked();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int x, int y) {
        Rectangle rect = new Rectangle(x, y, 1, 1);
        for (Button btn : buttons)
            buttonHighlight.set(getButtonIndex(btn), btn.collider.intersects(rect) || btn.collider.contains(rect));
    }

}
