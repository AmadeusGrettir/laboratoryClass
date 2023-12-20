package LR4.DistributerBehaviours;

import LR4.Containers.DistributerInfo;
import LR4.Timer;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReceiveConsumerRequestBehaviour extends Behaviour {
    private final Timer timer = Timer.getInstance();
    private String consRequest;
    private AID consumer;
    private AuctionParallelBehaviour auction;

    public ReceiveConsumerRequestBehaviour() {
    }


    @Override
    public void action() {
        ACLMessage request = myAgent.receive(MessageTemplate.MatchProtocol("ConsumerRequest"));
        if (request != null) {
            String content = request.getContent();
            log.info("Got {} from Consumer", content);
            consRequest = content;
            consumer = request.getSender();

            myAgent.addBehaviour(new WakerBehaviour(getAgent(), 10L) {
                @Override
                protected void onWake() {

                    String[] consumerRequest = consRequest.split(",");
                    double maxPrice = Double.valueOf(consumerRequest[1]);
                    double RequestedEnergy = Double.valueOf(consumerRequest[0]);

                    DistributerInfo distInfo = new DistributerInfo();
                    distInfo.setMaxPrice(maxPrice);
                    distInfo.setRequestedEnergy(RequestedEnergy);
                    distInfo.setDivided(false);
                    distInfo.setCountDivision(2);
                    distInfo.setConsumer(consumer);

                    auction = new AuctionParallelBehaviour(myAgent.getLocalName(), consumer, distInfo, timer.ConditionTime(0.8));
                    myAgent.addBehaviour(auction);
                }
            });
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
