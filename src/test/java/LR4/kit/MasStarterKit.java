package LR4.kit;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.Behaviour;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import lombok.SneakyThrows;

import java.util.Collections;
import java.util.List;

public class MasStarterKit {
    private AgentContainer mainContainer;

    public void startJade(List<String> services) {
        Properties props = new Properties();
        props.setProperty("gui", "false");
        props.setProperty("services", addServices(services));
        ProfileImpl p = new ProfileImpl(props);
        Runtime.instance().setCloseVM(true);
        mainContainer = Runtime.instance().createMainContainer(p);
    }

    public void startJade() {
        this.startJade(Collections.emptyList());
    }

    @SneakyThrows
    public void createAgent(String agentName, Behaviour... beh){
        AgentController newAgent = mainContainer.createNewAgent(agentName, AgentTester.class.getName(), beh);
        newAgent.start();
    }

    private String addServices(List<String> services) {
        String outString ="";
        for (String service : services) {
            outString+=service+";";
        }
        return outString;
    }

}
