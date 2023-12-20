package LR4;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.messaging.TopicManagementHelper;
import lombok.SneakyThrows;

public class TopicHelper {

    @SneakyThrows
    public static AID register(Agent a, String topicName){
        TopicManagementHelper tmh = (TopicManagementHelper) a.getHelper(TopicManagementHelper.SERVICE_NAME);
        AID topic = tmh.createTopic(topicName);
        tmh.register(topic);
        return topic;
    }

    @SneakyThrows
    public static void deregister(Agent a, AID jadeTopic) {
        TopicManagementHelper tmh = (TopicManagementHelper) a.getHelper(TopicManagementHelper.SERVICE_NAME);
        tmh.deregister(jadeTopic);
    }

}
