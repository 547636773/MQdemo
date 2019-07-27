package workfair;
import com.rabbitmq.client.*;
import service.SendService;
import utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RoundConsumeService {
    public static void main(String[] args) {
        Channel channel ;

        try {
            Connection conneciton = ConnectionUtils.getConneciton();
            channel = conneciton.createChannel();
            channel.basicQos(1);
            DefaultConsumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String (body,"utf-8");
                    System.out.println("receiveMsg:"+msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        //手动回执
                        channel.basicAck(envelope.getDeliveryTag(),false);
                    }

                }
            };
            // 公平分发 必须改成false
            boolean ackMode = false;
            channel.basicConsume(SendService.QUEUE_NAME,ackMode,consumer);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
