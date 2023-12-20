package LR4.DistributerBehaviours;


import LR4.Containers.DistributerInfo;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuctionParallelBehaviour extends ParallelBehaviour {
    private Behaviour wakeupBeh;
    private AuctionFSMBehaviour AuctionBeh;
    private String topicName;
    private AID consumer;
    private long auctionTime;
    private DistributerInfo distInfo;

    public AuctionParallelBehaviour(String topicName, AID consumer, DistributerInfo distInfo, long auctionTime) {
        super(WHEN_ANY);
        this.topicName = topicName;
        this.consumer = consumer;
        this.distInfo = distInfo;
        this.auctionTime = auctionTime;
    }
    @Override
    public void onStart() {

        AuctionBeh = new AuctionFSMBehaviour(topicName, consumer, distInfo, auctionTime);
        wakeupBeh = new WakerBehaviour(myAgent, auctionTime){
            @Override
            protected void onWake() {
                log.info("{} finished auction {} because of timer", myAgent.getLocalName(), topicName);
                ACLMessage badResult = new ACLMessage(ACLMessage.INFORM);
                badResult.setProtocol("result");
                badResult.setContent("Trading wasn't successful");
                myAgent.send(badResult);
            }
        };
        this.addSubBehaviour(AuctionBeh);
        this.addSubBehaviour(wakeupBeh);
    }
}
