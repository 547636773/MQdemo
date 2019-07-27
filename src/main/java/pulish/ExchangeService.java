package pulish;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ExchangeService {
    public static final String EXCHANGE_NAME= "test_exange";
    public static void main(String[] args) {
        try (Connection connection = ConnectionUtils.getConneciton();
             Channel channel = connection.createChannel();) {

            // 声明交换机
            channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
            // 声明消息
            String msg = "hello ps 211 ";
            Thread.sleep(10000);
            channel.basicPublish(EXCHANGE_NAME,"",false,null,msg.getBytes("utf-8"));

            System.out.println("exchange success !!");
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
