package LR4.ConsumerBehaviours;

import LR4.Timer;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
public class SendRequestBehaviour extends Behaviour {
    private int curHour;
    private List energyConsumption;
    private AID distributer;
    private double maxPrice;
    private Timer timer;

    public SendRequestBehaviour(List energyConsumption, double maxPrice, AID distributer) {
        this.timer = Timer.getInstance();
        this.maxPrice = maxPrice;
        this.curHour = timer.getCurHour() - 1;
        this.energyConsumption = energyConsumption;
        this.distributer = distributer;
    }

    @Override
    public void action() {
        int timerTime = timer.getCurHour();
        if (timerTime == 0 && curHour == 23) curHour = -1;
        if (timerTime - curHour > 0) {
            curHour = timerTime;

            if (myAgent.getLocalName().equals("Consumer1")){
                System.out.println();
                System.out.println("/////////////////////////////////////");
                System.out.println("HOUR  " + curHour);
            }

            ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
            request.setProtocol("ConsumerRequest");

            String content = energyConsumption.get(curHour).toString() + "," + maxPrice;
            request.setContent(content);

            log.info("{} sends {} to {}", getAgent().getLocalName(), content, distributer.getLocalName());
            request.addReceiver(distributer);

            getAgent().send(request);
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
