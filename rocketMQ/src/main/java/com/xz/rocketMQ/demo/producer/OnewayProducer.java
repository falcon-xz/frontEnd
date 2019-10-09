package com.xz.rocketMQ.demo.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * Created by xz on 2019/8/15.
 */
public class OnewayProducer {
    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("test") ;
        producer.setNamesrvAddr("127.0.0.1:8081");
        producer.setInstanceName("producer-instance");
        try {
            producer.start();
            for (int i = 0; i < 10 ; i++) {
                Message message = new Message("topicTest","TagA",("hello"+i).getBytes(RemotingHelper.DEFAULT_CHARSET)) ;
                producer.sendOneway(message);
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            System.out.println(producer.getCreateTopicKey());
            producer.shutdown();
        }
    }
}
