package LR4.ProducerBehaviours;

import LR4.Containers.ProducerInfo;
import LR4.ProducerBehaviours.ProducerFSM.*;
import jade.core.AID;
import jade.core.behaviours.FSMBehaviour;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ProducerFSMBehaviour extends FSMBehaviour {
    private final String SEND_FIRST = "SEND_FIRST";
    private final String SEND_LOWER = "SEND_LOWER";
    private final String FINAL_PRICE = "FINAL_PRICE";
    private final String NOT_ENOUGH_POWER = "NOT_ENOUGH_POWER";
    private final String WAIT_CONFIRM = "WAIT_CONFIRM";


    private AID topic;
    private ProducerInfo producerInfo;


    public ProducerFSMBehaviour(ProducerInfo producerInfo, AID topic) {
        this.topic = topic;
        this.producerInfo = producerInfo;
    }

    @Override
    public void onStart() {


        registerFirstState(new SendFirstPriceBehaviour(producerInfo, topic), SEND_FIRST);
        registerState(new SendLowerPriceBehaviour(producerInfo, topic), SEND_LOWER);
        registerLastState(new BankruptBehaviour(), FINAL_PRICE);
        registerLastState(new PowerlessBehaviour(topic), NOT_ENOUGH_POWER);

//        registerLastState(new WaitConfirmBehaviour(producerInfo), WAIT_CONFIRM);


        registerDefaultTransition(SEND_FIRST, SEND_LOWER);
        registerTransition(SEND_LOWER, FINAL_PRICE, 1);
        registerTransition(SEND_LOWER, NOT_ENOUGH_POWER, 0);

//        registerDefaultTransition(SEND_LOWER, WAIT_CONFIRM);
    }
}
