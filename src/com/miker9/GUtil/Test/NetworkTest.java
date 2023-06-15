package com.miker9.GUtil.Test;

import com.miker9.GUtil.IO.Network.Client.ClientCommunicator;
import com.miker9.GUtil.IO.Network.Server.ServerCommunicator;
import main.App;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * miker9
 * 25.04.14
 */
public class NetworkTest {

    public static void main(String[] args) throws UnknownHostException, InterruptedException {


        ServerCommunicator server = null;
        try {
            server = new ServerCommunicator(25565, new TestConnectionHandler("server"));
        } catch (IOException e) {
            App.onError(e);
        }

        ClientCommunicator client = new ClientCommunicator(new TestConnectionHandler("client1"));
        client.connect(InetAddress.getByName("127.0.0.1"), 25565);

        ClientCommunicator client2 = new ClientCommunicator(new TestConnectionHandler("client2"));
        client2.connect(InetAddress.getByName("127.0.0.1"), 25565);

        ClientCommunicator client3 = new ClientCommunicator(new TestConnectionHandler("client3"));
        client3.connect(InetAddress.getByName("127.0.0.1"), 25565);


        Thread.sleep(100);

        //server.close();
        client2.getConnection().disconnect("fdgjhs");
        while(true) {
            client.update();
            client2.update();
            client3.update();
            server.update();



            Thread.sleep(100);
        }


    }
}
