package UdpHomework;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Starter {
    public static void main(String[] args) {
        Map<String, Class<?>> agents = new HashMap<>();
        for (int i = 1; i <= 3; i++) {
            agents.put("agent_"+i, SuicideAgent.class);
        }

        Properties props = new ExtendedProperties();
        props.setProperty("gui", "false");
        props.setProperty("agents", addAgents(agents));
        props.setProperty("services", addServices(List.of("jade.core.messaging.TopicManagementService")));
        ProfileImpl p = new ProfileImpl(props);

        Runtime.instance().setCloseVM(true);
        Runtime.instance().createMainContainer(p);
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
