package cn.cindy.jmx.activemq.persistent;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;

/**
 * 可以看出消息消费者只接收到一个消息，它是一个Persistent的消息。
 * 而刚才发送的non persistent消息已经丢失了。
 * 另外, 如果发送一个non persistent消息, 而刚好这个时候没有消费者在监听, 这个消息也会丢失.
 */
public class DeliveryModeSendTest {
    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");

        Connection connection = factory.createConnection();
        connection.start();

        Queue queue = new ActiveMQQueue("testQueue");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);//持久化的消息
        producer.send(session.createTextMessage("A persistent Message"));

        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);//非持久化的消息
        producer.send(session.createTextMessage("A non persistent Message"));

        System.out.println("Send messages sucessfully!");
    }
}
