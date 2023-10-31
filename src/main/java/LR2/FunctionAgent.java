package LR2;

import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FunctionAgent extends Agent {

    @Override
    protected void setup() {
        log.info("Started {}", this.getLocalName());

        this.addBehaviour(new CalcMyFunctionBehaviour());
        this.addBehaviour(new CatchInitiativeBehaviour());
        if (this.getLocalName().equals("Agent1")){
            this.addBehaviour(new BeginCalculationBehaviour(this, 10000));
        }
    }
}
