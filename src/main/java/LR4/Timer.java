package LR4;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Timer {
    private static Timer INSTANCE;
    private long startTime;
    private int simulationSpeed;

    private Timer() {
        startTime = System.currentTimeMillis();
        simulationSpeed = 720;
    }

    public synchronized static Timer getInstance(){
        if (INSTANCE == null){
            INSTANCE = new Timer();
        }
        return INSTANCE;
    }

    public int getCurHour() {
        int curHour = (int) (System.currentTimeMillis() - startTime) * simulationSpeed / 1000 / 3600;
        return curHour % 24;
    }

    public long getMillisToNextHour() {
        long pastTime = (System.currentTimeMillis() - startTime) * simulationSpeed;
        long currentHour = (System.currentTimeMillis() - startTime) * simulationSpeed / 1000 / 3600;
        long hourFinishTime = (currentHour + 1) * 3600 * 1000;
        return (hourFinishTime - pastTime) / simulationSpeed;
    }

    public long ConditionTime(double h){
        return (long) (h * 1000 * 3600 / simulationSpeed);
    }


    public void reInitiate(int speed) {
        startTime = System.currentTimeMillis();
        this.simulationSpeed = speed;
    }

    public long getMillisPerHour() {
        return  1000 * 3600 / simulationSpeed;
    }
}
