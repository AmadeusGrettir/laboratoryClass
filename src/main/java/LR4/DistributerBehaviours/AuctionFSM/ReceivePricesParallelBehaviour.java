package LR4.DistributerBehaviours.AuctionFSM;


import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;


@Slf4j
public class ReceivePricesParallelBehaviour extends ParallelBehaviour {
    private Behaviour wakeupBeh;
    private ReceivePricesBehaviour ReceivePricesBeh;
    private Map<AID, Double> prices;
    private long collectingTime;
    private AID topic;


    public ReceivePricesParallelBehaviour(Map<AID, Double> prices, long collectingTime, AID topic) {
        super(WHEN_ANY);
        this.prices = prices;
        this.topic = topic;
        this.collectingTime = collectingTime;
    }
    @Override
    public void onStart() {
        ReceivePricesBeh = new ReceivePricesBehaviour(prices, topic);
        wakeupBeh = new WakerBehaviour(myAgent, collectingTime){
            @Override
            protected void onWake() {
                log.info("{} received prices {}", myAgent.getLocalName(), prices.values());
            }
        };
        this.addSubBehaviour(ReceivePricesBeh);
        this.addSubBehaviour(wakeupBeh);
    }
}
