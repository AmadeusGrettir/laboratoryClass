package LR4.ProducerBehaviours.ProducerFSM;

import jade.core.behaviours.OneShotBehaviour;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BankruptBehaviour extends OneShotBehaviour {
    @Override
    public void action() {
        log.info( "{} has riched final price", getAgent().getLocalName());
    }
}
