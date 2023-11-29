package UdpHomework;

import com.sun.jna.NativeLibrary;
import jade.core.AID;
import lombok.SneakyThrows;
import org.pcap4j.core.*;

import java.util.Date;
import java.util.List;

public class RawUdpSocketServer {
    static {
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            NativeLibrary.addSearchPath("wpcap", "C:\\Windows\\System32\\Npcap");
        }
    }

    AgentDetector detector;

    public RawUdpSocketServer(AgentDetector detector) {
        this.detector = detector;
    }

    protected boolean run = true;

    @SneakyThrows
    public void start(int port){
        run = true;
        List<PcapNetworkInterface> allDevs = Pcaps.findAllDevs();
        PcapNetworkInterface networkInterface = null;
        for (PcapNetworkInterface allDev : allDevs) {
            if (allDev.getName().equals("\\Device\\NPF_Loopback")){
                networkInterface = allDev;
                break;
            }
        }
        //TODO: handle if interface was not found
        PcapHandle pcapHandle = networkInterface.openLive(65536, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 50);
        pcapHandle.setFilter("ip proto \\udp && dst port "+port, BpfProgram.BpfCompileMode.NONOPTIMIZE);

        runInThread(pcapHandle);
    }

    protected void runInThread(PcapHandle pcapHandle) {
        new Thread( ()-> {
            grabPackets(pcapHandle);
        }).start();
    }

    protected void grabPackets(PcapHandle pcapHandle) {
        try {
            pcapHandle.loop(0, (PacketListener) packet -> {
                byte[] rawData = packet.getRawData();
                byte[] data = new byte[rawData.length-32];
                System.arraycopy(rawData, 32, data, 0, data.length);

                Parser parser = new Parser();
                AID aid = parser.fromJson(new String(data), AID.class);

                detector.agents.put(aid, new Date());

                if (!run){
                    try {
                        pcapHandle.breakLoop();
                    } catch (NotOpenException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (PcapNativeException | InterruptedException | NotOpenException e) {
            throw new RuntimeException(e);
        }
    }


}
