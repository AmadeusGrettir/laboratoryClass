package LR4.DistributerBehaviours.AuctionFSM;

import LR4.Containers.DistributerInfo;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ConfirmDealParallelBehaviour extends ParallelBehaviour {
    private Behaviour wakeupBeh;
    private ConfirmDealBehaviour receiveBeh;

    private DistributerInfo distInfo;
    private long confirmTime;

    public ConfirmDealParallelBehaviour(DistributerInfo distInfo, long confirmTime) {
        super(WHEN_ANY);
        this.confirmTime = confirmTime;
        this.distInfo = distInfo;
    }

    @Override
    public void onStart() {
        receiveBeh = new ConfirmDealBehaviour();
        wakeupBeh = new WakerBehaviour(myAgent, confirmTime){
            boolean wake = false;
            @Override
            protected void onWake() {
                wake = true;
            }

            @Override
            public int onEnd() {
                return wake ? 0 : 1;
            }
        };

        ACLMessage successMSG = new ACLMessage(ACLMessage.AGREE);
        successMSG.setProtocol("confirm");
        successMSG.addReceiver(distInfo.getBestProducer());
        myAgent.send(successMSG);

        this.addSubBehaviour(receiveBeh);
        this.addSubBehaviour(wakeupBeh);
    }

    @Override
    public int onEnd() {
        if (wakeupBeh.done()) {
            if (receiveBeh.onEnd() == 0) {
                log.info("No appropriate answer was received");
                return 0;
            } else {
                return 1;
            }
        } else {
            if (receiveBeh.onEnd() == 1) {
                log.info("Contract is confirmed");
                return  1;
            } else {
                log.info("Contract NOT confirmed");
                return 0;
            }
        }
    }
}

