package nakup.product.service.event;

import nakup.product.model.event.UnitPriceUpdateEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UnitPriceUpdatePublisher {
    private static final String TOPIC = "unit-price-update";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public UnitPriceUpdatePublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void unitPriceUpdate(UnitPriceUpdateEvent event) {
        System.out.println("Posting 'unit-price-update' event: " + event);
        kafkaTemplate.send(TOPIC, event);
    }
}
