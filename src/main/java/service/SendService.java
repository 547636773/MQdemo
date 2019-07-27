package service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SendService {
    public static final String QUEUE_NAME = "queue_test";

    public static void main(String[] args) {
        try (Connection conneciton = ConnectionUtils.getConneciton();
             Channel channel = conneciton.createChannel();
        ) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            for (int i = 0; i < 4; i++) {
                String msg = "hello queue index" + i;
                channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            }
            System.out.println("send success");
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
