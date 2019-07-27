package pulish;

import com.rabbitmq.client.*;
import service.SendService;
import utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveService2 {
    public static final String QUEUE_NAME = "queue2";
    private Channel channel;

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection conneciton = ConnectionUtils.getConneciton();
        Channel channel = conneciton.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 绑定到交换机
        channel.queueBind(QUEUE_NAME, ExchangeService.EXCHANGE_NAME, "");

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("receive error Msg:" + msg);
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // 公平分发 必须改成false
        boolean ackMode = true;
        channel.basicConsume(SendService.QUEUE_NAME, ackMode, consumer);
    }
}
