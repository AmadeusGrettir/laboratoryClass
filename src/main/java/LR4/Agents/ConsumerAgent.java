package LR4.Agents;

import LR4.ConsumerBehaviours.GetOrderResultBehaviour;
import LR4.ConsumerBehaviours.SendRequestBehaviour;
import jade.core.AID;
import jade.core.Agent;

import java.util.*;

import LR4.config.XmlUtils;
import LR4.config.ConsumerConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsumerAgent extends Agent {
    @Override
    protected void setup() {

        Optional<ConsumerConfig> parsedCfg = XmlUtils.parse(
                "src/main/resources/LR4resources/"+this.getLocalName()+".xml", ConsumerConfig.class);

        List<Double> energyConsumption = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            energyConsumption.add(parsedCfg.get().getLoadByHour(i));
        }

        double maxPrice = 2.0;

        AID distributor = new AID(parsedCfg.get().getDistributor(), false);

        log.debug("I'm {} and I'm alive", parsedCfg.get().getName());
        log.debug("Power schedule: {}", energyConsumption);


        addBehaviour(new SendRequestBehaviour(energyConsumption, maxPrice, distributor));
        addBehaviour(new GetOrderResultBehaviour());

    }
}
