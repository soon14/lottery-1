/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oruit.app.aliyun;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.common.utils.ServiceSettings;
import com.aliyun.mns.model.Message;
import com.aliyun.mns.model.QueueMeta;
import org.apache.log4j.Logger;

/**
 * @deprecated 阿里云消息队列封装类
 * @author Liuk
 */
public class AliyunTaskUtil {

    private static final Logger log = Logger.getLogger(AliyunTaskUtil.class);
    private static MNSClient client = null;

    private static final String QUEUE_NAME = "java-ggc";

    static {
        CloudAccount account = new CloudAccount(
                ServiceSettings.getMNSAccessKeyId(),
                ServiceSettings.getMNSAccessKeySecret(),
                ServiceSettings.getMNSAccountEndpoint());
        client = account.getMNSClient();
    }

    public static void clear() {
        if (client.isOpen()) {
            client.close();
        }
    }

    /**
     * 创建队列
     */
    public static void createQueue() {
        QueueMeta queueMeta = new QueueMeta();
        queueMeta.setQueueName(QUEUE_NAME);
        queueMeta.setPollingWaitSeconds(15);
        queueMeta.setMaxMessageSize(2048L);
        CloudQueue queue = client.createQueue(queueMeta);
        System.out.println("Queue1 URL: " + queue.getQueueURL());
    }

    /**
     * 删除队列
     */
    public static void deleteQueue() {
        try {
            CloudQueue queue = client.getQueueRef(QUEUE_NAME);
            queue.delete();
        } catch (ServiceException | ClientException e) {
            log.debug("----删除队列错误------", e);
        }

    }

    /**
     * 把消息往队列中
     *
     * @param userId
     */
    public static void sendMessage(Integer userId) {
        try {
            CloudQueue queue = client.getQueueRef(QUEUE_NAME);
            Message message = new Message();
            message.setMessageBody(userId.toString());
            message.setPriority(1); //消息的优先级，优先级越高的消息，越容易更早被消费
            queue.putMessage(message);
            log.debug("-----------send message----------"+userId);
        } catch (ServiceException | ClientException e) {
            log.debug("----发送消息错误----", e);
        }

    }

    public static Message receiveMessage() {
        try {
            CloudQueue queue = client.getQueueRef(QUEUE_NAME);
            Message msg = queue.popMessage();
            String messageBody = msg.getMessageBodyAsString();
            String msgId = msg.getMessageId();
            log.debug("--------接收的消息内容--------" + messageBody + "------" + msgId);
             //删除队列消息
            String receiptHandle = msg.getReceiptHandle();
            queue.deleteMessage(receiptHandle);
            return msg;
           
        } catch (ServiceException | ClientException e) {
            log.debug("----接收消息错误----", e);
        }
        return null;
    }
    
    // 使用同步接口
    public static Message messageOperators(Integer userid) {
        try {
            // 创建队列
          CloudQueue queue = client.getQueueRef(QUEUE_NAME);

            // 发送消息
            Message message = new Message();
            message.setMessageBody(userid.toString());
            Message putMsg = queue.putMessage(message);
            System.out.println("PutMessage has MsgId: " + putMsg.getMessageId());
            System.out.println("Md5:" + putMsg.getMessageBodyMD5());

            // 查看消息
            Message peekMsg = queue.peekMessage();
            System.out.println("PeekMessage has MsgId: "
                    + peekMsg.getMessageId());
            System.out.println("PeekMessage Body: "
                    + peekMsg.getMessageBodyAsString());

            // 获取消息
            Message popMsg = queue.popMessage();
            System.out.println("PopMessage Body: "
                    + popMsg.getMessageBodyAsString());

            // 更改消息可见时间
            String receiptHandle = popMsg.getReceiptHandle();
            int visibilityTimeout = 1;
            String rh = queue.changeMessageVisibilityTimeout(receiptHandle,
                    visibilityTimeout);
            System.out.println("ReceiptHandle:" + rh);

            // 删除消息
            queue.deleteMessage(rh);
            return popMsg;
        } catch (ClientException | ServiceException ex) {
            // 错误处理
            ex.printStackTrace();
        }
        // 错误处理
        return null;
    }
    
    public static void main(String[] args) {
//        AliyunTaskUtil.createQueue();
        for (int i = 0; i < 100; i++) {
            Message message = AliyunTaskUtil.messageOperators(i);
            System.out.println("------------------------------------"+message.getMessageBodyAsString());
        }
    }
}
