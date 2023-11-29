package LR3.InitiatorBehaviours;

import LR3.NodeBehaviours.SendAnswerBehaviour;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Data
@Slf4j
public class ReceiveAnswersBehaviour extends Behaviour{
    HashMap<String, Double> traces = new HashMap<>();
    private int count = 0;
    private String first = "";

    @Override
    public void action() {
        ACLMessage answer = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
        if (answer != null) {
            String msg = answer.getContent();
            log.debug(msg);

            String[] detail = msg.split(";");

            first = detail[0];
            String currentTrace = detail[1];
            double weight = Double.valueOf(detail[2]);
            String trace = detail[3];

            if (myAgent.getLocalName().equals(first)) {
                count ++;
                traces.put(trace, weight);
            } else {
                List<String> currentTraceList = new ArrayList<>(Arrays.asList(currentTrace.split(",")));
                myAgent.addBehaviour(new SendAnswerBehaviour(first, currentTraceList, weight, trace));
            }
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}