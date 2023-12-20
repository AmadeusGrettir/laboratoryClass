package LR4;

import LR4.Agents.*;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class JadeStarter {
    public static void main(String[] args) {
        Map<String, Class<?>> agents = new HashMap<>();
        for (int i = 1; i <= 3; i++) {
            agents.put("Consumer"+i, ConsumerAgent.class);
            agents.put("Distributer"+i, DistributerAgent.class);
        }
        agents.put("SolarPowerPlant", ProducerAgent.class);
        agents.put("ThermalPowerPlant", ProducerAgent.class);
        agents.put("WindPowerPlant", ProducerAgent.class);


        Properties props = new ExtendedProperties();
        props.setProperty("gui", "false");
        props.setProperty("agents", addAgents(agents));
        props.setProperty("services", addServices(List.of("jade.core.messaging.TopicManagementService")));
        ProfileImpl p = new ProfileImpl(props);

        Runtime.instance().setCloseVM(true);
        Runtime.instance().createMainContainer(p);

        Timer timer = Timer.getInstance();
    }

    private static String addAgents(Map<String, Class<?>> createAgents){
        String outString = "";
        for (Map.Entry<String, Class<?>> entry : createAgents.entrySet()) {
            outString += entry.getKey()+":"+entry.getValue().getName()+";";
        }
        System.out.println(outString);
        return outString;
    }

    private static String addServices(List<String> services) {
        String outString ="";
        for (String service : services) {
            outString+=service+";";
        }
        return outString;
    }
}
