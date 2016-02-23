package JACCGames.Hexles.Networking;

import JACCGames.Hexles.GameState.GameState;
import JACCGames.Hexles.Objects.Hex;

import java.util.ArrayList;

/**
 * Created by corey on 2/21/2016.
 */
// PacketMessage class. Everything in this class can be sent over the network.
public class PacketMessage {
    public ArrayList<Hex> hexles = GameState.hexles;
    public boolean isOngoing = GameState.isOngoing;
    public float[] hexlesState = new float[hexles.size()];

    public PacketMessage() {
        hexles = GameState.hexles;
        for (int i = 0; i < hexlesState.length; i++) {
            hexlesState[i] = hexles.get(i).isActive;
        }
    }
}
