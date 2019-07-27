package topic;

import com.rabbitmq.client.*;
import routing.RoutingSendService;
import utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicConsumeService {
    public static final String QUEUE_NAME = "queue_topic";

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {

        Connection conneciton = ConnectionUtils.getConneciton();
        Channel channel = conneciton.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, TopicSendService.EXCHANGE_NAME, "good.add");
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
