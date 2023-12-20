package LR4.DistributerBehaviours.AuctionFSM;

import LR4.Containers.DistributerInfo;
import LR4.TopicHelper;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;

@Slf4j
public class ChooseTheBestPriceBehaviour extends OneShotBehaviour {
    private Map<AID, Double> prices;
    private AID topic;
    private double maxPrice;
    private int result;
    private DistributerInfo distInfo;


    public ChooseTheBestPriceBehaviour(Map<AID, Double> prices, DistributerInfo distInfo, AID topic) {
        this.prices = prices;
        this.topic = topic;
        this.maxPrice = distInfo.getMaxPrice();
        this.distInfo = distInfo;
    }

    @Override
    public void action() {

        Optional<Map.Entry<AID, Double>> minPrice = prices.entrySet().stream()
                .sorted((o1, o2) -> o1.getValue() > o2.getValue() ? 1 : -1)
                .findFirst();


        if (minPrice.isPresent()) {
            if (minPrice.get().getValue() > maxPrice) {
                log.debug("Prices are too high!");
                result = 2;
            } else {
                log.debug("Best price is {} from {}", minPrice.get().getValue(), minPrice.get().getKey().getLocalName());
                distInfo.setBestPrice(minPrice.get().getValue());
                distInfo.setBestProducer(minPrice.get().getKey());
                result = 1;
            }
        } else {
            log.debug("There's no proper offer");
//            result = 3;
            if (!distInfo.isDivided()){
                distInfo.setDivided(true);
                double newRequestedEnergy = distInfo.getRequestedEnergy() / 2;
                distInfo.setRequestedEnergy(newRequestedEnergy);
                result = 0;
            } else {
                result = 3;
            }
        }
    }

    @Override
    public int onEnd() {
        TopicHelper.deregister(getAgent(), topic);
        return result;
    }
}
