package LR3;

import LR3.InitiatorFSM.SendRequestBehaviour;
import LR3.InitiatorFSM.WaitForAnswerParallelBehaviour;
import LR3.NodeFSM.WaitRequestParallelBehaviour;
import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class NodeAgent extends Agent {

    @Override
    protected void setup() {
        DfHelper.register(this, "node");

        int nodePos = Integer.parseInt(this.getLocalName().substring(4));

        String first = XmlHelper.addFirst();
        String last = XmlHelper.addLast();
        List<String> neighbours = XmlHelper.addNeighbours(nodePos);
        List<String> weightings = XmlHelper.addWeightings(nodePos);
        List<String> currentTraceList = new ArrayList<>();
        currentTraceList.add(first);

        log.info("Started {} neighbours are {} weightings are {}", nodePos, neighbours, weightings);

        addBehaviour(new WaitRequestParallelBehaviour());
        addBehaviour(new WaitForAnswerParallelBehaviour());

        if (first.equals(this.getLocalName())){
            log.info("I'm intiator");
            addBehaviour(new SendRequestBehaviour(first, last, currentTraceList, 0));
        }
    }
}
