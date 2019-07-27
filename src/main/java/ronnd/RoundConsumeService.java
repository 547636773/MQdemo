package ronnd;

import com.rabbitmq.client.*;
import service.SendService;
import utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RoundConsumeService {
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
            // 公平分发 必须改成false
            boolean ackMode = true;
            channel.basicConsume( SendService.QUEUE_NAME,ackMode, consumer );
            Thread.sleep(1000);

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
