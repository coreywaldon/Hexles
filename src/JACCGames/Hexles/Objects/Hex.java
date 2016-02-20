package JACCGames.Hexles.Objects;

import JACCGames.Hexles.GameState.GameState;
import JACCGames.Hexles.Resources.Resources;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import static JACCGames.Hexles.GameState.GameState.hexles;

/**
 * Created by corey on 2/19/2016.
 */
public class Hex {
    private int x;
    private int y;
    private int WIDTH;
    private int HEIGHT;


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    float scale;
    public Circle collider;
    private Circle deactivationCollider;
    public float isActive = 1;

    public Hex(int x, int y, float scale, int width, int height){
        this.x=x;
        this.y=y;
        this.scale = scale;
        this.WIDTH=width;
        this.HEIGHT=height;
        collider = new Circle(x+(WIDTH/2), y+(HEIGHT/2), (WIDTH/2)-(50*scale));
        deactivationCollider = new Circle(x+(WIDTH/2), y+(HEIGHT/2), WIDTH);
    }

    public void isClicked(){
        if(isActive>0) {
            Sound click  = Resources.sounds.get("click");
            click.play();
            click.play(2f, 0.3f);
            GameState.curPlayer = (GameState.curPlayer==1) ? 2:1;
            System.out.println(GameState.curPlayer);
            isActive -= 0.5;
            hexles.stream().filter(hex -> checkCollision(hex.collider)).forEach(hex -> hex.isActive -= 0.5);
        }
    }

    public boolean checkCollision(Shape shape){
        if(deactivationCollider.intersects(shape)||deactivationCollider.contains(shape)) return true;
        else return false;
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
