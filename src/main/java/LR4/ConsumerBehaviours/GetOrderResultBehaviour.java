package LR4.ConsumerBehaviours;

import LR4.Containers.ResultsInfo;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class GetOrderResultBehaviour extends Behaviour {
    private ResultsInfo resultsInfo;

    public GetOrderResultBehaviour(ResultsInfo resultsInfo){
        this.resultsInfo = resultsInfo;
        resultsInfo.setResults(new ArrayList<>());
    }
    public GetOrderResultBehaviour(){
        this.resultsInfo = new ResultsInfo();
        resultsInfo.setResults(new ArrayList<>());
    }


    @Override
    public void action() {
        ACLMessage result = myAgent.receive(MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                MessageTemplate.MatchProtocol("result")));
        if (result != null) {
            if (result.getContent().equals("Trading wasn't successful") ||
                    result.getContent().equals("There is no appropriate price")){
                log.warn(result.getContent());
            } else {
                String content = result.getContent();
                String[] parsContent = content.split(";");
                double requestedEnergy = Double.valueOf(parsContent[1]);
                double bestPrice = Double.valueOf(parsContent[0]);
                String producer = parsContent[2];
                log.warn("{} received order result: bought {} kWh for {} Krub/kWh from producer: {}",
                        myAgent.getLocalName(),
                        requestedEnergy,
                        bestPrice,
                        producer
                );
                resultsInfo.getResults().add(bestPrice);
            }

        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
