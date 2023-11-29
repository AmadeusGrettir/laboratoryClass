package LR3;

import LR3.InitiatorBehaviours.SendRequestBehaviour;
import LR3.InitiatorBehaviours.WaitForAnswerParallelBehaviour;
import LR3.NodeBehaviours.WaitRequestParallelBehaviour;
import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class NodeAgent extends Agent {

    @Override
    protected void setup() {
        DfHelper.register(this, "node");

        int nodePos = Integer.parseInt(this.getLocalName().substring(4));

        String first = ParametersHelper.addFirst();
        String last = ParametersHelper.addLast();
        List<String> neighbours = ParametersHelper.addNeighbours(nodePos);
        List<String> weightings = ParametersHelper.addWeightings(nodePos);
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
