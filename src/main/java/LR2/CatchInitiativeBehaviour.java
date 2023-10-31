package LR2;


import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CatchInitiativeBehaviour extends Behaviour {
    @Override
    public void action() {
        ACLMessage receivedMSG = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
        if (receivedMSG != null){
            log.warn("{} catched initiative from {}", myAgent.getLocalName(), receivedMSG.getSender().getLocalName());

            String[] parsedMSD = receivedMSG.getContent().split(",");
            double x = Double.parseDouble(parsedMSD[0]);
            double delta = Double.parseDouble(parsedMSD[1]);

            myAgent.addBehaviour(new InitiateDistributedCalculationBehaviour(x,delta));
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
