package cn.cindy.jmx.activemq.correlationid;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;

/**
 * JMSCorrelationID主要是用来关联多个Message，例如需要回复一个消息的时候，通常把回复的消息的JMSCorrelationID设置为原来消息的ID
 * 在下面这个例子中，创建了三个消息生产者A，B，C和三个消息消费者A，B，C。生产者A给消费者A发送一个消息，同时需要消费者A给它回复一个消息。B、C与A类似。
 * 生产者A-----发送----〉消费者A-----回复------〉生产者A
 * 生产者B-----发送----〉消费者B-----回复------〉生产者B
 * 生产者C-----发送----〉消费者C-----回复------〉生产者C
 */
public class JMSCorrelationIDTest {
    private Queue queue;
    private Session session;

    public static void main(String[] args) throws Exception {
        new JMSCorrelationIDTest ();
    }

    public JMSCorrelationIDTest() throws JMSException{
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
        Connection connection = factory.createConnection();
        connection.start();

        queue = new ActiveMQQueue("testQueue");
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        setupConsumer("ConsumerA");
        setupConsumer("ConsumerB");
        setupConsumer("ConsumerC");

        setupProducer("ProducerA", "ConsumerA");
        setupProducer("ProducerB", "ConsumerB");
        setupProducer("ProducerC", "ConsumerC");
    }

    private void setupConsumer(final String name) throws JMSException {
        //创建一个消费者，它只接受属于它自己的消息
        MessageConsumer consumer = session.createConsumer(queue, "receiver='" + name + "'");
        consumer.setMessageListener(new MessageListener(){
            public void onMessage(Message m) {
                try {
                    MessageProducer producer = session.createProducer(queue);
                    System.out.println(name + " get:" + ((TextMessage)m).getText());
                    //回复一个消息
                    Message replyMessage = session.createTextMessage("Reply from " + name);
                    //设置JMSCorrelationID为刚才收到的消息的ID
                    replyMessage.setJMSCorrelationID(m.getJMSMessageID());
                    producer.send(replyMessage);
                } catch (JMSException e) { }
            }
        });
    }

    private void setupProducer(final String name, String consumerName) throws JMSException {
        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //创建一个消息，并设置一个属性receiver，为消费者的名字。
        Message message = session.createTextMessage("Message from " + name);
        message.setStringProperty("receiver", consumerName);
        producer.send(message);

        //等待回复的消息
        MessageConsumer replyConsumer = session.createConsumer(queue, "JMSCorrelationID='" + message.getJMSMessageID() + "'");
        replyConsumer.setMessageListener(new MessageListener(){
            public void onMessage(Message m) {
                try {
                    System.out.println(name + " get reply:" + ((TextMessage)m).getText());
                } catch (JMSException e) { }
            }
        });
    }

}
