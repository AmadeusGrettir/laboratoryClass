package LR4.DistributerBehaviours.AuctionFSM;

import LR4.Containers.DistributerInfo;
import LR4.DistributerBehaviours.AuctionParallelBehaviour;
import jade.core.behaviours.OneShotBehaviour;

public class OneMoreTimeBehaviour extends OneShotBehaviour {
    private DistributerInfo distInfo;
    private long auctionDuration;
    public OneMoreTimeBehaviour(DistributerInfo distInfo, long auctionDuration) {
        this.distInfo = distInfo;
        this.auctionDuration = auctionDuration;
    }

    @Override
    public void action() {
        myAgent.addBehaviour(new AuctionParallelBehaviour(myAgent.getLocalName()+"Again",
                distInfo.getConsumer(),
                distInfo, auctionDuration));
    }
}
