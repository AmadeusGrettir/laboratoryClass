package LR4.Agents;

import LR4.DistributerBehaviours.ReceiveConsumerRequestBehaviour;
import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DistributerAgent extends Agent {
    @Override
    protected void setup() {
        log.debug("I'm {} and I'm alive", this.getLocalName());
        addBehaviour(new ReceiveConsumerRequestBehaviour());
    }
}
