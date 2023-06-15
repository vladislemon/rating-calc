package com.miker9.GUtil.Test;

import com.miker9.GUtil.IO.Network.Connection;
import com.miker9.GUtil.IO.Network.ConnectionHandler;
import com.miker9.GUtil.IO.Network.ConnectionStatus;
import com.miker9.GUtil.IO.Network.Packet;

/**
 * miker9
 * 27.04.14
 */
public class TestConnectionHandler implements ConnectionHandler {
    private String name;
    public TestConnectionHandler(String name) {
        this.name = name;
    }

    @Override
    public void onUpdate(Connection connection) {

        while(connection.hasPackets()) {
            Packet packet = connection.nextPacket();
            System.out.println(name + ": [" + connection.getAddress() + "] " + connection.getCID() + " Packet received: ID = " + packet.getId() + " Size = " + packet.getSize());
        }

        //Info.info(connection.getTimeSinceLastPackedReceived());
        if(connection.getTimeSinceLastPackedReceived() > 1000) {
            System.out.println(name + ": [" + connection.getAddress() + "] " + connection.getCID() + " no messages received for 1 seconds. Disconnecting");
            connection.disconnect("Time out");
        }
    }



    @Override
    public void onConnecting(Connection connection) {
        System.out.println(name + ": [" + connection.getAddress() + "] " + connection.getCID() + " is connecting");
    }

    @Override
    public void onDisconnected(Connection connection) {
        System.out.println(name + ": [" + connection.getAddress() + "] " + connection.getCID() + " has disconnected");
    }

    @Override
    public void onDisconnectedWithError(Connection connection) {
        if(connection.getStatus() == ConnectionStatus.CONNECTION_FAILED) {
            System.out.println(name + ": [" + connection.getAddress() + "] " + connection.getCID() + " connection failed: " + connection.getErrorMessage());
        } else if(connection.getStatus() == ConnectionStatus.CONNECTION_ERROR_DISCONNECTED) {
            System.out.println(name + ": [" + connection.getAddress()+ "] " + connection.getCID() +  " connection error: " + connection.getErrorMessage());
        }
    }
}
