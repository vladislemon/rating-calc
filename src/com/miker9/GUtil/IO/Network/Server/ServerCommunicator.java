package com.miker9.GUtil.IO.Network.Server;

import com.miker9.GUtil.IO.Network.Connection;
import com.miker9.GUtil.IO.Network.ConnectionHandler;
import com.miker9.GUtil.IO.Network.ConnectionStatus;
import com.miker9.GUtil.IO.Network.Packet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * miker9
 * 27.04.14
 */
public class ServerCommunicator {
    protected ServerSocket serverSocket;
    protected final Map<Integer, Connection> connections = new HashMap<Integer, Connection>();
    protected ConnectionHandler connectionHandler;
    protected boolean closed;

    public ServerCommunicator(int port, ConnectionHandler connectionHandler) throws IOException{
        this.connectionHandler = connectionHandler;

        serverSocket = new ServerSocket(port);

        new Thread() {
            @Override
            public void run() {
                listen();
            }
        }.start();


    }

    private void listen() {
        while(!closed) {
            Socket socket;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                return;
            }

            if(socket != null) {
                Connection connection = new Connection(socket);
                synchronized (connections) {
                    connections.put(connection.getCID(), connection);
                }
                connectionHandler.onConnecting(connection);
            }

        }
    }

    public void close() {
        close("");
    }

    public void close(String message) {
        closed = true;
        if(serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {}

            synchronized (connections) {
                for (Connection connection : connections.values()) {
                    connection.disconnect("Server closed: " + message);
                }
            }
        }
    }

    public void update() {
        synchronized (connections) {
            Iterator<Map.Entry<Integer, Connection>> iterator = connections.entrySet().iterator();

            while(iterator.hasNext()) {
                Connection connection = iterator.next().getValue();

                connectionHandler.onUpdate(connection);
                if(connection.getStatus() == ConnectionStatus.CONNECTION_DISCONNECTED) {
                    connectionHandler.onDisconnected(connection);
                    iterator.remove();
                } else if(connection.getStatus() == ConnectionStatus.CONNECTION_ERROR_DISCONNECTED || connection.getStatus() == ConnectionStatus.CONNECTION_FAILED) {
                    connectionHandler.onDisconnectedWithError(connection);
                    iterator.remove();
                }
            }

        }
    }

    public void sendAll(Packet packet) {
        synchronized (connections) {
            for (Connection connection : connections.values()) {
                connection.sendPacket(packet);
            }
        }
    }

    public Connection[] getConnections() {
        Connection[] result = new Connection[connections.values().size()];
        connections.values().toArray(result);
        return result;
    }
}
