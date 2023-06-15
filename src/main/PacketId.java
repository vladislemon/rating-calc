package main;

import com.miker9.GUtil.IO.Network.Packet;
import util.BytesUtil;

/**
 * slimon
 * 22.07.2014
 */
public class PacketId extends Packet {

    public PacketId(int id) {
        super(Chat.CLIENT_ID_PACKET_ID, BytesUtil.toBytes(id));
    }
}
