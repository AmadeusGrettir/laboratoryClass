package LR4.Agents;

import LR3.DfHelper;
import LR4.Containers.ProducerPowerInfo;
import LR4.ProducerBehaviours.WaitAuctionBehaviour;
import LR4.ProducerBehaviours.WaitConfirmBehaviour;
import LR4.config.PlantsConfig.*;
import LR4.config.XmlUtils;
import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ProducerAgent extends Agent {
    @Override
    protected void setup() {
        Generation parsedCfg = getConfig(this.getLocalName());
        log.debug("I'm {} and I'm alive", this.getLocalName());
        DfHelper.register(this, "producer");

        List<Double> energyGeneration = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            energyGeneration.add(parsedCfg.getPowerByHour(i));
        }

        log.debug("Power schedule: {}", energyGeneration);
        ProducerPowerInfo producerInfo = new ProducerPowerInfo();
        producerInfo.setEnergyGeneration(energyGeneration);

        addBehaviour(new WaitAuctionBehaviour(producerInfo));
    }

    private Generation getConfig(String localName) {
        switch (localName) {
            case "ThermalPowerPlant":
                Optional<ThermalPowerPlantConfig> parsedThermalCfg = XmlUtils.parse(
                        "src/main/resources/LR4resources/ThermalPowerPlant.xml", ThermalPowerPlantConfig.class);
                return parsedThermalCfg.get();
            case "WindPowerPlant":
                Optional<WindPowerPlantConfig> parsedWindCfg = XmlUtils.parse(
                        "src/main/resources/LR4resources/WindPowerPlant.xml", WindPowerPlantConfig.class);
                return parsedWindCfg.get();
            case "SolarPowerPlant":
                Optional<SolarPowerPlantConfig> parsedSolarCfg = XmlUtils.parse(
                        "src/main/resources/LR4resources/SolarPowerPlant.xml", SolarPowerPlantConfig.class);
                return parsedSolarCfg.get();
        }
        return null;
    }
}
