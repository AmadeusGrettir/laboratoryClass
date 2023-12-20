package LR4.ProducerBehaviours;

import LR4.Containers.ProducerInfo;
import LR4.Containers.ProducerPowerInfo;
import LR4.Timer;
import LR4.TopicHelper;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class WaitAuctionBehaviour extends Behaviour {
    private ProducerPowerInfo producerPowerInfo;
    Timer timer = Timer.getInstance();

    public WaitAuctionBehaviour(ProducerPowerInfo producerInfo) {
        this.producerPowerInfo = producerInfo;
    }

    @Override
    public void action() {
        ACLMessage request = myAgent.receive(MessageTemplate.MatchProtocol("invite"));
        if (request != null) {
            String msg = request.getContent();
            log.debug("{} got invitation msg {}", myAgent.getLocalName(), request.getContent());

            String[] detail = msg.split(";");
            String topicName = detail[0];
            String[] consRequest = detail[1].split(",");
            double RequestedEnergy = Double.valueOf(consRequest[0]);

            ProducerInfo producerInfo = new ProducerInfo();
            producerInfo.setProducerPowerInfo(producerPowerInfo);

            List<Double> energyGeneration = producerInfo.getProducerPowerInfo().getEnergyGeneration();
            double GeneratedEnergy = energyGeneration.get(timer.getCurHour());
            producerInfo.getProducerPowerInfo().setCurrentEnergy(GeneratedEnergy);
            producerInfo.setRequestedEnergy(RequestedEnergy);


            if (RequestedEnergy <= GeneratedEnergy) {
                log.warn("{} joined the auction of {}",myAgent.getLocalName(), topicName);

                AID topic = TopicHelper.register(myAgent, topicName);
                double finalPrice = getFinalPrice(myAgent.getLocalName(), GeneratedEnergy);
                producerInfo.setFinalPrice(finalPrice);
                producerInfo.setCurrentPrice(finalPrice * 2);
                myAgent.addBehaviour(new ProducerFSMBehaviour(producerInfo, topic));
                myAgent.addBehaviour(new WaitConfirmBehaviour(producerInfo));
            } else {
                log.debug("{} doesn't have enough power: {}", myAgent.getLocalName(), GeneratedEnergy);
            }
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }

    private double getFinalPrice(String localName, double GeneratedEnergy) {
        switch (localName) {
            case "ThermalPowerPlant":
                return 0.8;
            case "WindPowerPlant":
                return 1 / GeneratedEnergy * 25;
            case "SolarPowerPlant":
                return 1 / GeneratedEnergy * 100;
        }
        return 0;
    }
}
