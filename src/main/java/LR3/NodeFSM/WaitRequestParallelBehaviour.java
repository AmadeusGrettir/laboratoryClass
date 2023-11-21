package LR3.NodeFSM;

import LR3.InitiatorFSM.SendRequestBehaviour;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class WaitRequestParallelBehaviour extends ParallelBehaviour {
    private Behaviour wakeupBeh;
    private ReceiveRequestBehaviour receiveBeh;

    public WaitRequestParallelBehaviour() {
        super(WHEN_ANY);
    }

    @Override
    public void onStart() {
        receiveBeh = new ReceiveRequestBehaviour();
        wakeupBeh = new WakerBehaviour(myAgent, 5000){
            @Override
            protected void onWake() {
                super.onWake();
            }
        };
        this.addSubBehaviour(receiveBeh);
        this.addSubBehaviour(wakeupBeh);
    }

    @Override
    public int onEnd() {
        //0 -> запрос соседям, 1 -> отправка ответа, 2 -> закончить поведение
        if (wakeupBeh.done()) {
            return 2;
        } else {
            String first = receiveBeh.getFirst();
            String last = receiveBeh.getLast();
            double weight = receiveBeh.getWeight();
            String trace = receiveBeh.getTrace();
            List<String> currentTraceList = new ArrayList<>(Arrays.asList(trace.split(",")));

            if (receiveBeh.onEnd() == 0) {
                myAgent.addBehaviour(new WaitRequestParallelBehaviour());
                myAgent.addBehaviour(new SendRequestBehaviour(first, last, currentTraceList, weight));
                return 0;

            } else if (receiveBeh.onEnd() == 1){
                myAgent.addBehaviour(new WaitRequestParallelBehaviour());
                currentTraceList.remove(currentTraceList.size() - 1);
                myAgent.addBehaviour(new SendAnswerBehaviour(first, currentTraceList, weight, trace));
                return 1;

            } else if (receiveBeh.onEnd() == 2){
                return 2;
            }
        }
        return 0;
    }
}
