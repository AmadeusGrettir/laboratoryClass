package UdpHomework;
import jade.core.AID;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.*;

@Slf4j
public class AgentDetector {
    @Setter
    @Getter
    private AID agent;
    Map<AID, Date> agents;

    private final ScheduledExecutorService ses;
    private ScheduledFuture<?> discoveringTask;
    private ScheduledFuture<?> cleaningTask;
    private ScheduledFuture<?> sendingTask;

    public AgentDetector() {
        ses = Executors.newScheduledThreadPool(3);
        agents = new HashMap<>();
    }

    @SneakyThrows
    void startPublishing(AID aid, int port){
        RawUdpSocketCLient cLient = new RawUdpSocketCLient(this);
        cLient.initialize(port);

        PacketCreator pc = new PacketCreator();
        byte[] testData = pc.getPacket(aid);

        if (sendingTask == null) {
            log.info("{} started sending.", aid.getLocalName());
            sendingTask = ses.scheduleWithFixedDelay(() -> cLient.send(testData),
                    1000, 1000, TimeUnit.MILLISECONDS);
        } else {
            log.warn("Unable command: Sending has been started before.");
        }
    }

    public void stopPublishing() {
        log.info("Agent {} stopped sending.", agent.getLocalName());
        sendingTask.cancel(true);
        sendingTask = null;
    }

    void startDiscovering(int port){
        RawUdpSocketServer rawServer = new RawUdpSocketServer(this);
        if (discoveringTask == null) {
            discoveringTask = ses.schedule(() -> rawServer.start(port), 0, TimeUnit.MILLISECONDS);
            cleaningTask = ses.scheduleWithFixedDelay(this::SuicidedAgentRemoving,
                    4000,
                    3000,
                    TimeUnit.MILLISECONDS);
            log.info("Agent {} started discovering.", agent.getLocalName());
        } else {
            log.warn("Unable command: Discovering has been started before.");
        }
    }


    private void SuicidedAgentRemoving() {
        log.warn("{} is discovering", agent.getLocalName());

        Date curDate = new Date();
        Map<AID, Date> curAgents = new HashMap<>();
        curAgents.putAll(agents);
        for (Map.Entry<AID, Date> entry: curAgents.entrySet()) {
            if (curDate.getTime() - entry.getValue().getTime() > 1500) {
                log.info("{} was deleted from list of {}",
                        entry.getKey().getLocalName(),
                        agent.getLocalName());

                log.info("Delay of sending package for {} - {}", entry.getKey().getLocalName(),
                        curDate.getTime() - entry.getValue().getTime());

                agents.remove(entry.getKey());
            }
        }

        if (!agents.containsKey(agent)){
            log.info("Discovering for {} was stopped", agent.getLocalName());
            discoveringTask.cancel(true);
            cleaningTask.cancel(true);
        }
    }


    List<AID> getActiveAgents(){
        List<AID> activeAgents = new ArrayList<>(agents.keySet());
        return activeAgents;
    }
}
