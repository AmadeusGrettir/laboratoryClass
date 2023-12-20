package LR4.DistributerBehaviours.AuctionFSM;

import LR4.Containers.DistributerInfo;
import LR4.DistributerBehaviours.AuctionFSMBehaviour;
import LR4.DistributerBehaviours.AuctionParallelBehaviour;
import LR4.Timer;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DivideEnergyBehaviour extends ParallelBehaviour {

    private AuctionParallelBehaviour AuctionBeh1;
    private AuctionParallelBehaviour AuctionBeh2;
    private DistributerInfo distributerInfo;
    private final Timer timer = Timer.getInstance();


    public DivideEnergyBehaviour(DistributerInfo distInfo) {
        super(WHEN_ALL);
        this.distributerInfo = distInfo;
    }

    public void onStart() {
        log.info("Request {} is divided", myAgent.getLocalName());
        AuctionBeh1 = new AuctionParallelBehaviour(myAgent.getLocalName()+"Div1",
                distributerInfo.getConsumer(),
                distributerInfo, timer.ConditionTime(0.4));
        AuctionBeh2 = new AuctionParallelBehaviour(myAgent.getLocalName()+"Div2",
                distributerInfo.getConsumer(),
                distributerInfo, timer.ConditionTime(0.4));
        this.addSubBehaviour(AuctionBeh1);
        this.addSubBehaviour(AuctionBeh2);
    }
}
