package main;

import com.miker9.GUtil.IO.Network.Packet;

/**
 * slimon
 * 14.07.2014
 */
public class PacketSystemMessage extends Packet {

    public PacketSystemMessage(String message) {
        super(Chat.SYSTEM_MESSAGE_PACKET_ID, message.getBytes());
    }
}
