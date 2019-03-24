package pers.fancy.cloud.finchley.consumer;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

/**
 * @author fancy
 * @time 2019/3/24 0024 15:59
 */
@EnableBinding(Sink.class)
public class KafkaConsumer {

    @StreamListener(Sink.INPUT)
    public void process(Message<?> message) {

        byte[] payload = (byte[]) message.getPayload();
        String result = new String(payload);
        MessageHeaders headers = message.getHeaders();
//        Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
//        if (acknowledgment != null) {
//            System.out.println("Acknowledgment provided");
//            acknowledgment.acknowledge();
//        }
        System.out.println("接收到消息：" + result);
    }
}
