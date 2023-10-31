package LR2;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class FunctionAgent extends Agent {


    @Override
    protected void setup() {

        System.out.println("Started "+this.getLocalName());

        this.addBehaviour(new CalcMyFunction());
        this.addBehaviour(new CatchInitiative());

        if (this.getLocalName().equals("Agent1")){
            System.out.println("started");
            ACLMessage initiative = new ACLMessage(ACLMessage.INFORM);
            initiative.setContent(0+","+0.1);
            initiative.addReceiver(new AID(this.getLocalName(), false));
            this.send(initiative);
        }
    }
}
