package workfair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RoundSendService {
    public static final String QUEUE_NAME = "queue_test";

    public static void main(String[] args) {
        try (Connection conneciton = ConnectionUtils.getConneciton();
             Channel channel = conneciton.createChannel();
        ) {
            boolean durable = false; //队列持久化 持久化到哪里 细节
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
            channel.basicQos(1);
            for (int i = 0; i < 100; i++) {
                String msg = "hello queue index :" + i;
                channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
                Thread.sleep(1005);
            }
            System.out.println("send success");
        } catch (TimeoutException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
