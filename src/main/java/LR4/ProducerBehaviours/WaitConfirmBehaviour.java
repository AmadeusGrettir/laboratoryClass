package LR4.ProducerBehaviours;

import LR4.Containers.ProducerInfo;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitConfirmBehaviour extends Behaviour {
    ProducerInfo producerInfo;
    private boolean done = false;

    public WaitConfirmBehaviour(ProducerInfo producerInfo){
        this.producerInfo = producerInfo;
    }

    @Override
    public void action() {
        ACLMessage agr = myAgent.receive(MessageTemplate.MatchProtocol("confirm"));
        if (agr != null) {
            myAgent.addBehaviour(new SendContractBehaviour(producerInfo, agr.getSender()));
            done = true;
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return done;
    }
}

