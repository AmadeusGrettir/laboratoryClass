package LR2;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InitiateDistributedCalculationBehaviour extends Behaviour {

    private double delta;
    private final double x;

    public InitiateDistributedCalculationBehaviour(double x, double delta) {
        this.x = x;
        this.delta = delta;
    }

    private final double[] points = new double[3];

    @Override
    public void onStart() {

        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);

        points[0] = x-delta;
        points[1] = x;
        points[2] = x+delta;
        request.setContent(points[0] +","+ points[1] +","+ points[2]);

        request.addReceiver(new AID("Agent1", false));
        request.addReceiver(new AID("Agent2", false));
        request.addReceiver(new AID("Agent3", false));
        myAgent.send(request);
    }

    private final double[] results = new double[] {0,0,0};
    private int count = 0;

    @Override
    public void action() {

        ACLMessage receive = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
        if (receive != null){
            log.debug("Answer received from: {} ", receive.getSender().getLocalName());
            String[] res = receive.getContent().split(",");
            for (int i = 0; i < results.length; i++) {
                results[i] += Double.parseDouble(res[i]);
            }

            count++;
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return count == 3;
    }

    @Override
    public int onEnd() {
        double minY = results[0];
        double minX = points[0];
        for (int i = 1; i < results.length; i++) {
            if (results[i]<minY){
                minY = results[i];
                minX = points[i];
            }
        }

        double demandDelta = 0.001;
        if (delta <= demandDelta){
            String resultInfo = String.format("That's all, min = (%5.3f, %5.3f)", minX, minY);
            log.info(resultInfo);
        } else {
            ACLMessage initiative = new ACLMessage(ACLMessage.INFORM);
            if (minX == x){
                delta /= 2;
                initiative.addReceiver(new AID(myAgent.getLocalName(), false));
            } else {
                int agentNum = Integer.parseInt(String.valueOf(myAgent.getLocalName().charAt(5)))+1;
                if(agentNum == 4){
                    agentNum = 1;
                }
                String newAgent = "Agent" + agentNum;
                initiative.addReceiver(new AID(newAgent, false));
            }
            initiative.setContent(minX+","+delta);
            myAgent.send(initiative);
        }
        return 0;
    }
}
