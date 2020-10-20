package helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Comsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("msg");
        connectionFactory.setPassword("msg123");
        connectionFactory.setVirtualHost("/msg");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        //channel 绑定 queue
        channel.queueDeclare("world",false,false,false,null);

        //通过channel取得消息
        /**
         * 参数分别是队列名称，是否自动确认，回调函数
         */
        channel.basicConsume("world",true,new DefaultConsumer(channel){

            /**
             * @param consumerTag
             * @param envelope
             * @param properties
             * @param body 队列里的message
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("msg is :"+new String(body));
            }
        });

        //注释掉下面两句，程序会一直监听queue,有消息就取出来
        channel.close();
        connection.close();
    }
}
