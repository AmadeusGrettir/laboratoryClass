package LR4.Containers;

import jade.core.AID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DistributerInfo {
    private double maxPrice;
    private double bestPrice;
    private double RequestedEnergy;
    private AID bestProducer;
    private boolean divided;
    private int countDivision;
    private AID consumer;
}
