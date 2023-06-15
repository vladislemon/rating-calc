package main;

import com.miker9.GUtil.IO.Network.Connection;
import com.miker9.GUtil.IO.Network.ConnectionHandler;
import com.miker9.GUtil.IO.Network.Packet;

import java.util.HashMap;

/**
 * slimon
 * 14.07.2014
 */
public class ServerConnectionHandler implements ConnectionHandler {

    private HashMap<Integer, String> names = new HashMap<Integer, String>();

    public int registerClient(String name) {
        int id = names.size();
        names.put(id, name);
        return id;
    }

    @Override
    public void onUpdate(Connection connection) {
        while(connection.hasPackets()) {
            Packet packet = connection.nextPacket();
            switch (packet.getId()) {
                case Chat.CLIENT_REGISTER_PACKET_ID:
                    String name = new String(packet.getData());
                    Chat.names.add(name);

                    break;
                case Chat.CHAT_MESSAGE_PACKET_ID:
                    Chat.sendServerChatMessage(PacketChatMessage.decodeData(packet.getData()));
                    break;
            }
        }
    }

    @Override
    public void onConnecting(Connection connection) {

    }

    @Override
    public void onDisconnected(Connection connection) {

    }

    @Override
    public void onDisconnectedWithError(Connection connection) {

    }
}
