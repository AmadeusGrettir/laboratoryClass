package LR3.NodeBehaviours;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.List;


public class SendAnswerBehaviour extends OneShotBehaviour {
    private String first;
    private double weight;
    private String trace;
    private List<String> currentTraceList;

    public SendAnswerBehaviour(String first, List<String> currentTraceList, double weight, String trace) {
        this.first = first;
        this.weight = weight;
        this.trace = trace;
        this.currentTraceList = currentTraceList;
    }
    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setProtocol("FindBridges");

        String currentNode = currentTraceList.get(currentTraceList.size()-1);
        currentTraceList.remove(currentTraceList.size() - 1);
        String currentTrace = String.join(",", currentTraceList);

        msg.setContent(first+";"+currentTrace+";"+weight+";"+trace);
        msg.addReceiver(new AID(currentNode, false));
        myAgent.send(msg);
    }
}
