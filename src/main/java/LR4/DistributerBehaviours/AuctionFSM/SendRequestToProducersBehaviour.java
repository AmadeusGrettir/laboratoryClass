package LR4.DistributerBehaviours.AuctionFSM;

import LR4.Containers.DistributerInfo;
import LR4.DfHelper;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SendRequestToProducersBehaviour extends OneShotBehaviour {
    private DistributerInfo distributerInfo;
    private String topicName;
    public SendRequestToProducersBehaviour(String topicName, DistributerInfo distributerInfo) {
        this.distributerInfo = distributerInfo;
        this.topicName = topicName;
    }

    @Override
    public void action() {
        List<AID> producers = DfHelper.search(myAgent, "producer");

        ACLMessage topicMsg = new ACLMessage(ACLMessage.INFORM);
        topicMsg.setProtocol("invite");

        topicMsg.setContent(topicName + ";" + distributerInfo.getRequestedEnergy() + "," + distributerInfo.getMaxPrice());
        producers.forEach(topicMsg::addReceiver);
        log.info("{} connects producers to topic {}", getAgent().getLocalName(),  myAgent.getLocalName());
        getAgent().send(topicMsg);
    }
}
