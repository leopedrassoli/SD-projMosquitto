package entidades;

import java.util.Random;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttPublisher {

    public static void main(String[] args) throws InterruptedException {

        String topic = "sensor/temperature/1";
        int qos = 2;
        String broker = "tcp://localhost:1883";
        String clientId = "publisherPedrassoli";
        MemoryPersistence persistence = new MemoryPersistence();
        
        Random r = new Random();
        int temp;
        String content;

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("P-Connecting to broker: " + broker);
            sampleClient.connect(connOpts);
            System.out.println("P-Connected");
            while(true){
                temp = r.nextInt(45-15) + 15;
                content = Integer.toString(temp);
                System.out.println("P-Publishing topic, message: " + topic + ", " + content);
                MqttMessage message = new MqttMessage(content.getBytes());
                message.setQos(qos);
                sampleClient.publish(topic, message); 
                Thread.sleep(1000);
            }          
            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }
}