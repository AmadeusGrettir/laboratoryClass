package LR2;

import LR2.functions.Function1;
import LR2.functions.Function;
import LR2.functions.Function2;
import LR2.functions.Function3;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.HashMap;

public class CalcMyFunction extends Behaviour {
    @Override
    public void action() {
        HashMap<String, Function> functions = new HashMap<>();
        functions.put("Agent1", new Function1());
        functions.put("Agent2", new Function2());
        functions.put("Agent3", new Function3());

        ACLMessage receive = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
        if (receive != null){
            System.out.println("request to " + myAgent.getLocalName());
            Function function = functions.get(myAgent.getLocalName());

            String[] arrayX = receive.getContent().split(",");

            double[] arrayY = new double[] {0,0,0};
            for (int i = 0; i < arrayX.length; i++) {

                double x = Double.parseDouble(arrayX[i]);
                arrayY[i] = function.calc(x);
            }

            ACLMessage answer = new ACLMessage(ACLMessage.CONFIRM);
            answer.setContent(arrayY[0]+","+arrayY[1]+","+arrayY[2]);
            answer.addReceiver(new AID(receive.getSender().getLocalName(), false));
            myAgent.send(answer);

        } else {
            block();
        }

    }

    @Override
    public boolean done() {
        return false;
    }
}
