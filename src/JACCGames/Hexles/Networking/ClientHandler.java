package JACCGames.Hexles.Networking;

import JACCGames.Hexles.GameState.GameState;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 * Created by corey on 2/21/2016.
 */
public class ClientHandler extends Listener {

    public static void main(String[] args) throws Exception {
        System.out.println("Connecting");
    }

    public void connected(Connection c) {
        System.out.println("Connected");
    }

    public void received(Connection c, Object p) {
        System.out.println("RECEIVED (CLIENT)");
        if (p instanceof float[]) {
            GameState.curPlayer = 2;
            GameState.canPlay = true;
            GameState.syncHex((float[]) p);
        }

    }
}
