package projmosquitto;

/**
 *
 * @author Leonardo Pedrassoli Silva 11521ECP003
 * 
 * Moquitto cmd: net start mosquitto; mosquitto -v
 */
import entidades.*;

public class ProjMosquitto {
    public static void main(String[] args) throws InterruptedException {
        
        MqttPublisher publ = new MqttPublisher();
        MqttSubscriber subs = new MqttSubscriber();
        
        subs.main(args);
        Thread.sleep(1000);
        publ.main(args);
       
    }
}
