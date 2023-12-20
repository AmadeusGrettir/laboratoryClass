package LR4.config.PlantsConfig;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.function.Function;

@Data
@XmlRootElement(name = "ThermalPowerPlant")
@XmlAccessorType(XmlAccessType.FIELD)
public class ThermalPowerPlantConfig implements Generation {
    @XmlElement(name = "power")
    private double power;

    @Override
    public Double getPowerByHour(int t) {
        return power;
    }
}
