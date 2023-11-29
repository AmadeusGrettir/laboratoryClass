package UdpHomework;

import jade.core.AID;
import lombok.SneakyThrows;

import java.net.InetAddress;
import java.nio.ByteBuffer;

public class PacketCreator {

    @SneakyThrows
    public byte[] getPacket(AID aid) {
        Parser parser = new Parser();
        String content = parser.toJson(aid);

        byte[] data = content.getBytes();

        int totalLength = data.length + 28;
        byte [] packet = new byte[totalLength + 4];
        for (int i = 0, j = 7; i < 1; i++, j++) {
            packet[i] = longToBytes(0x02)[j];
        }

        // безполезная первая часть


        //Header Length = 20 bytes
        for (int i = 4, j = 7; i < 5; i++, j++) packet[i] = longToBytes(69)[j];
        //Differentiated Services Field
        for (int i = 5 , j = 7; i < 6; i++, j++) packet[i] = longToBytes(0x00)[j];
        //Total Length
        for (int i = 6, j = 6; i < 8; i++, j++) packet[i] = longToBytes(totalLength)[j];
        //Identification - for fragmented packages
        for (int i = 8, j = 6; i < 10; i++, j++) packet[i] = longToBytes(33500)[j];
        //Flag, Fragment Offset - for fragmented packages
        for (int i = 10, j = 6; i < 12; i++, j++) packet[i] = longToBytes(0x00)[j];
        //Time to Live - max limit for moving through the network
        for (int i = 12, j = 7; i < 13; i++, j++) packet[i] = longToBytes(128)[j];
        //Protocol - UDP
        for (int i = 13 , j = 7; i < 14; i++, j++) packet[i] = longToBytes(17)[j];
        //Header Checksum, can be 0x00 if it is not calculated
        for (int i = 14, j = 6; i < 16; i++, j++) packet[i] = longToBytes(0x00)[j];


        // наконец User Datagram Protocol (что-то полезное)
        byte[] ipSourceBytes = InetAddress.getByName("127.0.0.1").getAddress();
        for (int i = 16, j = 0; i < 20; i++, j++) packet[i] = ipSourceBytes[j];

        byte[] ipDestinationBytes = InetAddress.getByName("255.255.255.255").getAddress();
        for (int i = 20, j = 0; i < 24; i++, j++) packet[i] = ipDestinationBytes[j];

        //Source port
        for (int i = 24, j = 6; i < 26; i++, j++) packet[i] = longToBytes(1020)[j];
        //Destination port
        for (int i = 26, j = 6; i < 28; i++, j++) packet[i] = longToBytes(1200)[j];
        //Length
        int length = totalLength - 20;
        for (int i = 28, j = 6; i < 30; i++, j++) packet[i] = longToBytes(length)[j];
        //Checksum, can be 0x00 if it is not calculated
        for (int i = 30, j = 6; i < 32; i++, j++) packet[i] = longToBytes(0x0000)[j];
        //Data
        System.arraycopy(data, 0, packet, 32, data.length);
        return packet;
    }

    private byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }
}
