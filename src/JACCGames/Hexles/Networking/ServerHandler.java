package JACCGames.Hexles.Networking;

import JACCGames.Hexles.GameState.GameState;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

//https://github.com/EsotericSoftware/kryonet

public class ServerHandler extends Listener {
    Server server;
    int tcpPort, udpPort;

    public ServerHandler(Server server, int tcpPort, int udpPort) {
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
        this.server = server;
    }

    public void connected(Connection c) {
    }

    public void disconnected(Connection c) {
    }

    public void received(Connection c, Object p) {
        System.out.println("RECEIVED (SERVER)");
        if (p instanceof float[]) {
            GameState.curPlayer = 1;
            GameState.syncHex((float[]) p);
            GameState.canPlay = true;
        }

    }
}
