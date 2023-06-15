package main;

import com.miker9.GUtil.IO.Network.Client.ClientCommunicator;
import com.miker9.GUtil.IO.Network.Server.ServerCommunicator;
import timer.Timer;
import timer.TimerListener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * slimon
 * 07.07.2014
 */
public class Chat {

    public static final int CLIENT_REGISTER_PACKET_ID = 1,
            CLIENT_ID_PACKET_ID = 2,
            SYSTEM_MESSAGE_PACKET_ID = 3,
            CHAT_MESSAGE_PACKET_ID = 4;

    public static ServerCommunicator server;
    public static ClientCommunicator client;
    public static Timer timer;
    public static boolean isHosted, isConnected;

    public static int id = -1;
    public static List<String> names = new ArrayList<String>();

    public static void init() {
        isHosted = false;
        isConnected = false;
        timer = new Timer(50);
        timer.register(timerListener);
        timer.start();
    }

    public static void hostServer() {
        try {
            server = new ServerCommunicator(getPort(), new ServerConnectionHandler());
            isHosted = true;
            timer.setPause(false);
        } catch (IOException e) {
            App.onError(e);
        }
    }

    public static void closeServer() {
        server.close("server closed connection");
        //server = null;
        isHosted = false;
    }

    public static void connectToServer() {
        client = new ClientCommunicator(new ClientConnectionHandler());
        try {
            client.connect(InetAddress.getByName(getIp()), getPort());
            isConnected = true;
            timer.setPause(false);
        } catch (UnknownHostException e) {
            App.onError(e);
        }
        client.getConnection().sendPacket(new PacketClientRegister(getNickname()));
    }

    public static void disconnect() {
        client.getConnection().disconnect("client left the server");
        //client = null;
        isConnected = false;
    }

    private static String getIp() {
        return App.gui.chat.ipField.getText();
    }

    private static int getPort() {
        return Integer.parseInt(App.gui.chat.portField.getText());
    }

    private static String getNickname() {
        return App.gui.chat.nickField.getText();
    }

    public static void sendChatMessage(String message) {
        if(id != -1)
        client.getConnection().sendPacket(new PacketChatMessage(id, message));
    }

    public static void sendServerChatMessage(PacketChatMessage packet) {
        server.sendAll(packet);
    }

    public static void addMessage(PacketChatMessage packet) {
        App.gui.chat.messagesField.append(names.get(packet.clientId));
        App.gui.chat.messagesField.append(": ");
        App.gui.chat.messagesField.append(packet.message);
        App.gui.chat.messagesField.append("\n");
    }

    public static void addSystemMessage(String message) {
        App.gui.chat.messagesField.append("#System: ");
        App.gui.chat.messagesField.append(message);
        App.gui.chat.messagesField.append("\n");
    }

    private static TimerListener timerListener = new TimerListener() {
        @Override
        public void onTick(long currentTick) {
            timer.setPause(!isHosted && !isConnected);
            if(isHosted) {
                server.update();
            }
            if(isConnected) {
                client.update();
            }
        }
    };
}
