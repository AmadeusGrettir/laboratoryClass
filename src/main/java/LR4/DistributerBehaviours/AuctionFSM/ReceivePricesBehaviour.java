package LR4.DistributerBehaviours.AuctionFSM;


import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;

import static jade.lang.acl.MessageTemplate.*;

@Slf4j
public class ReceivePricesBehaviour extends Behaviour {
    private Map<AID, Double> prices;
    private AID topic;

    public ReceivePricesBehaviour(Map<AID, Double> prices, AID topic) {
        this.prices = prices;
        this.topic = topic;
    }

    @Override
    public void action() {
        ACLMessage priceMsg = myAgent.receive(and(MessageTemplate.MatchProtocol("price"),
                MessageTemplate.MatchTopic(topic)));
        if (priceMsg != null) {
            double price = Double.valueOf(priceMsg.getContent());

            log.debug("{} got: {} from {}", myAgent.getLocalName(), price, priceMsg.getSender().getLocalName());
            prices.put(priceMsg.getSender(), price);
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
