package LR4.DistributerBehaviours;

import LR4.Containers.DistributerInfo;
import LR4.TopicHelper;
import LR4.DistributerBehaviours.AuctionFSM.*;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;

import java.util.HashMap;
import java.util.Map;


public class AuctionFSMBehaviour extends FSMBehaviour {
    private final String SEND_REQUEST = "SEND_REQUEST";
    private final String COLLECT_PRICES = "COLLECT_PRICES";
    private final String CHOOSE_BEST = "CHOOSE_BEST";
    private final String DIVIDE_ENERGY = "DIVIDE_ENERGY";
    private final String RESPOND_TO_CONSUMER = "RESPOND_TO_CONSUMER";
    private final String CONFIRM_DEAL = "CONFIRM_DEAL";
    private final String BAD_ENDING = "BAD_ENDING";
    private final String HIGH_PRICE = "HIGH_PRICE";
    private final String ONE_MORE = "ONE_MORE";

    private String topicName;
    private AID consumer;
//    private String consRequest;
    private long auctionTime;
    private DistributerInfo distInfo;


    public AuctionFSMBehaviour(String topicName, AID consumer, DistributerInfo distInfo, long auctionTime) {
        this.topicName = topicName;
        this.consumer = consumer;
        this.distInfo = distInfo;
        this.auctionTime = auctionTime;
    }

    @Override
    public void onStart() {
        AID topic = TopicHelper.register(myAgent, topicName);

        Map<AID, Double> prices = new HashMap<>();
        long collectingTime = auctionTime / 4;
        long confirmTime = auctionTime / 10;


        registerFirstState(new SendRequestToProducersBehaviour(topicName, distInfo), SEND_REQUEST);
        registerState(new ReceivePricesParallelBehaviour(prices, collectingTime, topic), COLLECT_PRICES);
        registerState(new ChooseTheBestPriceBehaviour(prices, distInfo, topic), CHOOSE_BEST);
        registerState(new ConfirmDealParallelBehaviour(distInfo, confirmTime), CONFIRM_DEAL);


        registerLastState(new DivideEnergyBehaviour(distInfo), DIVIDE_ENERGY);
        registerLastState(new OneMoreTimeBehaviour(distInfo, collectingTime), ONE_MORE);
        registerLastState(new BadEndingBehaviour(consumer), BAD_ENDING);
        registerLastState(new ResponseToConsumerBehaviour(distInfo, consumer), RESPOND_TO_CONSUMER);
        registerLastState(new HighPriceBehaviour(consumer), HIGH_PRICE);



        registerDefaultTransition(SEND_REQUEST, COLLECT_PRICES);
        registerDefaultTransition(COLLECT_PRICES, CHOOSE_BEST);
        registerTransition(CHOOSE_BEST, DIVIDE_ENERGY, 0);
        registerTransition(CHOOSE_BEST, CONFIRM_DEAL, 1);
        registerTransition(CHOOSE_BEST, HIGH_PRICE, 2);
        registerTransition(CHOOSE_BEST, BAD_ENDING, 3);


        registerTransition(CONFIRM_DEAL, ONE_MORE, 0);
        registerTransition(CONFIRM_DEAL, RESPOND_TO_CONSUMER, 1);

    }
}
