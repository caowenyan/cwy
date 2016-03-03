package cn.cindy.jmx.activemq.selector;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;

/**
 * sesssion.createConsumer(destination, selector)
 * 这里selector是一个字符串，用来过滤消息。
 * 也就是说，这种方式可以创建一个可以只接收特定消息的一个消费者。
 * Selector的格式是类似于SQL-92的一种语法。可以用来比较消息头信息和属性。
 * 下面创建两个消费者，共同监听同一个Queue，但是它们的Selector不同，然后创建一个消息生产者，来发送多个消息。
 */
public class JMSSelectorTest {
    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");

        Connection connection = factory.createConnection();
        connection.start();

        Queue queue = new ActiveMQQueue("testQueue");

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageConsumer comsumerA = session.createConsumer(queue, "receiver = 'A'");
        comsumerA.setMessageListener(new MessageListener(){
            public void onMessage(Message m) {
                try {
                    System.out.println("ConsumerA get " + ((TextMessage) m).getText());
                } catch (JMSException e1) { }
            }
        });

        MessageConsumer comsumerB = session.createConsumer(queue, "receiver = 'B'");
        comsumerB.setMessageListener(new MessageListener(){
            public void onMessage(Message m) {
                try {
                    System.out.println("ConsumerB get " + ((TextMessage) m).getText());
                } catch (JMSException e) { }
            }
        });

        MessageProducer producer = session.createProducer(queue);
        for(int i=0; i<10; i++) {
            String receiver = (i%3 == 0 ? "A" : "B");
            TextMessage message = session.createTextMessage("Message" + i + ", receiver:" + receiver);
            message.setStringProperty("receiver", receiver);
            producer.send(message );
        }
    }
}
