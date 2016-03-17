package JACCGames.Hexles.GameState;

import JACCGames.Hexles.Networking.ClientHandler;
import JACCGames.Hexles.Networking.PacketMessage;
import JACCGames.Hexles.Networking.ServerHandler;
import JACCGames.Hexles.Objects.Button;
import JACCGames.Hexles.Objects.Hex;
import JACCGames.Hexles.Resources.Resources;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.util.ArrayList;


public class GameState extends BasicGameState implements Runnable {
    public static ArrayList<Hex> hexles = new ArrayList<>();
    public static float resMult = 1;
    public static int curPlayer = 1;
    public static boolean localGame = false, isServer = false, startLoad = false;
    public static boolean fullscreen = false, played = false, isOngoing = true, canPlay, started = false;
    public static Server server;
    public static Client client;
    public int tcpPort = 54555, udpPort = 54777;
    String IPAddress = "localhost";
    Music theme;
    boolean Escape = false;
    private String message = "Player "+curPlayer+"'s turn";

    public GameState() {
        localGame = false;
    }

    public static boolean isGameOngoing() {
        for (Hex hex : hexles) if (hex.isActive > 0) return true;
        return false;
    }

    public static void syncHex(float[] p) {
        for (int i = 0; i < hexles.size(); i++) {
            hexles.get(i).isActive = p[i];
        }
    }

