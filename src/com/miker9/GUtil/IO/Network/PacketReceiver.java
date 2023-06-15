package com.miker9.GUtil.IO.Network;

import java.nio.ByteBuffer;

/**
 * Created by miker9 on 25.04.14.
 */
public class PacketReceiver {
    private int id;
    private int size;
    private ByteBuffer data;
    private int filled;

    public PacketReceiver(int id, int size) {
        this.id = id;
        this.size = size;
        data = ByteBuffer.allocate(size);
    }

    public int dataNeeded() {
        return size - filled;
    }

    public void addData(byte[] bytes) {
        filled += bytes.length;
        data.put(bytes);
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public byte[] getData() {
        return data.array();
    }
}
