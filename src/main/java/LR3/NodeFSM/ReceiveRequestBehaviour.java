package LR3.NodeFSM;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import LR3.XmlHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Slf4j
public class ReceiveRequestBehaviour extends Behaviour{
    //по умолчанию отправляет запрос соседям
    private int crossroad = 0;

    //чтобы исключить отправителя из отправки нового запроса
    private double weight;
    private String first;
    private String last;
    private String trace;

    private boolean recieved = false;
    @Override
    public void action() {
        ACLMessage request = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
        if (request != null) {
            recieved = true;
            String msg = request.getContent();
            log.info(msg);

            String[] detail = msg.split(";");
            first = detail[0];
            last = detail[1];
            weight = Double.valueOf(detail[2]);
            trace = detail[3]+","+myAgent.getLocalName();

            String sender = request.getSender().getLocalName();
            int nodePos = Integer.parseInt(myAgent.getLocalName().substring(4));
            int senderPos = Integer.parseInt(sender.substring(4));
            weight += XmlHelper.getWeighting(senderPos,nodePos);

            List<String> currentTraceList = new ArrayList<>(Arrays.asList(trace.split(",")));
            currentTraceList.remove(currentTraceList.size() - 1);

            if (myAgent.getLocalName().equals(last)){
                crossroad = 1;
            } else if (currentTraceList.contains(myAgent.getLocalName())){
                crossroad = 2;
            }
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return recieved;
    }

    @Override
    public int onEnd() {
        return crossroad;
    }

}
