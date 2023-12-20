package LR4.ProducerBehaviours.ProducerFSM;


import LR4.Containers.ProducerInfo;
import LR4.TopicHelper;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

import static jade.lang.acl.MessageTemplate.*;

@Slf4j
public class SendLowerPriceBehaviour extends Behaviour {
    private ProducerInfo prodInfo;
    private double finalPrice;
    private double step;
    private double requiredPower;
    private AID topic;
    private boolean done;
    private int result;

    public SendLowerPriceBehaviour(ProducerInfo prodInfo, AID topic) {
        this.prodInfo = prodInfo;
        this.topic = topic;
        this.finalPrice = prodInfo.getFinalPrice();
        this.requiredPower = prodInfo.getRequestedEnergy();
        this.done = false;
    }

    @Override
    public void action() {
        ACLMessage priceMsg = myAgent.receive(and(MessageTemplate.MatchProtocol("price"),
                and(not(MessageTemplate.MatchSender(myAgent.getAID())), MessageTemplate.MatchTopic(topic)
                )));

        if (priceMsg != null && prodInfo.getProducerPowerInfo().hasEnoughPower(requiredPower)) {

            double price = Double.valueOf(priceMsg.getContent());

            step = finalPrice / 10;

            if (price < prodInfo.getCurrentPrice()){
                double newPrice = price - step;
                if (newPrice > finalPrice){
                    prodInfo.setCurrentPrice(newPrice);
                    sendPrice(newPrice);
                } else {
                    result = 1;
                    done = true;
                }
            }
        } else if (!prodInfo.getProducerPowerInfo().hasEnoughPower(requiredPower)) {
            result = 0;
            done = true;
        } else {
            block();
        }
    }

    @Override
    public int onEnd() {
        TopicHelper.deregister(myAgent, topic);
        return result;
    }

    @Override
    public boolean done() {
        return done;
    }

    private void sendPrice(double price) {
        log.debug("{} sends to {} data {}", getAgent().getLocalName(), topic.getLocalName(), price);

        ACLMessage priceMsg = new ACLMessage(ACLMessage.INFORM);
        priceMsg.setProtocol("price");
        priceMsg.addReceiver(topic);

        priceMsg.setContent(""+price);
        getAgent().send(priceMsg);
    }
}
