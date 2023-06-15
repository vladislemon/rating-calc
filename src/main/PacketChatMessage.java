package main;

import com.miker9.GUtil.IO.Network.Packet;
import util.BytesUtil;

/**
 * slimon
 * 14.07.2014
 */
public class PacketChatMessage extends Packet {

    public int clientId;
    public String message;

    public PacketChatMessage(int clientId, String message) {
        super(Chat.CHAT_MESSAGE_PACKET_ID, encodeData(clientId, message));
        this.clientId = clientId;
        this.message = message;
    }

    private static byte[] encodeData(int clientId, String message) {
        byte[] id = BytesUtil.toBytes(clientId);
        byte[] mes = message.getBytes();
        byte[] ret = new byte[id.length + mes.length];
        System.arraycopy(id, 0, ret, 0, id.length);
        System.arraycopy(mes, id.length, ret, id.length, mes.length - id.length);
        return ret;
    }

    public static PacketChatMessage decodeData(byte[] data) {
        byte[] idData = new byte[4];
        System.arraycopy(data, 0, idData, 0, idData.length);
        int id = BytesUtil.toInt(idData);
        byte[] mesData = new byte[data.length - idData.length];
        System.arraycopy(data, idData.length, mesData, 0, mesData.length);
        String message = new String(mesData);
        return new PacketChatMessage(id, message);
    }
}
