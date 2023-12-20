package LR3;

import LR3.config.NodeConfig;
import LR3.config.XmlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ParametersHelper {
    private static Optional<NodeConfig> parsedCfg = XmlUtils.parse("src/main/resources/LR3resources/config14.xml", NodeConfig.class);

    public static double getWeight(int senderPos, int nodePos){
        List<String> we = new ArrayList<>();
        parsedCfg.ifPresent(cfg ->
                we.addAll(cfg
                        .getNodeList()
                        .get(senderPos - 1)
                        .getNeighbourList()
                        .stream()
                        .filter(s -> s.getPosition()==(nodePos))
                        .map(s -> ""+s.getWeighting())
                        .collect(Collectors.toList())
                ));
        return Double.valueOf(we.get(0));
    }

    public static List<String> addWeightings(int nodePos){
        List<String> weightings = new ArrayList<>();
        parsedCfg.ifPresent(cfg ->
                weightings.addAll(cfg
                        .getNodeList()
                        .get(nodePos - 1)
                        .getNeighbourList()
                        .stream()
                        .map(s -> ""+s.getWeighting())
                        .collect(Collectors.toList()))
        );
        return weightings;
    }

    public static List<String> addNeighbours(int nodePos){
        List<String> neighbours = new ArrayList<>();
        parsedCfg.ifPresent(cfg ->
                neighbours.addAll(cfg
                        .getNodeList()
                        .get(nodePos - 1)
                        .getNeighbourList()
                        .stream()
                        .map(s -> "node" + s.getPosition())
                        .collect(Collectors.toList()))
        );
        return neighbours;
    }

    public static String addFirst(){
        StringBuilder firstSB = new StringBuilder("node");
        parsedCfg.ifPresent(cfg -> firstSB.append(cfg.getFirst()));
        return firstSB.toString();
    }

    public static String addLast(){
        StringBuilder secondSB = new StringBuilder("node");
        parsedCfg.ifPresent(cfg -> secondSB.append(cfg.getLast()));
        return secondSB.toString();
    }
}
