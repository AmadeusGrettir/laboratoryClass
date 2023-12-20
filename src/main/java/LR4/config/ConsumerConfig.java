package LR4.config;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "loadConfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsumerConfig {
    @XmlAttribute
    private String name;
    @XmlElement
    private String distributor;
    @XmlElement
    private double maxPower;
    @XmlElementWrapper(name = "loads")
    @XmlElement(name = "load")
    private List<Double> LoadList;
    public double getLoadByHour(int h) {
        return LoadList.get(h) * maxPower;
    }
}

