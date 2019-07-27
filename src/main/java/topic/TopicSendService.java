package topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicSendService {
    public static final String EXCHANGE_NAME = "exchange_test_topic";

    public static void main(String[] args) {
        try (Connection conneciton = ConnectionUtils.getConneciton();
             Channel channel = conneciton.createChannel();
        ) {
            channel.exchangeDeclare(EXCHANGE_NAME,"topic");
            for (int i = 0; i < 100; i++) {
                String msg = "hello queue index :" + i;
                if(i % 3 == 0){
                    channel.basicPublish(EXCHANGE_NAME, "good.add", null, msg.getBytes());
                }else{
                    channel.basicPublish(EXCHANGE_NAME, "good.delete", null, msg.getBytes());
                }
                Thread.sleep(5);
            }
            System.out.println("send success");
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
