package UdpHomework;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;

import java.util.List;

@Slf4j
public class RawUdpSocketCLient {
    private PcapHandle pcapHandle;
    AgentDetector detector;

    public RawUdpSocketCLient(AgentDetector detector) {
        this.detector = detector;
    }

    @SneakyThrows
    public void send(byte[] data){
        String name = detector.getAgent().getLocalName();
        log.debug("Agent {} send message", name);
        pcapHandle.sendPacket(data);
    }

    @SneakyThrows
    public void initialize(int port){
        List<PcapNetworkInterface> allDevs = Pcaps.findAllDevs();
        PcapNetworkInterface networkInterface = null;
        for (PcapNetworkInterface allDev : allDevs) {
            if (allDev.getName().equals("\\Device\\NPF_Loopback")){
                networkInterface = allDev;
                break;
            }
        }
        pcapHandle = networkInterface.openLive(65536, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 50);


    }



}
