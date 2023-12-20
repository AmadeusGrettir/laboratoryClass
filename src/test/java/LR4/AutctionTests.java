package LR4;

import LR4.ConsumerBehaviours.GetOrderResultBehaviour;
import LR4.ConsumerBehaviours.SendRequestBehaviour;
import LR4.Containers.ProducerPowerInfo;
import LR4.Containers.ResultsInfo;
import LR4.DistributerBehaviours.ReceiveConsumerRequestBehaviour;
import LR4.ProducerBehaviours.WaitAuctionBehaviour;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import LR4.kit.MasStarterKit;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutctionTests {
    private MasStarterKit kit = new MasStarterKit();
    private Timer timer = Timer.getInstance();

    //Сценарий 1: Торги с единственным поставщиком.
    // Задать такое количество покупаемой мощности,
    // чтобы только 1 поставщик смог удовлетворить запросу.
    // Ожидаемый результат: агент-производитель продает по завышенной цене мощность,
    // однако контракт отклоняется поставщиком из-за большой цены.
    @Test
    @SneakyThrows
    void Scenarius1(){
        kit.startJade(List.of("jade.core.messaging.TopicManagementService"));

        timer.reInitiate(900);
        ResultsInfo results = new ResultsInfo();
        List<Double> energyConsumption = new ArrayList<>();
        energyConsumption.add(17.0);
        List<Double> energyGeneration1 = new ArrayList<>();
        energyGeneration1.add(20.0);
        List<Double> energyGeneration2 = new ArrayList<>();
        energyGeneration2.add(12.0);
        List<Double> energyGeneration3 = new ArrayList<>();
        energyGeneration3.add(0.0);

        AID distributor = new AID("Distributer", false);

        Behaviour[] consumer = createConsumerBehaviours(energyConsumption, 0.01, distributor, results);

        Behaviour receiveConsumerRequest = new ReceiveConsumerRequestBehaviour();

        Behaviour producer1 = createProducerBehaviour(energyGeneration1);
        Behaviour producer2 = createProducerBehaviour(energyGeneration2);
        Behaviour producer3 = createProducerBehaviour(energyGeneration3);

        kit.createAgent("Consumer", consumer);
        kit.createAgent("Distributer", receiveConsumerRequest);
        kit.createAgent("ThermalPowerPlant", registerInDf("producer"), producer1);
        kit.createAgent("WindPowerPlant", registerInDf("producer"), producer2);
        kit.createAgent("SolarPowerPlant", registerInDf("producer"), producer3);

        Thread.sleep(3000);

        assertEquals(0, results.getResults().size());
    }

    //Сценарий 2: Успешный аукцион с двумя участниками.
    // Задать такое количество покупаемой мощности,
    // чтобы два поставщика смогли удовлетворить запросу и начали процесс снижения цены.
    // Ожидаемый результат: агенты соревнуются друг с другом для право продать ЭЭ,
    // и один из агентов-производителей продает по удовлетворительной цене запрошенную мощность.
    @Test
    @SneakyThrows
    void Scenarius2(){
        kit.startJade(List.of("jade.core.messaging.TopicManagementService"));

        timer.reInitiate(900);
        ResultsInfo results = new ResultsInfo();
        List<Double> energyConsumption = new ArrayList<>();
        energyConsumption.add(10.0);
        List<Double> energyGeneration1 = new ArrayList<>();
        energyGeneration1.add(20.0);
        List<Double> energyGeneration2 = new ArrayList<>();
        energyGeneration2.add(20.0);
        List<Double> energyGeneration3 = new ArrayList<>();
        energyGeneration3.add(0.0);

        AID distributor = new AID("Distributer", false);

        Behaviour[] consumer = createConsumerBehaviours(energyConsumption, 2.0, distributor, results);

        Behaviour receiveConsumerRequest = new ReceiveConsumerRequestBehaviour();

        Behaviour producer1 = createProducerBehaviour(energyGeneration1);
        Behaviour producer2 = createProducerBehaviour(energyGeneration2);
        Behaviour producer3 = createProducerBehaviour(energyGeneration3);

        kit.createAgent("Consumer", consumer);
        kit.createAgent("Distributer", receiveConsumerRequest);
        kit.createAgent("ThermalPowerPlant", registerInDf("producer"), producer1);
        kit.createAgent("WindPowerPlant", registerInDf("producer"), producer2);
        kit.createAgent("SolarPowerPlant", registerInDf("producer"), producer3);

        Thread.sleep(3000);

        assertEquals(1, results.getResults().size());
    }

    //Сценарий 3: Дефицит мощности в системе.
    // Задать такое количество покупаемой мощности,
    // что ни один производитель не может полностью удовлетворить запрос.
    // Ожидаемый результат: агент дистрибьютер должен разбить контракт на
    // несколько частей и закупить требуемое количество у различных поставщиков.
    @Test
    @SneakyThrows
    void Scenarius3(){
        kit.startJade(List.of("jade.core.messaging.TopicManagementService"));

        timer.reInitiate(900);
        ResultsInfo results = new ResultsInfo();
        List<Double> energyConsumption = new ArrayList<>();
        energyConsumption.add(20.0);
        List<Double> energyGeneration1 = new ArrayList<>();
        energyGeneration1.add(15.0);
        List<Double> energyGeneration2 = new ArrayList<>();
        energyGeneration2.add(15.0);
        List<Double> energyGeneration3 = new ArrayList<>();
        energyGeneration3.add(0.0);

        AID distributor = new AID("Distributer", false);

        Behaviour[] consumer = createConsumerBehaviours(energyConsumption, 5.0, distributor, results);

        Behaviour receiveConsumerRequest = new ReceiveConsumerRequestBehaviour();

        Behaviour producer1 = createProducerBehaviour(energyGeneration1);
        Behaviour producer2 = createProducerBehaviour(energyGeneration2);
        Behaviour producer3 = createProducerBehaviour(energyGeneration3);

        kit.createAgent("Consumer", consumer);
        kit.createAgent("Distributer", receiveConsumerRequest);
        kit.createAgent("ThermalPowerPlant", registerInDf("producer"), producer1);
        kit.createAgent("WindPowerPlant", registerInDf("producer"), producer2);
        kit.createAgent("SolarPowerPlant", registerInDf("producer"), producer3);

        Thread.sleep(3000);

        assertEquals(2, results.getResults().size());
    }

    private Behaviour registerInDf(String serviceToReg){
        return new OneShotBehaviour() {
            @Override
            public void action() {
                DfHelper.register(this.getAgent(), serviceToReg);
            }
        };
    }


    private Behaviour[] createConsumerBehaviours(List<Double> energyConsumption, double maxPrice, AID distributer, ResultsInfo results){
        Behaviour sendOffer = new SendRequestBehaviour(energyConsumption, maxPrice, distributer);
        Behaviour getOrderResult = new GetOrderResultBehaviour(results);
        return new Behaviour[]{sendOffer, getOrderResult};
    }


    private Behaviour createProducerBehaviour(List<Double> energyGeneration){
        return new OneShotBehaviour(){
            Behaviour subBeh;
            @Override
            public void action() {
                ProducerPowerInfo producerInfo = new ProducerPowerInfo();
                producerInfo.setEnergyGeneration(energyGeneration);
                subBeh = new WaitAuctionBehaviour(producerInfo);
                getAgent().addBehaviour(subBeh);
            }

            @Override
            public int onEnd() {
                return subBeh.onEnd();
            }
        };
    }

}
