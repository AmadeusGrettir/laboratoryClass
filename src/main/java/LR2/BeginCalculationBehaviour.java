package LR2;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BeginCalculationBehaviour extends WakerBehaviour {
    public BeginCalculationBehaviour(Agent a, long wakeupTime) {
        super(a, wakeupTime);
    }

    @Override
    protected void onWake() {
        log.info("Let the calculation begin!");
        ACLMessage initiative = new ACLMessage(ACLMessage.INFORM);
        initiative.setContent(0+","+1);
        initiative.addReceiver(new AID(myAgent.getLocalName(), false));
        myAgent.send(initiative);
    }
}
