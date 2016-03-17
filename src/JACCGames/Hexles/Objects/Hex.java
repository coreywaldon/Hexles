package JACCGames.Hexles.Objects;

import JACCGames.Hexles.GameState.GameState;
import JACCGames.Hexles.Networking.PacketMessage;
import JACCGames.Hexles.Resources.Resources;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import static JACCGames.Hexles.GameState.GameState.hexles;

public class Hex {
    public Circle collider;
    public float isActive = 1;
    float scale;
    private int x;
    private int y;
    private int WIDTH;
    private int HEIGHT;
    private Circle deactivationCollider;
    public Hex(int x, int y, float scale, int width, int height){
        this.x=x;
        this.y=y;
        this.scale = scale;
        this.WIDTH=width;
        this.HEIGHT=height;
        collider = new Circle(x+(WIDTH/2), y+(HEIGHT/2), (WIDTH/2)-(50*scale));
        deactivationCollider = new Circle(x+(WIDTH/2), y+(HEIGHT/2), WIDTH);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public void isClicked(){
        if(isActive>0) {
            Sound click  = Resources.sounds.get("click");
            click.play();
            click.play(2f, 0.3f);
            GameState.curPlayer = (GameState.curPlayer==1) ? 2:1;
            this.isActive -= 0.5;
            hexles.stream().filter(hex -> checkCollision(hex.collider)).forEach(hex -> hex.isActive -= 0.5);
            GameState.isOngoing = GameState.isGameOngoing();
            if (!GameState.localGame) {
                if (GameState.isServer) GameState.server.sendToAllTCP(new PacketMessage().hexlesState);
                else GameState.client.sendTCP(new PacketMessage().hexlesState);
                GameState.canPlay = false;
            }
        }
    }
    public boolean checkCollision(Shape shape){
        return deactivationCollider.intersects(shape) || deactivationCollider.contains(shape);
    }

    public int getX(){return this.x;}

    public void multCoordinates(float scale){
        this.x=Math.round(x*scale);
        this.y=Math.round(y*scale);
        this.scale = scale*this.scale;
        HEIGHT = Math.round(HEIGHT*scale);
        WIDTH = Math.round(WIDTH*scale);
        collider = new Circle(x+(WIDTH/2), y+(HEIGHT/2), (WIDTH/2)-(50*this.scale));
        deactivationCollider = new Circle(x+(WIDTH/2), y+(HEIGHT/2), WIDTH);
    }

    public int getY(){return this.y;}

    public float getScale(){return this.scale;}
}
