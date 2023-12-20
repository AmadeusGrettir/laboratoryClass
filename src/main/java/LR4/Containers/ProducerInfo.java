package LR4.Containers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProducerInfo {
//    private List<Double> energyGeneration;
    private double finalPrice;
    private double currentPrice;
//    private double currentEnergy;
    private double RequestedEnergy;

//    public boolean hasEnoughPower() {
//        return currentEnergy >= RequestedEnergy;
//    }
    private ProducerPowerInfo producerPowerInfo;

    public double getFirstPrice(){
        return finalPrice * 2;
    }
}
