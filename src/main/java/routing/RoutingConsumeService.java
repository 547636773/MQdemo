package routing;

import com.rabbitmq.client.*;
import pulish.ExchangeService;
import service.SendService;
import utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RoutingConsumeService {
    public static final String QUEUE_NAME = "queue_direct1";

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {

        Connection conneciton = ConnectionUtils.getConneciton();
        Channel channel = conneciton.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, RoutingSendService.EXCHANGE_NAME, "info");
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("receive info Msg:" + msg);
            }
        };
        // 公平分发 必须改成false
        boolean ackMode = false;
        channel.basicConsume(QUEUE_NAME, ackMode, consumer);
        Thread.sleep(10);

    }
}
