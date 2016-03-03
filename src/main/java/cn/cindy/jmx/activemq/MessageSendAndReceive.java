package cn.cindy.jmx.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;
/**
 * Created by Caowenyan on 2016/3/2.
 */
public class MessageSendAndReceive {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost:61616");
        //不知道怎么使用,现在不好使
//        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        Connection connection = factory.createConnection();
        connection.start();

        Queue queue = new ActiveMQQueue("testQueue");

        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Message message = session.createTextMessage("Hello JMS!");

        MessageProducer producer = session.createProducer(queue);
        producer.send(message);

        System.out.println("Send Message Completed!");

        MessageConsumer comsumer = session.createConsumer(queue);
        //1直接等待输出结果,阻塞
        Message recvMessage = comsumer.receive();
        System.out.println(((TextMessage)recvMessage).getText());
        //2当有消息到达的时候，会回调它的onMessage()方法
        /*comsumer.setMessageListener(new MessageListener(){
            @Override
            public void onMessage(Message m) {
                TextMessage textMsg = (TextMessage) m;
                try {
                    System.out.println(textMsg.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

        });*/
    }
}
