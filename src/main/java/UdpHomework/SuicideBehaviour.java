package UdpHomework;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SuicideBehaviour extends WakerBehaviour {
    AgentDetector detector;

    public SuicideBehaviour(Agent a, long wakeupDate, AgentDetector detector) {
        super(a, wakeupDate);
        this.detector = detector;
    }

    @Override
    protected void onWake() {
        detector.stopPublishing();
    }
}
