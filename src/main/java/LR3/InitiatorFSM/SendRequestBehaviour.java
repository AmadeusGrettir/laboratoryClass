package LR3.InitiatorFSM;

import LR3.XmlHelper;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class SendRequestBehaviour extends OneShotBehaviour {
    private String first;
    private String last;
    private List<String> currentTraceList;
    private double weight;

    public SendRequestBehaviour(String first, String last, List<String> currentTraceList, double weight) {
        this.first = first;
        this.last = last;
        this.currentTraceList = currentTraceList;
        this.weight = weight;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.setProtocol("FindBridges");

        int nodePos = Integer.parseInt(myAgent.getLocalName().substring(4));
        List<String> neighbours = XmlHelper.addNeighbours(nodePos);

        Iterator<String> neighboursIterator = neighbours.iterator();
        while(neighboursIterator.hasNext()) {
            String next = neighboursIterator.next();
            if (currentTraceList.contains(next)) {
                neighboursIterator.remove();
            }
        }
        String currentTrace = String.join(",", currentTraceList);

        msg.setContent(first+";"+last+";"+weight+";"+currentTrace);
        neighbours.forEach(e -> msg.addReceiver(new AID(e, false)));
        myAgent.send(msg);
    }
}
