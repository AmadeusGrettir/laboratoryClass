package LR2;


import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class CatchInitiative extends Behaviour {
    @Override
    public void action() {
        ACLMessage receive = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
        if (receive != null){
            System.out.println(myAgent.getLocalName() + " catched initiative from " + receive.getSender().getLocalName());
            String[] res = receive.getContent().split(",");
            double x = Double.parseDouble(res[0]);
            double delta = Double.parseDouble(res[1]);

            myAgent.addBehaviour(new InitiateDistributedCalculation(x,delta));
        } else {
            block();
        }


    }

    @Override
    public boolean done() {
        return false;
    }
}
