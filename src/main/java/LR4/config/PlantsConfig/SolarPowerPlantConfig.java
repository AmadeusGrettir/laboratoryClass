package LR4.config.PlantsConfig;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.function.Function;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SolarPowerPlant")
public class SolarPowerPlantConfig implements Generation{
    @XmlElementWrapper(name = "solarCoefficients")
    @XmlElement(name = "coef")
    private List<Double> solarCoefficients;

    @Override
    public Double getPowerByHour(int t) {
        if (t < 5 || t > 19) {
            return 0.0;
        } else {
            double power = 0.0;
            for (int i = 0; i < solarCoefficients.size(); i++) {
                power += Math.abs(solarCoefficients.get(i) * Math.pow(t, i));
            }
            return power/10;
        }
    }
}
