package LR4.ProducerBehaviours.ProducerFSM;


import LR4.Containers.ProducerInfo;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SendFirstPriceBehaviour extends OneShotBehaviour {
    private AID topic;
    private double firstPrice;

    public SendFirstPriceBehaviour(ProducerInfo prodInfo, AID topic) {
        this.topic = topic;
        this.firstPrice = prodInfo.getFirstPrice();
    }

    @Override
    public void action() {

        log.info("{} joined auction {} with start price: {}",
                myAgent.getLocalName(), topic.getLocalName(), firstPrice);

        ACLMessage priceMsg = new ACLMessage(ACLMessage.INFORM);
        priceMsg.setProtocol("price");
        priceMsg.addReceiver(topic);

        priceMsg.setContent(""+firstPrice);
        getAgent().send(priceMsg);
    }


}
