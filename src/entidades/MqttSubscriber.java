package entidades;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttSubscriber implements MqttCallback {

    public static void main(String[] args) {
        
        String topic = "sensor/temperature/1";
        int qos = 2;
        String broker = "tcp://localhost:1883";
        String clientId = "subscriberpedrassoli";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttAsyncClient sampleClient = new MqttAsyncClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            sampleClient.setCallback(new MqttSubscriber());
            System.out.println("S-Connecting to broker: " + broker);
            sampleClient.connect(connOpts);
            System.out.println("S-Connected");
            Thread.sleep(1000);
            sampleClient.subscribe(topic, qos);
            System.out.println("S-Subscribed");
        } catch (InterruptedException | MqttException me) {
            if (me instanceof MqttException) {
                System.out.println("reason " + ((MqttException) me).getReasonCode());
            }
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
        }
    }

    @Override
    public void connectionLost(Throwable arg0) {
        System.err.println("S-connection lost");

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {
        System.err.println("S-delivery complete");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("topic, message: " + topic + ", " + new String(message.getPayload()));
    }
    
}
