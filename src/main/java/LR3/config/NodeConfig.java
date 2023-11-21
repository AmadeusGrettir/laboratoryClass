package LR3.config;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "cfg")
@XmlAccessorType(XmlAccessType.FIELD)
public class NodeConfig {
    @XmlElement
    private int first;
    @XmlElement
    private int last;
    @XmlElementWrapper(name="nodes")
    @XmlElement(name="node")
    private List<Node> nodeList;
}

