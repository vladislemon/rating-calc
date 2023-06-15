package com.miker9.GUtil.IO.Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

/**
 * miker9
 * 23.04.14
 */
public class Connection extends Thread{
    public static int lastCID = 1;


    protected int packetSizeLimit;
    protected InetAddress address;
    protected int port;
    protected int connectionTimeout;
    protected long lastPacketReceivedTime;
    protected String errorMessage = "";
    protected int CID;


    protected Socket socket;
    protected DataInputStream in;
    protected DataOutputStream out;
    protected final Queue<Packet> received = new LinkedList<Packet>();
    protected final Queue<Packet> toSend = new LinkedList<Packet>();
    protected ConnectionStatus status;
    protected PacketReceiver packetReceiver;
    protected boolean closed;
    protected boolean closeOnNextIteration;

    public Connection(Socket socket) {
        status = ConnectionStatus.CONNECTION_NOT_INITIALIZED;
        packetSizeLimit = 1024 * 1024;//1 mb
        address = socket.getInetAddress();
        port = socket.getPort();
        this.socket = socket;
        CID = lastCID++;

        start();
    }

    public Connection(InetAddress address, int port) {
        this(address, port, 1000);
    }

    public Connection(InetAddress address, int port, int timeout) {
        status = ConnectionStatus.CONNECTION_NOT_INITIALIZED;
        packetSizeLimit = 1024 * 1024;//1 mb
        this.address = address;
        this.port = port;
        connectionTimeout = timeout;
        CID = lastCID++;

        start();
    }



    @Override
    public void run() {

        //if we don't already have a socket it means we need to create one.
        if(socket == null) {
            socket = new Socket();
            try {
                socket.connect(new InetSocketAddress(address, port));
            } catch (IOException e) {
                status = ConnectionStatus.CONNECTION_FAILED;
                errorMessage = e.getMessage();
                closed = true;
                return;
            }
        }


        //checking if socket is connected.
        if(socket.isConnected()) {
            status = ConnectionStatus.CONNECTION_OK;
            lastPacketReceivedTime = System.currentTimeMillis();
        } else {
            status = ConnectionStatus.CONNECTION_FAILED;
            errorMessage = "Socket is not connected";
            closed = true;
            return;
        }


        //getting input and output streams.
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            status = ConnectionStatus.CONNECTION_FAILED;
            errorMessage = e.getMessage();
            closed = true;
            return;
        }




        while(!closed) {
            //a trick to make sure at least 1 cycle is made after closeOnNextIteration becomes true
            boolean shouldClose = closeOnNextIteration;

            //receive
            synchronized (received) {
                try {
                    while(in.available() > 0) {

                        if(packetReceiver == null){
                            if(in.available() >= 8) {
                                int id = in.readInt();
                                int size = in.readInt();
                                packetReceiver = new PacketReceiver(id, size);

                                //check packet size
                                if(packetReceiver.getSize() > packetSizeLimit || packetReceiver.getSize() < 0) {
                                    errorMessage = "Wrong packet size (ID = " + packetReceiver.getId() + ", CID = " + CID + " size = " + packetReceiver.getSize() + "b)";
                                    status = ConnectionStatus.CONNECTION_ERROR_DISCONNECTED;
                                    shouldClose = true;
                                }
                            }
                        }

                        if(packetReceiver != null) {
                            byte[] data = new byte[Math.min(packetReceiver.dataNeeded(), in.available())];
                            in.read(data);
                            packetReceiver.addData(data);

                            if(packetReceiver.dataNeeded() == 0) {
                                lastPacketReceivedTime = System.currentTimeMillis();

                                //check if packet id = 0 (default disconnect packet)
                                if(packetReceiver.getId() == 0) {
                                    status = ConnectionStatus.CONNECTION_DISCONNECTED;
                                    shouldClose = true;
                                }

                                received.add(new Packet(packetReceiver.getId(), packetReceiver.getData()));
                                packetReceiver = null;
                            }
                        }
                    }



                } catch (IOException e) {
                    errorMessage = e.getMessage();
                    status = ConnectionStatus.CONNECTION_ERROR_DISCONNECTED;
                    shouldClose = true;
                }
            }

            //send
            synchronized (toSend) {
                try {
                    while(!toSend.isEmpty()) {
                        Packet p = toSend.poll();
                        out.writeInt(p.getId());
                        out.writeInt(p.getSize());
                        out.write(p.getData());
                        out.flush();
                    }
                } catch (IOException e) {
                    errorMessage = e.getMessage();
                    status = ConnectionStatus.CONNECTION_ERROR_DISCONNECTED;
                    shouldClose = true;
                }
            }


            if(shouldClose) {
                closed = true;
            }

            //limit maximum tick amount
            try {Thread.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {e.printStackTrace();}

        //close socket at the end
        try {socket.close();} catch (IOException e) {errorMessage = e.getMessage();}
    }

    public void sendPacket(Packet p) {
        synchronized (toSend) {
            toSend.add(p);
        }
    }

    public boolean hasPackets() {
        synchronized (received) {
            return !received.isEmpty();
        }
    }

    public Packet nextPacket() {
        synchronized (received) {
            return received.poll();
        }
    }

    public void close(ConnectionStatus status) {
        closeOnNextIteration = true;
        this.status = status;
    }

    public void close() {
        close(status = ConnectionStatus.CONNECTION_DISCONNECTED);
    }

    public void disconnect() {
        disconnect("");
    }

    public void disconnect(String message) {
        sendPacket(new Packet0Disconnect(message));
        close();
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public Socket getSocket() {
        return socket;
    }

    public int getCID() {
        return CID;
    }

    public ConnectionStatus getStatus() {
        return status;
    }

    public long getLastPacketReceivedTime() {
        return lastPacketReceivedTime;
    }

    public long getTimeSinceLastPackedReceived() {
        if(status == ConnectionStatus.CONNECTION_OK) {
            return System.currentTimeMillis() - getLastPacketReceivedTime();
        } else {
            return 0;
        }
    }

    public boolean isClosed() {
        return closed;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getPacketSizeLimit() {
        return packetSizeLimit;
    }

    public void setPacketSizeLimit(int packetSizeLimit) {
        this.packetSizeLimit = packetSizeLimit;
    }

    public void setStatus(ConnectionStatus status) {
        this.status = status;
    }
}
