package LR4.config.PlantsConfig;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.Random;
import java.util.function.Function;

@Data
@XmlRootElement(name = "WindPowerPlant")
@XmlAccessorType(XmlAccessType.FIELD)
public class WindPowerPlantConfig implements Generation {
    @XmlElement(name = "meanValue")
    private double meanValue;
    @XmlElement(name = "dispersion")
    private double dispersion;
    private Random random = new Random();

    @Override
    public Double getPowerByHour(int t) {
        return Math.abs(random.nextGaussian())*dispersion + meanValue;
    }
}
