package LR4.DistributerBehaviours.AuctionFSM;


import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BadEndingBehaviour extends OneShotBehaviour {
    private AID consumer;
    public BadEndingBehaviour( AID consumer) {
        this.consumer = consumer;
    }

    @Override
    public void action() {
        log.info("{} sends best price to {}", myAgent.getLocalName(), consumer.getLocalName());
        ACLMessage response = new ACLMessage(ACLMessage.INFORM);
        response.addReceiver(consumer);
        response.setProtocol("result");
        response.setContent("Trading wasn't successful");
        getAgent().send(response);
    }
}
