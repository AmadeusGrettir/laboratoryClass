package LR4.Containers;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProducerPowerInfo {
    private List<Double> energyGeneration;
    private double currentEnergy;
    public boolean hasEnoughPower(double RequestedEnergy) {
        return currentEnergy >= RequestedEnergy;
    }
}
