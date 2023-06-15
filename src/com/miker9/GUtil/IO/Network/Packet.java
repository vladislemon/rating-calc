package com.miker9.GUtil.IO.Network;

/**
 * Created by miker9 on 25.04.14.
 */
public class Packet {
    private int size;
    private int id;
    private byte[] data;
    private int sourceCID;

    public Packet(int id, byte[] data) {
        this(id, data, 0);
    }

    public Packet(int id, byte[] data, int sourceCID) {
        this.sourceCID = sourceCID;
        size = data.length;
        this.id = id;
        this.data = data;
    }

    public int getSize() {
        return size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
        size = data.length;
    }

    public int getSourceCID() {
        return sourceCID;
    }

    public void setSourceCID(int sourceCID) {
        this.sourceCID = sourceCID;
    }

}

