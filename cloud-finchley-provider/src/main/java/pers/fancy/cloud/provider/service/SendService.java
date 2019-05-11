package pers.fancy.cloud.provider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

/**
 * 功能描述：
 *
 * @author lihuan
 * @date: 2019/3/22 14:41
 */
@EnableBinding(Source.class)
public class SendService {

    @Autowired
    private Source source;

    public void sendMessage(String msg) {
        try {
            Message message = MessageBuilder.withPayload(msg).build();
//            MessageBuilder.createMessage("","");
            source.output().send(message);
            System.out.println("ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

