package cn.cindy.jmx.activemq.persistent;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;

/**
 * Created by Caowenyan on 2016/3/2.
 */
public class DeliveryModeReceiveTest {
    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");

        Connection connection = factory.createConnection();
        connection.start();

        Queue queue = new ActiveMQQueue("testQueue");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageConsumer comsumer = session.createConsumer(queue);
        comsumer.setMessageListener(new MessageListener(){
            public void onMessage(Message m) {
                try {
                    System.out.println("Consumer get " + ((TextMessage)m).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
