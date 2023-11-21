package LR3.InitiatorFSM;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;


@Slf4j
public class WaitForAnswerParallelBehaviour extends ParallelBehaviour {
    private Behaviour wakeupBeh;
    private ReceiveAnswersBehaviour receiveBeh;

    public WaitForAnswerParallelBehaviour() {
        super(WHEN_ANY);
    }

    @Override
    public void onStart() {
        receiveBeh = new ReceiveAnswersBehaviour();
        wakeupBeh = new WakerBehaviour(myAgent, 10000){
            @Override
            protected void onWake() {
                if (myAgent.getLocalName().equals(receiveBeh.getFirst())){
                    log.warn("CALCULATION IS ENDED");
                }
            }
        };
        this.addSubBehaviour(receiveBeh);
        this.addSubBehaviour(wakeupBeh);
    }

    @Override
    public int onEnd() {
        if (receiveBeh.getCount() == 0){
            if (myAgent.getLocalName().equals(receiveBeh.getFirst())){
                log.warn("No way found");
            }

            return 0;
        } else{
            HashMap<String, Double> traces = receiveBeh.getTraces();
            myAgent.addBehaviour(new ChooseTheWayBehaviour(traces));
            return 1;
        }
    }
}
