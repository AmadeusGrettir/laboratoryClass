package LR4.ProducerBehaviours;

import LR4.Containers.ProducerInfo;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SendContractBehaviour extends OneShotBehaviour {

    private ProducerInfo producerInfo;
    private AID sender;

    public SendContractBehaviour(ProducerInfo producerInfo, AID sender){
        this.producerInfo = producerInfo;
        this.sender = sender;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID(sender.getLocalName(), false));
        msg.setProtocol("contract");
        log.warn("My current energy is {}", producerInfo.getProducerPowerInfo().getCurrentEnergy());
        log.warn("My requested energy is {} from {}", producerInfo.getRequestedEnergy(), sender.getLocalName());

        if (producerInfo.getProducerPowerInfo().getCurrentEnergy() < producerInfo.getRequestedEnergy()) {
            msg.setContent("refuse");
        } else {

            producerInfo.getProducerPowerInfo().setCurrentEnergy(producerInfo.getProducerPowerInfo().getCurrentEnergy() - producerInfo.getRequestedEnergy());
            msg.setContent("accept");
        }
        myAgent.send(msg);
    }

}
