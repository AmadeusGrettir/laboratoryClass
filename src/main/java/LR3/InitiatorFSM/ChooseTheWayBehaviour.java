package LR3.InitiatorFSM;

import jade.core.behaviours.OneShotBehaviour;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class ChooseTheWayBehaviour extends OneShotBehaviour {
    HashMap<String, Double> traces;

    public ChooseTheWayBehaviour(HashMap<String, Double> traces) {
        this.traces = traces;
    }

    @Override
    public void action() {
        traces.forEach((key, value) -> log.info("found way {} with weight {}", key, value));

        Optional<Map.Entry<String, Double>> ans = traces.entrySet().stream()
                .sorted((o1, o2) -> o1.getValue() > o2.getValue() ? 1 : -1)
                .findFirst();

        log.info("The shortest way is {} it's weight {}",
                ans.get().getKey(),
                ans.get().getValue());
    }
}
