package helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Provider {
    public static void main(String[] args) throws IOException, TimeoutException {
        //通过链接工厂获取链接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("msg");
        connectionFactory.setPassword("msg123");
        connectionFactory.setVirtualHost("/msg");
        Connection connection = connectionFactory.newConnection();

        //创建链接绑定的通道
        Channel channel = connection.createChannel();
        //创建通道对应的queue
        /**
         * 参数分别是queuename,durable,exclusive,autoDelete,其他参数
         */
        channel.queueDeclare("world",false,false,false,null);

        /**
         * 参数分别是exchange名称，queue名称，额外参数,具体的消息
         */
        channel.basicPublish("","world",null,"hello world".getBytes());

        channel.close();
        connection.close();
    }
}
