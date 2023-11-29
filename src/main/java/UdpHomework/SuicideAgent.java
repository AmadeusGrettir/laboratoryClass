package UdpHomework;

import jade.core.AID;
import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class SuicideAgent extends Agent {

    private AgentDetector detector = new AgentDetector();

    @Override
    protected void setup() {

        long myTime = (long) (new Random().nextDouble() * 20000);

        log.debug("Start {}. Will end in {} sec", this.getLocalName(), myTime/1000);

        detector.setAgent(new AID(this.getLocalName(), false));
        detector.startDiscovering(1200);
        detector.startPublishing(new AID(this.getLocalName(), false), 1200);

        addBehaviour(new SuicideBehaviour(this, myTime, detector));
    }

}
