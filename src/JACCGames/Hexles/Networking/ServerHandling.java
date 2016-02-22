package JACCGames.Hexles.Networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.util.Date;

//https://github.com/EsotericSoftware/kryonet

public class ServerHandling extends Listener{
    static Server server;
    static int udpPort = 27960, tcpPort = 27960;
    public static void main(String[] args) throws Exception {

        //Create the server and register the packet class
        server = new Server();
        server.bind(tcpPort, udpPort);
        server.getKryo().register(PacketMessage.class);
        server.start();

        server.addListener(new ServerHandling());


        System.out.println("Server is operational");
    }

    public void connected(Connection c){
        System.out.println("Received a connection from" + c.getRemoteAddressTCP().getHostString());
        PacketMessage packetMessage = new PacketMessage();
        packetMessage.message = "Hello friend! The time is: "+new Date().toString();
        c.sendTCP(packetMessage);
    }

    public void disconnected(Connection c){
        System.out.println("The client has disconnected from the server.");
    }

    public void received(Connection c, Object p){

    }


}
