package JACCGames.Hexles.Networking;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.jcraft.jogg.Packet;

/**
 * Created by corey on 2/21/2016.
 */
public class ClientHandling extends Listener {
    static Client client;
    static String ip = "localhost";
    static int tcpPort = 27960, udpPort = 27960;

    static boolean messageRecieved = false;

    public static void main(String[] args) throws Exception{
        System.out.println("Connecting");
        client = new Client();
        client.getKryo().register(PacketMessage.class);
        client.start();
        client.connect(5000, ip, tcpPort, udpPort);
        client.addListener(new ClientHandling());
        System.out.println("Waiting for packet...");
        while(!messageRecieved){
            Thread.sleep(5000);
        }
        System.out.println("The client will now exit");
    }

    public void received(Connection c, Object p){
        if(p instanceof PacketMessage){
            PacketMessage packet = (PacketMessage) p;
            System.out.println("Message from host: " + packet.message);
            messageRecieved=true;
        }
    }


}
