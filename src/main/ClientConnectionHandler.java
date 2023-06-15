package main;

import com.miker9.GUtil.IO.Network.Connection;
import com.miker9.GUtil.IO.Network.ConnectionHandler;
import com.miker9.GUtil.IO.Network.Packet;
import util.BytesUtil;

/**
 * slimon
 * 07.07.2014
 */
public class ClientConnectionHandler implements ConnectionHandler {

    @Override
    public void onUpdate(Connection connection) {
        while(connection.hasPackets()) {
            Packet packet = connection.nextPacket();
            switch (packet.getId()) {
                case Chat.CLIENT_REGISTER_PACKET_ID:
                    Chat.id = BytesUtil.toInt(packet.getData());
                    break;
                case Chat.CHAT_MESSAGE_PACKET_ID:
                    Chat.addMessage(PacketChatMessage.decodeData(packet.getData()));
                    break;
            }
        }
    }

    @Override
    public void onConnecting(Connection connection) {
        App.gui.chat.messagesField.append("connected\n");
    }

    @Override
    public void onDisconnected(Connection connection) {
        App.gui.chat.messagesField.append("disconnected:\n");
    }

    @Override
    public void onDisconnectedWithError(Connection connection) {
        App.gui.chat.messagesField.append("disconnected:" + connection.getErrorMessage() + "\n");
    }
}
