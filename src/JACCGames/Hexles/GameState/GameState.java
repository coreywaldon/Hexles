package JACCGames.Hexles.GameState;

import JACCGames.Hexles.Objects.Hex;
import JACCGames.Hexles.Resources.Resources;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import java.awt.Font;
import java.util.ArrayList;


/**
 * Created by corey on 2/19/2016.
 */
public class GameState extends BasicGameState {
    public static ArrayList<Hex> hexles = new ArrayList<>();
    public static float resMult = 1;
    public static int curPlayer = 1;
    private String message = "Player "+curPlayer+"'s turn";
    @Override
    public int getID() {
        return States.GAME;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame s) throws SlickException {
        for(int i = 0;i < 5; i++){
            for(int j = 0; j < 6; j++){
                hexles.add(new Hex(60+(200*j), 60+(118*i), 0.25f, (int)(128*resMult), (int)(128*resMult)));
            }
        }
        for(int i = 0;i < 6; i++){
            for(int j = 0; j < 5; j++){
                hexles.add(new Hex(160+(200*j), (118*i), 0.25f, (int)(128*resMult), (int)(128*resMult)));
            }
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame s, Graphics g) throws SlickException {
        Image bg = new Resources().images.get("bg");
        Image hexTex = new Resources().images.get("hex");
        Image deadHexTex = new Resources().images.get("deadHex");
        Image midHexTex = new Resources().images.get("midHex");
        bg.draw(0,0,354354354f);
        hexTex.setFilter(Image.FILTER_LINEAR);
        deadHexTex.setFilter(Image.FILTER_LINEAR);
        midHexTex.setFilter(Image.FILTER_LINEAR);
        java.awt.Font font = new java.awt.Font("Verdana", Font.BOLD, (int)(16*resMult));
        TrueTypeFont ttf = new TrueTypeFont(font, true);
        ttf.drawString(gc.getWidth()*0.85f, gc.getHeight()*0.05f, message, Color.black);
        message = "Player "+curPlayer+"'s turn";
        hexles.stream().filter(hex -> hex.isActive>0.5f).forEach(hex -> hexTex.draw(hex.getX(), hex.getY(), hex.getScale()));
        hexles.stream().filter(hex -> hex.isActive==0.5f).forEach(hex -> midHexTex.draw(hex.getX(), hex.getY(), hex.getScale()));
        hexles.stream().filter(hex -> hex.isActive<0.5f).forEach(hex -> deadHexTex.draw(hex.getX(), hex.getY(), hex.getScale()));
    }
    boolean fullscreen = false, played = false;
    @Override
    public void update(GameContainer gc, StateBasedGame s, int delta) throws SlickException {
        if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
            AppGameContainer agc = (AppGameContainer) gc;
            fullscreen = !fullscreen;
            float resMult = (1080f/gc.getHeight());
            for(Hex hex:hexles){
                hex.multCoordinates(resMult);
            }
            agc.setDisplayMode(1920, 1080, fullscreen);
        }
        if(!isGameOngoing()){
            message = "Player "+((curPlayer==1)?2:1)+" wins!";
            if(!played){
                Sound endGame = Resources.sounds.get("endgame");
                endGame.play(1.5f, 0.5f);
                played=!played;
            }
        }

    }
    public boolean isGameOngoing(){
        for(Hex hex:hexles) {
            if (hex.isActive > 0) return true;
        }
        return false;
    }

    @Override
    public void mousePressed(int button, int x, int y){
        System.out.println("CLICK");
        Rectangle rect = new Rectangle(x, y, 1, 1);
        System.out.println(x+" "+y);
        hexles.stream().filter(hex -> hex.collider.contains(rect) || hex.collider.intersects(rect)).forEach(Hex::isClicked);
    }
}
