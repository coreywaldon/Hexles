package JACCGames.Hexles.Objects;

import JACCGames.Hexles.Enums.ButtonType;
import JACCGames.Hexles.GameState.MenuState;
import org.newdawn.slick.geom.Rectangle;

public class Button {
    public static float scale = 1;
    public int x, y, height, width;
    public ButtonType type;
    public Rectangle collider;

    public Button(int x, int y, int width, int height, float scale, ButtonType type) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        Button.scale = scale;
        this.type = type;
        collider = new Rectangle(x, y, width * scale * 2, height * scale * 2);
    }

    public void multCoords(float resMult) {
        x *= resMult;
        y *= resMult;
        width *= resMult;
        height *= resMult;
        collider.setBounds(x, y, width * 2, height * 2);
    }

    public void isClicked() throws InterruptedException {
        switch (type) {
            case BUTTON_LOCALGAME: {
                MenuState.gameStart();
                break;
            }
            case BUTTON_FINDMATCH: {
                break;
            }
            case BUTTON_OPTIONSMENU: {
                break;
            }
            case BUTTON_EXITMENU: {
                MenuState.pGC.exit();
                break;
            }
        }

    }


}
