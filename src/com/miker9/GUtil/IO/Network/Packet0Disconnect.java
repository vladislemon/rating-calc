package com.miker9.GUtil.IO.Network;

/**
 * Created by miker9 on 25.04.14.
 */
public class Packet0Disconnect extends Packet{
    public Packet0Disconnect() {
        this("");
    }

    public Packet0Disconnect(String message) {
        super(0, message.getBytes(), 0);
    }
}

