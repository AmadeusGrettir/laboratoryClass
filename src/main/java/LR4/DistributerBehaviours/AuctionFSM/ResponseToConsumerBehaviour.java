package LR4.DistributerBehaviours.AuctionFSM;


import LR4.Containers.DistributerInfo;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseToConsumerBehaviour extends OneShotBehaviour {
    private DistributerInfo distInfo;
    private AID consumer;
    public ResponseToConsumerBehaviour(DistributerInfo distInfo, AID consumer) {
        this.distInfo = distInfo;
        this.consumer = consumer;
    }

    @Override
    public void action() {
        log.info("{} sends best price to {}", myAgent.getLocalName(), consumer.getLocalName());
        ACLMessage response = new ACLMessage(ACLMessage.INFORM);
        response.addReceiver(consumer);
        response.setProtocol("result");
        response.setContent(distInfo.getBestPrice() + ";" + distInfo.getRequestedEnergy() + ";" + distInfo.getBestProducer().getLocalName());
        getAgent().send(response);
    }
}
