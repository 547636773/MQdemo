package ronnd;

import com.rabbitmq.client.*;
import service.SendService;
import utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RoundConsumeService2 {
    public static void main(String[] args) {
        try {
            Connection conneciton = ConnectionUtils.getConneciton();
            Channel channel = conneciton.createChannel();
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String (body,"utf-8");
                    System.out.println("receiveMsg:"+msg);
                }
            };
            channel.basicConsume(SendService.QUEUE_NAME,true,consumer);


        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
