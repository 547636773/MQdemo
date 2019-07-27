package utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils {
    public static Connection getConneciton() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/vhost_knight");
        factory.setUsername("knight");
        factory.setPassword("123");
        return factory.newConnection();
    }

}
