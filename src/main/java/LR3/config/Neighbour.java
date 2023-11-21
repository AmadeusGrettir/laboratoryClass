package LR3.config;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Neighbour {
    @XmlElement(name = "neighbourPosition")
    private int position;
    @XmlElement(name = "weightingCoefficient")
    private double weighting;
}