    @Override
    public int getID() {
        return States.GAME;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame s) throws SlickException {
        if (startLoad) {
            started = true;
            if (!localGame) {
                if (isServer) {
                    canPlay = true;
                    server = new Server();
                    server.getKryo().register(PacketMessage.class);
                    System.out.println("PacketMessage registered");
                    server.getKryo().register(java.util.ArrayList.class);
                    server.getKryo().register(Hex.class);
                    server.getKryo().register(java.lang.Class.class);
                    server.getKryo().register(GameState.class);
                    server.getKryo().register(Circle.class);
                    server.getKryo().register(float[].class);

                    server.start();
                    try {
                        server.bind(tcpPort, udpPort);
                        System.out.println("Server bound successfully");
                    } catch (IOException e) {
                        e.printStackTrace();
                        gc.exit();
                    }
                    server.addListener(new ServerHandler(server, tcpPort, udpPort));
                } else {
                    canPlay = false;
                    client = new Client();
                    System.out.println("PacketMessage registered");
                    client.start();
                    try {
                        client.connect(3000, "localhost", tcpPort, udpPort);
                    } catch (IOException e) {
                        e.printStackTrace();
                        gc.exit();
                    }
                    client.getKryo().register(java.util.ArrayList.class);
                    client.getKryo().register(PacketMessage.class);
                    client.getKryo().register(Hex.class);
                    client.getKryo().register(java.lang.Class.class);
                    client.getKryo().register(GameState.class);
                    client.getKryo().register(Circle.class);
                    client.getKryo().register(float[].class);
                    client.addListener(new ClientHandler());
                }
            }
            theme = new Music("res/Theme_1.ogg");
            theme.loop(1f, 0.5f);
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 6; j++) {
                    hexles.add(new Hex(60 + (200 * j), 60 + (118 * i), 0.25f, (int) (128 * resMult), (int) (128 * resMult)));
                }
            }
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    hexles.add(new Hex(160 + (200 * j), (118 * i), 0.25f, (int) (128 * resMult), (int) (128 * resMult)));
                }
            }
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame s, Graphics g) throws SlickException {
        Image bg = Resources.images.get("bg");
        Image hexTex = Resources.images.get("hex");
        Image deadHexTex = Resources.images.get("deadHex");
        Image midHexTex = Resources.images.get("midHex");

        bg.draw(0, 0, resMult);
        hexTex.setFilter(Image.FILTER_LINEAR);
        deadHexTex.setFilter(Image.FILTER_LINEAR);
        midHexTex.setFilter(Image.FILTER_LINEAR);
        AngelCodeFont HexFont = Resources.HexFont;

        HexFont.drawString(gc.getWidth() * 0.849f, gc.getHeight() * 0.049f, message, Color.black);
        HexFont.drawString(gc.getWidth() * 0.849f, gc.getHeight() * 0.05f, message, Color.black);
        HexFont.drawString(gc.getWidth() * 0.849f, gc.getHeight() * 0.051f, message, Color.black);
        HexFont.drawString(gc.getWidth() * 0.85f, gc.getHeight() * 0.049f, message, Color.black);
        HexFont.drawString(gc.getWidth() * 0.85f, gc.getHeight() * 0.051f, message, Color.black);
        HexFont.drawString(gc.getWidth() * 0.851f, gc.getHeight() * 0.05f, message, Color.black);
        HexFont.drawString(gc.getWidth() * 0.85f, gc.getHeight() * 0.051f, message, Color.black);
        HexFont.drawString(gc.getWidth() * 0.851f, gc.getHeight() * 0.051f, message, Color.black);
        HexFont.drawString(gc.getWidth() * 0.85f, gc.getHeight() * 0.05f, message, Color.red);

        message = "Player "+curPlayer+"'s turn";
        hexles.stream().filter(hex -> hex.isActive>0.5f).forEach(hex -> hexTex.draw(hex.getX(), hex.getY(), hex.getScale()));
        hexles.stream().filter(hex -> hex.isActive==0.5f).forEach(hex -> midHexTex.draw(hex.getX(), hex.getY(), hex.getScale()));
        hexles.stream().filter(hex -> hex.isActive<0.5f).forEach(hex -> deadHexTex.draw(hex.getX(), hex.getY(), hex.getScale()));
        if (!isOngoing) {
            Rectangle box = new Rectangle(gc.getWidth() / 2f - gc.getWidth() * 0.3f / 2f, gc.getHeight() / 2f - gc.getHeight() * 0.3f / 2f, gc.getWidth() * 0.3f, gc.getHeight() * 0.3f);
            g.setColor(new Color(0, 0, 0, 200));
            g.draw(box);
            g.fill(box);
            String restart = "Press SPACE to restart", winnerMessage = "Player " + curPlayer + " wins!";
            HexFont.drawString(gc.getWidth() / 2f - (HexFont.getWidth(winnerMessage) / 2), gc.getHeight() / 2.2f - (HexFont.getHeight(winnerMessage) / 2), winnerMessage);
            HexFont.drawString(gc.getWidth() / 2f - (HexFont.getWidth(restart) / 2), gc.getHeight() / 2f - (HexFont.getHeight(restart) / 2), restart);
        }

        if (Escape) {
            Rectangle box = new Rectangle(gc.getWidth() / 2f - gc.getWidth() * 0.3f / 2f, gc.getHeight() / 2f - gc.getHeight() * 0.3f / 2f, gc.getWidth() * 0.3f, gc.getHeight() * 0.3f);
            g.setColor(new Color(0, 0, 0, 200));
            g.draw(box);
            g.fill(box);
            String restart = "Press ENTER to exit", winnerMessage = "Press ESCAPE to go back";
            HexFont.drawString(gc.getWidth() / 2f - (HexFont.getWidth(winnerMessage) / 2), gc.getHeight() / 2.2f - (HexFont.getHeight(winnerMessage) / 2), winnerMessage);
            HexFont.drawString(gc.getWidth() / 2f - (HexFont.getWidth(restart) / 2), gc.getHeight() / 2f - (HexFont.getHeight(restart) / 2), restart);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame s, int delta) throws SlickException {
        if (startLoad && !started) {
            init(gc, s);
        }
        if (gc.getInput().isKeyPressed(Input.KEY_F))
            fullscreen = !fullscreen;
        if (fullscreen)
            gc.setFullscreen(true);
        else
            gc.setFullscreen(false);
        if (gc.getScreenWidth() != gc.getWidth() && gc.getScreenHeight() != gc.getHeight()) {
            AppGameContainer g = (AppGameContainer) gc;
            resMult = ((float) gc.getScreenWidth() / (float) gc.getWidth());
            g.setDisplayMode(gc.getScreenWidth(), gc.getScreenHeight(), fullscreen);
            for (Hex hex : hexles) {
                hex.multCoordinates(resMult);
            }
        }

        if (!isOngoing) {
            message = "Player "+((curPlayer==1)?2:1)+" wins!";
            if(!played){
                Sound endGame = Resources.sounds.get("endgame");
                endGame.play(1.5f, 4.5f);
                played=!played;
            }
            if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
                resetGame();
            }
        } else if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
            Escape = !Escape;
        }
        if (Escape) {
            if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
                resetGame();
                Escape = false;
                if (resMult != MenuState.resMult) {
                    for (Button btn : MenuState.buttons)
                        btn.multCoords(resMult);
                    MenuState.resMult = resMult;
                }
                theme.stop();
                s.enterState(States.MENU);
            }
        }

    }

    @Override
    public void mousePressed(int button, int x, int y){
        if (canPlay) {
            isGameOngoing();
            Rectangle rect = new Rectangle(x, y, 1, 1);
            hexles.stream().filter(hex -> hex.collider.contains(rect) || hex.collider.intersects(rect)).forEach(Hex::isClicked);
        }
    }

    public void resetGame() {
        for (Hex hex : hexles)
            hex.isActive = 1f;
        played = false;
        curPlayer = 1;
        isOngoing = true;
    }

    @Override
    public void run() {

    }
}
