package com.miker9.GUtil.IO.Network.Client;

import com.miker9.GUtil.IO.Network.Connection;
import com.miker9.GUtil.IO.Network.ConnectionHandler;
import com.miker9.GUtil.IO.Network.ConnectionStatus;

import java.net.InetAddress;

/**
 * Created by miker9 on 27.04.14.
 */
public class ClientCommunicator {
    protected ConnectionHandler connectionHandler;
    protected Connection connection;

    public ClientCommunicator(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }


    public void connect(InetAddress address, int port) {
        if(connection != null) {
            connection.disconnect();
        }
        connection = new Connection(address, port);
        connectionHandler.onConnecting(connection);
    }

    public void connect(InetAddress address, int port, int timeout) {
        connection = new Connection(address, port, timeout);
        connectionHandler.onConnecting(connection);
    }


    public void update() {
        if(connection != null) {
            connectionHandler.onUpdate(connection);
        }

        if(connection != null) {
            if(connection.getStatus() == ConnectionStatus.CONNECTION_DISCONNECTED) {
                connectionHandler.onDisconnected(connection);
                connection = null;
            } else if(connection.getStatus() == ConnectionStatus.CONNECTION_ERROR_DISCONNECTED || connection.getStatus() == ConnectionStatus.CONNECTION_FAILED) {
                connectionHandler.onDisconnectedWithError(connection);
                connection = null;
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
