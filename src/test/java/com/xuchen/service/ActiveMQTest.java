//package com.xuchen.service;
//
//
//import com.xuchen.BootManage;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.jms.Destination;
//import java.time.LocalDateTime;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = BootManage.class)
//public class ActiveMQTest {
//
//    @Autowired
//    JmsMessagingTemplate jmsMessagingTemplate;
//
//    @Test
//    public void send(){
//        Destination d = new ActiveMQQueue("test-mq");
//        jmsMessagingTemplate.convertAndSend(d, LocalDateTime.now()+"hello world");
//    }
//
//    @Test
//    public void receive(){
//        jmsMessagingTemplate.setDefaultDestinationName("test-mq");
//        Message<?> receive = jmsMessagingTemplate.receive();
//        System.out.println(receive.getHeaders());
//        System.out.println(receive.getPayload());
//    }
//
//}
