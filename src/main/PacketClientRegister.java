package main;

import com.miker9.GUtil.IO.Network.Packet;
import util.BytesUtil;

/**
 * slimon
 * 22.07.2014
 */
public class PacketClientRegister extends Packet {

    public PacketClientRegister(String nickname) {
        super(Chat.CLIENT_REGISTER_PACKET_ID, nickname.getBytes());
    }

    public PacketClientRegister(int id) {
        super(Chat.CLIENT_REGISTER_PACKET_ID, BytesUtil.toBytes(id));
    }
}
