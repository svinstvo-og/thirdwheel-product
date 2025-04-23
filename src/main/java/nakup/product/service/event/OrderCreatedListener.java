package nakup.product.service.event;

import nakup.product.model.event.OrderCreatedEvent;
import nakup.product.model.event.UnitPriceUpdateEvent;
import nakup.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class OrderCreatedListener {

    @Autowired
    private ProductService productService;

    @Autowired
    private UnitPriceUpdatePublisher unitPriceUpdatePublisher;

    private final String TOPIC = "order-created";

    @KafkaListener(topics = TOPIC, groupId = "product-service", properties = {"spring.json.value.default.type=nakup.product.model.event.OrderCreatedEvent"})
    public void handleOrderCreated(OrderCreatedEvent event) {
        System.out.println("Accepted 'order-created' event: " + event);

        HashMap<Long, Double> unitPrices = productService.getPrices(event.items().keySet().stream().toList());
        UnitPriceUpdateEvent unitPriceUpdateEvent = new UnitPriceUpdateEvent(event.orderId(), event.userId(), unitPrices);

        unitPriceUpdatePublisher.unitPriceUpdate(unitPriceUpdateEvent);
    }

}
