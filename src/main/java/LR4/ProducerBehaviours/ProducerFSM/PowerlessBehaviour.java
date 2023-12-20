package LR4.ProducerBehaviours.ProducerFSM;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PowerlessBehaviour extends OneShotBehaviour {
    private AID topic;
    public PowerlessBehaviour(AID topic) {
        this.topic = topic;
    }

    @Override
    public void action() {
        log.info("{} can't trade with {} due to lack of power", getAgent().getLocalName(), topic.getLocalName());
        ACLMessage refuse = new ACLMessage(ACLMessage.REFUSE);
        refuse.setProtocol("Refuse trading");
        refuse.setContent(getAgent().getLocalName());
        refuse.addReceiver(topic);
        getAgent().send(refuse);
    }
}
